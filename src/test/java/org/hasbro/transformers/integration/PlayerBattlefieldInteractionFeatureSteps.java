package org.hasbro.transformers.integration;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.hasbro.transformers.activity.menu.battlefield.Battlefield;
import org.hasbro.transformers.activity.menu.battlefield.fight.FightMode;
import org.hasbro.transformers.activity.menu.battlefield.movement.Movement;
import org.hasbro.transformers.activity.menu.battlefield.movement.PlayerMovementConsoleView;
import org.hasbro.transformers.activity.menu.battlefield.movement.PlayerMovementPresenter;
import org.hasbro.transformers.activity.menu.player.Position;
import org.hasbro.transformers.resource.CybertronResource;
import org.hasbro.transformers.resource.Cybertronian;
import org.hasbro.transformers.utils.StreamUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hasbro.transformers.RPGTestData.*;
import static org.hasbro.transformers.integration.FeatureUtils.populateStream;
import static org.hasbro.transformers.integration.FeatureUtils.resetStream;
import static org.hasbro.transformers.utils.MessageReader.resetScanner;
import static org.hasbro.transformers.utils.RPGSettings.freePath;
import static org.junit.Assert.*;

public class PlayerBattlefieldInteractionFeatureSteps implements En {
    private final Cybertronian player = anAutobot();
    private final Cybertronian legendaryPlayer = aLegendaryAutobot();
    private final CybertronResource amateurDecepticon = anAmateurDecepticon();
    private final CybertronResource decepticon = aDecepticon();
    private final CybertronResource legendaryDecepticon = aLegendaryDecepticon();
    private Battlefield battleField;

    public PlayerBattlefieldInteractionFeatureSteps() {
        Given(
            "^A one on one battlefield$",
            this::createOneOnOneBattlefield
        );

        Given(
            "^A one on one battlefield with amateur decepticon and normal autobot$",
            this::createOneOnOneBattlefieldWithAmateurDecepticonAndNormalAutobot
        );

        Given(
            "^A one on one battlefield with legendary decepticon and normal autobot$",
            this::createOneOnOneBattlefieldWithLegendaryDecepticonAndNormalAutobot
        );

        When(
            "^Player moves on the battlefield with sequences \"([^\"]*)\"$",
            this::movePlayerOnTheBattlefield
        );

        When(
            "^Player moves next to the enemy with sequences \"([^\"]*)\" and consistently attacks the enemy who is on \"([^\"]*)\" side$",
            this::forcePlayerToConsistentlyAttackTheEnemy
        );

        When(
            "^Player moves with sequences \"([^\"]*)\" and consistently stays still next to enemy who is on \"([^\"]*)\" side$",
            this::forcePlayerToConsistentlyStaysStill
        );

        Then(
            "^Final position of the player is Row: \"([^\"]*)\", Column: \"([^\"]*)\"$",
            this::assertFinalPositionOfPlayer
        );

        Then(
            "^Player should defeat the enemy$",
            this::assertPlayerWins
        );

        Then(
            "^Player should be defeated by the enemy$",
            this::assertPlayerLooses
        );
    }

    @Before
    public void beforeScenario() {
        resetScanner();
    }

    @After
    public void afterScenario() {
        resetStream();
    }

    private void createOneOnOneBattlefield() {
        battleField = Battlefield.restore(
            Arrays.asList(
                Arrays.asList(decepticon, freePath(), freePath()),
                Arrays.asList(freePath(), player, freePath()),
                Arrays.asList(freePath(), freePath(), freePath())
            )
        );
    }

    private void createOneOnOneBattlefieldWithAmateurDecepticonAndNormalAutobot() {
        battleField = Battlefield.restore(
            Arrays.asList(
                Arrays.asList(amateurDecepticon, freePath(), freePath()),
                Arrays.asList(freePath(), legendaryPlayer, freePath()),
                Arrays.asList(freePath(), freePath(), freePath())
            )
        );
    }

    private void createOneOnOneBattlefieldWithLegendaryDecepticonAndNormalAutobot() {
        battleField = Battlefield.restore(
            Arrays.asList(
                Arrays.asList(legendaryDecepticon, freePath(), freePath()),
                Arrays.asList(freePath(), player, freePath()),
                Arrays.asList(freePath(), freePath(), freePath())
            )
        );
    }

    private void movePlayerOnTheBattlefield(String sequences) {
        String[] splitSequences = sequences.split(",");
        Map<String, Integer> mappedMenuOptions = StreamUtils.mapAsMenuOptions(Movement.values());

        String joined = String.join(System.lineSeparator(), Stream.of(splitSequences).map(seq -> mappedMenuOptions.get(seq).toString()).toArray(String[]::new));
        populateStream(joined);

        try {
            new PlayerMovementPresenter(new PlayerMovementConsoleView(battleField), battleField).init();
        } catch (NoSuchElementException e) {
            // Do nothing, end of test data input
        }
    }

    private void forcePlayerToConsistentlyAttackTheEnemy(String sequences, String enemyPosition) {
        String[] splitSequences = sequences.split(",");
        String[] playerFightModes = { "ATTACK", "ATTACK" };
        Stream<String> consoleInputs = prepareConsoleStream(enemyPosition, splitSequences, playerFightModes);

        populateStream(consoleInputs.collect(Collectors.joining(System.lineSeparator())));

        try {
            new PlayerMovementPresenter(new PlayerMovementConsoleView(battleField), battleField).init();
        } catch (NoSuchElementException e) {
            // Do nothing, end of test data input
        }
    }

    private void forcePlayerToConsistentlyStaysStill(String sequences, String enemyPosition) {
        String[] splitSequences = sequences.split(",");
        String[] playerFightModes = { "STAY_STILL", "STAY_STILL" };
        Stream<String> consoleInputs = prepareConsoleStream(enemyPosition, splitSequences, playerFightModes);

        populateStream(consoleInputs.collect(Collectors.joining(System.lineSeparator())));

        try {
            new PlayerMovementPresenter(new PlayerMovementConsoleView(battleField), battleField).init();
        } catch (NoSuchElementException e) {
            // Do nothing, end of test data input
        }
    }

    private void assertFinalPositionOfPlayer(int row, int column) {
        Position playerPosition = battleField.findPlayerPosition();
        assertEquals(row, playerPosition.getRow());
        assertEquals(column, playerPosition.getColumn());
    }

    private void assertPlayerWins() {
        assertTrue(legendaryPlayer.isAlive());
        assertFalse(amateurDecepticon.isAlive());
    }

    private void assertPlayerLooses() {
        assertFalse(player.isAlive());
        assertTrue(legendaryDecepticon.isAlive());
    }

    private Stream<String> prepareConsoleStream(String enemyPosition, String[] splitSequences, String[] playerFightModes) {
        Map<String, Integer> mappedMenuOptions = StreamUtils.mapAsMenuOptions(Movement.values());
        Map<String, Integer> mappedFightModes = StreamUtils.mapAsMenuOptions(FightMode.values());
        return Stream.of(
            // Move to next of the enemy
            String.join(System.lineSeparator(), Stream.of(splitSequences).map(seq -> mappedMenuOptions.get(seq).toString()).toArray(String[]::new)),
            // Move on to the position of enemy
            String.join(System.lineSeparator(), mappedMenuOptions.get(enemyPosition).toString()),
            // Fight
            String.join(System.lineSeparator(), Stream.of(playerFightModes).map(seq -> mappedFightModes.get(seq).toString()).toArray(String[]::new))
        );
    }
}
