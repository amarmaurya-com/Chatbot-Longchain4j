package com.codes.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriteTool {

    @Tool("""
            Write the given content into hte file and save with the given name(FileName must be with extension).
            """)
    public void writeToFile(@P("Content to write into the file") String content,
                            @P("Name of the file") String fileName) throws IOException {
        System.out.println("*** FileWriteTool.writeToFile");
        System.out.println("File Name: " + fileName);
        System.out.println("Content: " + content);

        Path path = Path.of("files/"+fileName);
        Files.createDirectories(path.getParent());

        FileWriter fileWriter = new FileWriter("files/"+fileName);
        try {
            fileWriter.write(content);
            fileWriter.close();
        }catch (IOException e) {
            System.out.println("Error writing to file" + e.getMessage());
        }
    }
}
