package org.hasbro.transformers.resource;

import org.hasbro.transformers.utils.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hasbro.transformers.RPGTestData.aDecepticon;
import static org.hasbro.transformers.RPGTestData.anAutobot;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RandomUtils.class)
public class CybertronResourceTest {
    private Cybertronian player;
    private Cybertronian enemy;

    @Before
    public void setUp() {
        player = anAutobot();
        enemy = aDecepticon();

        PowerMockito.mockStatic(RandomUtils.class);
    }

    @Test
    public void deadPlayerShouldNotBeAbleToAttack() {
        // Killing the player
        player.impactedBy(100);

        player.attack(enemy);
        assertEquals(100, enemy.getHealth());
    }

    @Test
    public void playerShouldNotBeAbleToAttackWhileDefending() {
        player.defend();
        player.attack(enemy);
        assertEquals(100, enemy.getHealth());
    }

    @Test
    public void healthOfEnemyShouldNotBeReducedWhenEnemyIsDefending() {
        RandomUtils randomUtils = mockRandomUtils();
        when(randomUtils.yesOrNo()).thenReturn(false);
        when(randomUtils.fromList(anyList())).thenReturn(player.getWeapons().get(0));

        enemy.defend();
        player.attack(enemy);
        assertEquals(100, enemy.getHealth());
    }

    @Test
    public void healthOfEnemyShouldBeReducedWhenEnemyIsInAttackMode() {
        RandomUtils randomUtils = mockRandomUtils();
        when(randomUtils.yesOrNo()).thenReturn(false);
        when(randomUtils.fromList(anyList())).thenReturn(player.getWeapons().get(0));

        player.attack(enemy);
        assertEquals(76, enemy.getHealth());
    }

    @Test
    public void healthOfEnemyShouldBeDrasticallyReducedDueToTransformation() {
        RandomUtils randomUtils = mockRandomUtils();
        when(randomUtils.yesOrNo()).thenReturn(true);

        player.attack(enemy);
        assertEquals(50, enemy.getHealth());
    }

    @Test
    public void healthOfPlayerShouldBeBoostedAfterEnemyIsKilled() {
        RandomUtils randomUtils = mockRandomUtils();
        when(randomUtils.yesOrNo()).thenReturn(true);

        player.impactedBy(40);
        enemy.impactedBy(50);

        player.attack(enemy);
        assertFalse(enemy.isAlive());
        assertEquals(85, player.getHealth());
    }

    private RandomUtils mockRandomUtils() {
        RandomUtils randomUtils = mock(RandomUtils.class);
        when(RandomUtils.getInstance()).thenReturn(randomUtils);
        return randomUtils;
    }
}
