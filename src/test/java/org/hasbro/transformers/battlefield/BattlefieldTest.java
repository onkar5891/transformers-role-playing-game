package org.hasbro.transformers.battlefield;

import org.hasbro.transformers.activity.menu.battlefield.Battlefield;
import org.hasbro.transformers.activity.menu.player.Position;
import org.hasbro.transformers.resource.Cybertronian;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hasbro.transformers.RPGTestData.anAutobot;
import static org.hasbro.transformers.RPGTestData.createStructureOneOnOne;
import static org.hasbro.transformers.utils.MessageWriter.println;
import static org.hasbro.transformers.utils.RPGSettings.megatron;
import static org.hasbro.transformers.utils.RPGSettings.restoreBattlefield;
import static org.junit.Assert.*;

public class BattlefieldTest {
    private Battlefield battlefield;
    private Cybertronian player;
    private Cybertronian decepticon;

    @Before
    public void setUp() {
        player = anAutobot();
        decepticon = megatron();

        List<List<Cybertronian>> resources = createStructureOneOnOne(player, decepticon);
        battlefield = Battlefield.restore(resources);
    }

    @Test
    public void shouldContinueTheBattleIfOpponentsAreAlive() {
        assertTrue(battlefield.shouldBattleContinue());
    }

    @Test
    public void shouldNotContinueTheBattleIfPlayerIsDead() {
        player.impactedBy(100);
        assertFalse(battlefield.shouldBattleContinue());
    }

    @Test
    public void shouldNotContinueTheBattleIfAllDecepticonsAreDead() {
        decepticon.impactedBy(100);
        assertFalse(battlefield.shouldBattleContinue());
    }

    @Test
    public void shouldKnowTheStatusOfPlayerAliveness() {
        assertTrue(battlefield.isPlayerAlive());

        player.impactedBy(100);
        assertFalse(battlefield.isPlayerAlive());
    }

    @Test
    public void shouldSaveAndRestoreTheStateOfBattlefield() {
        Path dir = Paths.get("/tmp/transformers-rpg");
        createBaseDir(dir);

        Path path = Paths.get(dir.toString(), "test-transformers-state.rpg");

        battlefield.setPath(path);
        battlefield.saveState();

        Battlefield restoredBattlefield = restoreBattlefield(path);
        Position position = battlefield.findPlayerPosition();
        Position restoredPosition = restoredBattlefield.findPlayerPosition();
        assertEquals(position, restoredPosition);

        deleteStateFile(path);
    }

    private void deleteStateFile(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            println("Could not delete RPG state file");
        }
    }

    private void createBaseDir(Path dir) {
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            println("Could not create directories");
        }
    }
}
