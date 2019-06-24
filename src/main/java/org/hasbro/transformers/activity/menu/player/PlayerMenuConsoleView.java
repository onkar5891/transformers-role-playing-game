package org.hasbro.transformers.activity.menu.player;

import org.hasbro.transformers.questionnaire.MenuChooser;
import org.hasbro.transformers.resource.CybertronResource;
import org.hasbro.transformers.resource.Cybertronian;
import org.hasbro.transformers.resource.TransformerMode;
import org.hasbro.transformers.resource.Weapon;

import java.util.Arrays;

import static org.hasbro.transformers.activity.menu.player.PlayerCharacteristics.*;
import static org.hasbro.transformers.resource.Allegiance.AUTOBOT;

public class PlayerMenuConsoleView implements PlayerMenuView {
    private MenuChooser<Gender> genderMenuChooser = new MenuChooser<>("Choose Gender", Gender.values());
    private Cybertronian resource;
    private Navigator navigator;

    private String characterName;
    private Gender gender;
    private int riflePower;
    private float rifleAccuracy;
    private int swordPower;
    private float swordAccuracy;
    private String transformedIdentity;
    private int transformedPowerImpact;

    @Override
    public void show() {
        characterName = NAME_QUESTION.ask();

        genderMenuChooser.load();
        gender = genderMenuChooser.getItem();

        riflePower = Integer.parseInt(RIFLE_POWER_QUESTION.ask());
        rifleAccuracy = Float.parseFloat(RIFLE_ACCURACY_QUESTION.ask());
        swordPower = Integer.parseInt(SWORD_POWER_QUESTION.ask());
        swordAccuracy = Float.parseFloat(SWORD_ACCURACY_QUESTION.ask());
        transformedIdentity = ALTERNATE_MODE_IDENTITY_QUESTION.ask();
        transformedPowerImpact = Integer.parseInt(ALTERNATE_MODE_POWER_IMPACT_QUESTION.ask());

        navigator.onPlayerCreated();
    }

    @Override
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public Cybertronian getPlayer() {
        return new CybertronResource(
            characterName,
            gender,
            AUTOBOT,
            Arrays.asList(
                new Weapon("Rifle", riflePower, rifleAccuracy),
                new Weapon("Sword", swordPower, swordAccuracy)
            ),
            new TransformerMode(transformedIdentity, transformedPowerImpact),
            true,
            true
        );
    }
}
