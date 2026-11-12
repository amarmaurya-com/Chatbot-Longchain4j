package com.codes;

import com.codes.dto.DayPlan;
import com.codes.dto.StudyPlan;
import com.codes.service.ChatHelper;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.TokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.midi.SoundbankResource;
import javax.xml.transform.Source;
import java.security.SecureRandom;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.CompletableFuture;

/**
 * Hello world!
 *
 */
public class App 
{
        private static final Logger log = LoggerFactory.getLogger(App.class);
        private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
        private static int length = 12;

        private static StringBuilder sb = new StringBuilder(length);
        private static SecureRandom random = new SecureRandom();

    static {
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
    }
    public static void main(String[] args )
    {
        System.out.println( "Hello World!");
        System.out.println("press 1: To create Study plane");
        System.out.println("Enter 'EXIT' to quit.'\n");
        System.out.println("Convo Id: "+sb.toString());
        Scanner input = new Scanner(System.in);
        while(true)
        {
            System.out.print("Your: ");
            String read =  input.nextLine();

            if(read.trim().equalsIgnoreCase("Exit"))
            {
                System.out.println("Exiting ChatBot");
                System.out.println("Thank you for using this program.");
                System.exit(0);
            }

            if (read.equalsIgnoreCase("1")){
                System.out.println("Enter the Topic: ");
                String topic = input.nextLine();
                StudyPlan studyPlane = ChatHelper.prepareStudyPlan(topic);
                System.out.println("--- Study Plan: "+studyPlane.planName()+"-----");

                for (DayPlan paln : studyPlane.dayPlan()){
                    System.out.println("Day "+ paln.dayNumber() + ": " + paln.title());
                    System.out.println(paln.content());
                    System.out.println("Practice Questions: " + paln.practiceQuestions());
                    System.out.println("*** *** *** ***");
                }
                continue;
            }

            TokenStream response = ChatHelper.getResponse(sb.toString(), read);
            CompletableFuture<ChatResponse> futureResponse = new CompletableFuture<>();

            response.onCompleteResponse(chatResponse -> {
                        System.out.println();
                        futureResponse.complete(chatResponse);
                    })
                    .onPartialResponse(System.out::print)
                    .onError(e->{
                        System.out.println("Error: " + e.getMessage());
                    })
                    .start();
            futureResponse.join();
        }
    }
}
