package org.hasbro.transformers.utils;

import java.util.List;
import java.util.Random;

public class RandomUtils {
    private static volatile RandomUtils instance;

    private final Random random;

    private RandomUtils() {
        this.random = new Random();
    }

    public static RandomUtils getInstance() {
        if (instance == null) {
            synchronized (RandomUtils.class) {
                if (instance == null) {
                    instance = new RandomUtils();
                }
            }
        }
        return instance;
    }

    public <T> T fromArray(T[] items) {
        return items[random.nextInt(items.length)];
    }

    public <T> T fromList(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    public boolean yesOrNo() {
        return random.nextBoolean();
    }
}
