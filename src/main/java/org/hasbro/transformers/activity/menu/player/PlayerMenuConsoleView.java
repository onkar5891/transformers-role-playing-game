package org.hasbro.transformers.activity.menu.player;

import org.hasbro.transformers.questionnaire.MenuChooser;
import org.hasbro.transformers.resource.CybertronResource;
import org.hasbro.transformers.resource.Cybertronian;
import org.hasbro.transformers.resource.TransformerMode;
import org.hasbro.transformers.resource.Weapon;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static org.hasbro.transformers.activity.menu.player.PlayerCharacteristics.*;
import static org.hasbro.transformers.resource.Allegiance.AUTOBOT;

public class PlayerMenuConsoleView implements PlayerMenuView {
    private MenuChooser<Gender> genderMenuChooser = new MenuChooser<>("Choose Gender", Gender.values());
    private Navigator navigator;

    private String playerName;
    private Gender gender;
    private Integer riflePower;
    private Float rifleAccuracy;
    private Integer swordPower;
    private Float swordAccuracy;
    private String transformedIdentity;
    private Integer transformedPowerImpact;

    @Override
    public void show() {
        playerName = NAME_QUESTION.ask();

        genderMenuChooser.load();
        gender = genderMenuChooser.getItem();

        riflePower = Integer.valueOf(RIFLE_POWER_QUESTION.ask());
        rifleAccuracy = Float.valueOf(RIFLE_ACCURACY_QUESTION.ask());
        swordPower = Integer.valueOf(SWORD_POWER_QUESTION.ask());
        swordAccuracy = Float.valueOf(SWORD_ACCURACY_QUESTION.ask());
        transformedIdentity = ALTERNATE_MODE_IDENTITY_QUESTION.ask();
        transformedPowerImpact = Integer.valueOf(ALTERNATE_MODE_POWER_IMPACT_QUESTION.ask());

        navigator.onPlayerCreated();
    }

    @Override
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public Cybertronian getPlayer() {
        requirePlayerDetails();

        return new CybertronResource(
            playerName,
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

    private void requirePlayerDetails() {
        requireNonNull(playerName);
        requireNonNull(gender);
        requireNonNull(riflePower);
        requireNonNull(rifleAccuracy);
        requireNonNull(swordPower);
        requireNonNull(swordAccuracy);
        requireNonNull(transformedIdentity);
        requireNonNull(transformedPowerImpact);
    }
}
