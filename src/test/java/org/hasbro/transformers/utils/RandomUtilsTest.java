package org.hasbro.transformers.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class RandomUtilsTest {
    private RandomUtils randomUtils;

    @Before
    public void setUp() {
        randomUtils = RandomUtils.getInstance();
    }

    @Test
    public void shouldProvideRandomFromArray() {
        Integer[] gameLevels = { 1, 2, 3, 4 };
        Integer level = randomUtils.fromArray(gameLevels);
        assertTrue(Arrays.asList(gameLevels).contains(level));
    }

    @Test
    public void shouldProvideRandomFromList() {
        List<String> autobots = Arrays.asList("Optimus", "Jazz", "Ratchet");
        String autobot = randomUtils.fromList(autobots);
        assertTrue(autobots.contains(autobot));
    }

    @Test
    public void shouldAnswerYesOrNo() {
        Map<Boolean, String> boolMap = new HashMap<>();
        boolMap.put(Boolean.TRUE, "true");
        boolMap.put(Boolean.FALSE, "false");

        assertTrue(boolMap.containsKey(randomUtils.yesOrNo()));
    }
}
