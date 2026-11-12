package com.codes.tools;

import dev.langchain4j.agent.tool.Tool;

import java.util.Arrays;

public class Calculater {
    @Tool
     int add(int... a) {
        System.out.println("*** Adding");
        return Arrays.stream(a).sum();
    }

    @Tool
     int multiply(int... a) {
        System.out.println("*** Multiplying");

        return Arrays.stream(a)
                .reduce(1, (x, y) -> x * y);
    }
    @Tool
     int subtract(int... a) {
        System.out.println("*** Subtracting");

        return Arrays.stream(a)
                .reduce((x, y) -> x - y)
                .orElse(0);
    }
    @Tool
     int max(int... a) {
        System.out.println("*** Max");

        return Arrays.stream(a).max().orElse(0);
    }
    @Tool
     int min(int... a) {
        System.out.println("*** Min");

        return Arrays.stream(a).min().orElse(0);
    }
    @Tool
     double average(int... a) {
        System.out.println("*** Average");
        return Arrays.stream(a).average().orElse(0);
    }

}
