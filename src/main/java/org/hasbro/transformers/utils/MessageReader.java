package org.hasbro.transformers.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static org.hasbro.transformers.utils.MessageWriter.println;
import static org.hasbro.transformers.utils.RPGSettings.inputMode;

public class MessageReader {
    private static volatile Scanner scanner;

    private MessageReader() {
    }

    public static Scanner singletonScanner() {
        if (scanner == null) {
            synchronized (MessageReader.class) {
                if (scanner == null) {
                    scanner = new Scanner(inputMode(), "UTF-8");
                }
            }
        }

        return scanner;
    }

    public static void resetScanner() {
        scanner = null;
    }

    public static void readFromFile(Path path, String messageOnError) {
        try {
            Files.lines(path).forEach(MessageWriter::println);
        } catch (IOException e) {
            println(messageOnError);
        }
    }
}
