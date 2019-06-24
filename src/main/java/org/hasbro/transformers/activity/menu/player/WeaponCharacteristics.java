package org.hasbro.transformers.activity.menu.player;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

class WeaponCharacteristics {
    static final int WEAPON_POWER_MIN = 1;
    static final int WEAPON_POWER_MAX = 100;

    static final int ALTERNATE_MODE_WEAPON_POWER_MIN = 50;
    static final int ALTERNATE_MODE_WEAPON_POWER_MAX = 100;

    static final float WEAPON_ACCURACY_MIN = 0.1f;
    static final float WEAPON_ACCURACY_MAX = 1.0f;

    private static final IntPredicate WEAPON_POWER_PREDICATE = n -> n >= WEAPON_POWER_MIN && n <= WEAPON_POWER_MAX;
    static final Predicate<String> WEAPON_POWER_VALIDITY_PREDICATE = power -> {
        try {
            return WEAPON_POWER_PREDICATE.test(Integer.parseInt(power));
        } catch (NumberFormatException e) {
            return true;
        }
    };

    private static final IntPredicate ALTERNATE_MODE_WEAPON_POWER_PREDICATE = n -> n >= ALTERNATE_MODE_WEAPON_POWER_MIN && n <= ALTERNATE_MODE_WEAPON_POWER_MAX;
    static final Predicate<String> ALTERNATE_MODE_WEAPON_POWER_VALIDITY_PREDICATE = power -> {
        try {
            return ALTERNATE_MODE_WEAPON_POWER_PREDICATE.test(Integer.parseInt(power));
        } catch (NumberFormatException e) {
            return true;
        }
    };

    private static final Predicate<Float> WEAPON_ACCURACY_PREDICATE = n -> n >= WEAPON_ACCURACY_MIN && n <= WEAPON_ACCURACY_MAX;
    static final Predicate<String> WEAPON_ACCURACY_VALIDITY_PREDICATE = accuracy -> {
        try {
            return WEAPON_ACCURACY_PREDICATE.test(Float.parseFloat(accuracy));
        } catch (NumberFormatException e) {
            return true;
        }
    };

    private WeaponCharacteristics() {
    }
}
