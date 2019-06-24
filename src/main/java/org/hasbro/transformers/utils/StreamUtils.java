package org.hasbro.transformers.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamUtils {
    private StreamUtils() {
    }

    public static <T, R> R evaluateTernaryViaFunction(Function<T, R> success, Function<T, R> failure, Predicate<T> predicate, T value) {
        Map<Boolean, Function<T, R>> map = new HashMap<>(2);
        map.put(Boolean.TRUE, success);
        map.put(Boolean.FALSE, failure);

        return map.get(predicate.test(value)).apply(value);
    }

    public static <T, R> R evaluateTernaryViaSupplier(Supplier<R> success, Supplier<R> failure, Predicate<T> predicate, T value) {
        Map<Boolean, Supplier<R>> map = new HashMap<>(2);
        map.put(Boolean.TRUE, success);
        map.put(Boolean.FALSE, failure);

        return map.get(predicate.test(value)).get();
    }

    @SafeVarargs
    public static <T extends Enum> Map<String, Integer> mapAsMenuOptions(T... values) {
        return Stream.of(values).collect(Collectors.toMap(Enum::name, ev -> ev.ordinal() + 1));
    }
}
