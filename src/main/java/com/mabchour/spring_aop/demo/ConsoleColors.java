package com.mabchour.spring_aop.demo;

public final class ConsoleColors {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private ConsoleColors() {}

    public static String red(String message) {
        return ANSI_RED + message + ANSI_RESET;
    }

    public static String green(String message) {
        return ANSI_GREEN + message + ANSI_RESET;
    }

    public static String yellow(String message) {
        return ANSI_YELLOW + message + ANSI_RESET;
    }

    public static String cyan(String message) {
        return ANSI_CYAN + message + ANSI_RESET;
    }
}

