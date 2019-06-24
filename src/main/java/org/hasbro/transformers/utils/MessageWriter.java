package org.hasbro.transformers.utils;

import java.io.PrintStream;
import java.util.stream.IntStream;

import static org.hasbro.transformers.utils.RPGSettings.outputMode;
import static org.hasbro.transformers.utils.RPGSettings.shouldPrettyPrint;

public class MessageWriter {
    private static final PrintStream PRINT_STREAM = outputMode();

    private MessageWriter() {
    }

    public static void println(Object message) {
        PRINT_STREAM.println(message);
    }

    public static void println() {
        PRINT_STREAM.println();
    }

    public static void print(Object message) {
        PRINT_STREAM.print(message);
    }

    public static void tabSuffixedPrint(int numTabs, Object message) {
        print(message);
        IntStream.rangeClosed(1, numTabs).forEach(i -> print("\t"));
    }

    public static String coloredMessage(ColorMode colorMode, Object message) {
        return colorMode.color + message + ColorMode.reset();
    }

    public static void prettyPrintln(ColorMode colorMode, Object message) {
        if (shouldPrettyPrint()) {
            printlnGuarded(colorMode, message);
        } else {
            println(message);
        }
    }

    private static void printlnGuarded(ColorMode color, Object message) {
        PRINT_STREAM.println(coloredMessage(color, message));
    }

    public enum ColorMode {
        RED("\u001b[31m"), GREEN("\u001b[32m"), CYAN("\u001b[36m");

        private String color;

        ColorMode(String color) {
            this.color = color;
        }

        private static String reset() {
            return "\u001b[0m";
        }
    }
}
