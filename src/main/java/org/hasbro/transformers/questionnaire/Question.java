package org.hasbro.transformers.questionnaire;

import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;

import static java.text.MessageFormat.format;
import static org.hasbro.transformers.utils.MessageReader.singletonScanner;
import static org.hasbro.transformers.utils.MessageWriter.print;
import static org.hasbro.transformers.utils.MessageWriter.println;

public class Question<T> {
    private static final Predicate<String> DEFAULT_VALUE_PREDICATE = String::isEmpty;
    private final Scanner scanner = singletonScanner();

    private String title;
    private T defaultValue;
    private Predicate<String> predicate;

    public Question(String title, Predicate<String> predicate) {
        this.title = title;
        this.predicate = predicate.negate();
    }

    public Question(String title, T defaultValue, Predicate<String> predicate) {
        this.title = title;
        this.defaultValue = defaultValue;
        this.predicate = predicate.negate();
    }

    public String ask() {
        String line;
        do {
            print(loadMessage());
            line = scanner.nextLine();

            if (DEFAULT_VALUE_PREDICATE.test(line) && Objects.nonNull(defaultValue)) {
                line = defaultValue.toString();
            }

            if (predicate.test(line)) {
                println("You provided invalid answer.");
            }
        } while (predicate.test(line));

        return line;
    }

    private String loadMessage() {
        if (Objects.isNull(defaultValue)) {
            return format("{0}: ", title);
        }
        return format("{0}: [{1}] ", title, defaultValue);
    }
}
