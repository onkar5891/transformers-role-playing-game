package org.hasbro.transformers.activity.menu.player;

import org.hasbro.transformers.questionnaire.Question;

import java.util.function.Predicate;

import static java.text.MessageFormat.format;
import static org.hasbro.transformers.activity.menu.player.WeaponCharacteristics.*;

class PlayerCharacteristics {
    static final Question<Integer> RIFLE_POWER_QUESTION = new Question<>(
        format("Rifle power ({0}-{1})", WEAPON_POWER_MIN, WEAPON_POWER_MAX),
        30,
        WEAPON_POWER_VALIDITY_PREDICATE
    );
    static final Question<Float> RIFLE_ACCURACY_QUESTION = new Question<>(
        format("Rifle accuracy ({0}-{1})", WEAPON_ACCURACY_MIN, WEAPON_ACCURACY_MAX),
        0.9f,
        WEAPON_ACCURACY_VALIDITY_PREDICATE
    );
    static final Question<Integer> SWORD_POWER_QUESTION = new Question<>(
        format("Sword power ({0}-{1})", WEAPON_POWER_MIN, WEAPON_POWER_MAX),
        25,
        WEAPON_POWER_VALIDITY_PREDICATE
    );
    static final Question<Float> SWORD_ACCURACY_QUESTION = new Question<>(
        format("Sword accuracy ({0}-{1})", WEAPON_ACCURACY_MIN, WEAPON_ACCURACY_MAX),
        0.9f,
        WEAPON_ACCURACY_VALIDITY_PREDICATE
    );
    static final Question<String> ALTERNATE_MODE_POWER_IMPACT_QUESTION = new Question<>(
        format("Power Impact After Transformation ({0}-{1})", ALTERNATE_MODE_WEAPON_POWER_MIN, ALTERNATE_MODE_WEAPON_POWER_MAX),
        "60",
        ALTERNATE_MODE_WEAPON_POWER_VALIDITY_PREDICATE
    );
    private static final Predicate<String> NAME_PREDICATE = name -> !name.isEmpty();
    static final Question<String> NAME_QUESTION = new Question<>("Autobot Name", "Bumblebee", NAME_PREDICATE);

    static final Question<String> ALTERNATE_MODE_IDENTITY_QUESTION = new Question<>("Transforms To", "Camaro", NAME_PREDICATE);
    private PlayerCharacteristics() {
    }
}
