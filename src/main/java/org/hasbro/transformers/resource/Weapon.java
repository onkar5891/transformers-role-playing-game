package org.hasbro.transformers.resource;

import java.io.Serializable;
import java.util.function.BiFunction;

import static java.text.MessageFormat.format;

public final class Weapon implements Serializable {
    private static final BiFunction<Integer, Float, Integer> ATTACK_IMPACT_FN = (weaponPower, weaponAccuracy) -> (int) (weaponPower * weaponAccuracy);

    private final String name;
    private final int powerImpact;

    public Weapon(String name, int power, float accuracy) {
        this.name = name;
        this.powerImpact = ATTACK_IMPACT_FN.apply(power, accuracy);
    }

    int getPowerImpact() {
        return powerImpact;
    }

    @Override
    public String toString() {
        return format("[Weapon: {0}, Power Impact: {1}]", name, powerImpact);
    }
}
