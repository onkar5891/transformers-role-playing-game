package org.hasbro.transformers.activity.menu.battlefield.movement;

import org.hasbro.transformers.activity.menu.battlefield.Battlefield;
import org.hasbro.transformers.activity.menu.player.Position;
import org.hasbro.transformers.resource.Cybertronian;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.hasbro.transformers.RPGTestData.anAutobot;
import static org.hasbro.transformers.RPGTestData.createStructureOneOnOne;
import static org.hasbro.transformers.utils.RPGSettings.megatron;
import static org.junit.Assert.assertEquals;

public class PlayerMovementPresenterTest {
    private static final int OFFSET = 5;
    private Battlefield battlefield;
    private int totalRowsInBattlefield;
    private int totalColumnsInBattlefield;
    private PlayerMovementView.Navigator playerMovementPresenter;

    @Before
    public void setUp() {
        Cybertronian player = anAutobot();
        Cybertronian decepticon = megatron();

        List<List<Cybertronian>> resources = createStructureOneOnOne(player, decepticon);
        battlefield = Battlefield.restore(resources);

        totalRowsInBattlefield = resources.size();
        totalColumnsInBattlefield = resources.get(1).size();
        playerMovementPresenter = new PlayerMovementPresenter(new PlayerMovementConsoleView(battlefield), battlefield);
    }

    @Test
    public void playerShouldNotMoveOutOfTheBattlefieldInUpwardDirection() {
        Position currentPosition = battlefield.findPlayerPosition();
        IntStream.rangeClosed(1, totalRowsInBattlefield + OFFSET).forEach(row -> playerMovementPresenter.onMovedUp(currentPosition));

        Position newPosition = battlefield.findPlayerPosition();
        assertEquals(0, newPosition.getRow());
        assertEquals(1, newPosition.getColumn());
    }

    @Test
    public void playerShouldNotMoveOutOfTheBattlefieldInDownwardDirection() {
        Position currentPosition = battlefield.findPlayerPosition();
        IntStream.rangeClosed(1, totalRowsInBattlefield + OFFSET).forEach(row -> playerMovementPresenter.onMovedDown(currentPosition));

        Position newPosition = battlefield.findPlayerPosition();
        assertEquals(2, newPosition.getRow());
        assertEquals(1, newPosition.getColumn());
    }

    @Test
    public void playerShouldNotMoveOutOfTheBattlefieldInLeftDirection() {
        Position currentPosition = battlefield.findPlayerPosition();
        IntStream.rangeClosed(1, totalColumnsInBattlefield + OFFSET).forEach(row -> playerMovementPresenter.onMovedLeft(currentPosition));

        Position newPosition = battlefield.findPlayerPosition();
        assertEquals(1, newPosition.getRow());
        assertEquals(0, newPosition.getColumn());
    }

    @Test
    public void playerShouldNotMoveOutOfTheBattlefieldInRightDirection() {
        Position currentPosition = battlefield.findPlayerPosition();
        IntStream.rangeClosed(1, totalColumnsInBattlefield + OFFSET).forEach(row -> playerMovementPresenter.onMovedRight(currentPosition));

        Position newPosition = battlefield.findPlayerPosition();
        assertEquals(1, newPosition.getRow());
        assertEquals(2, newPosition.getColumn());
    }
}
