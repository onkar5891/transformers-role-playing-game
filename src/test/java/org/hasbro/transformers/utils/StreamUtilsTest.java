package org.hasbro.transformers.utils;

import org.junit.Test;

import java.time.DayOfWeek;
import java.util.Map;

import static org.hasbro.transformers.utils.StreamUtils.*;
import static org.junit.Assert.*;

public class StreamUtilsTest {
    @Test
    public void shouldEvaluateTernaryViaFunction() {
        int myAge = 24;
        String gameToPlay = evaluateTernaryViaFunction(
            age -> "Transformers",
            age -> "Mario",
            t -> t > 18,
            myAge
        );
        assertEquals("Transformers", gameToPlay);
    }

    @Test
    public void shouldEvaluateTernaryViaSupplier() {
        int num = 10;
        String spellingOfTwelve = evaluateTernaryViaSupplier(
            () -> "Ten",
            () -> "Don't Know",
            t -> t == 12,
            num
        );
        assertEquals("Don't Know", spellingOfTwelve);
    }

    @Test
    public void shouldMapMenuOptions() {
        Map<String, Integer> mappedDaysOfWeek = mapAsMenuOptions(DayOfWeek.values());
        assertEquals(mappedDaysOfWeek.size(), DayOfWeek.values().length);
        assertTrue(mappedDaysOfWeek.values().stream().noneMatch(num -> num == 0));
    }
}
