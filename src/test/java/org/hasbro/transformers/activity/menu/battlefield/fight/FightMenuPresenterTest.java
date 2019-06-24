package org.hasbro.transformers.activity.menu.battlefield.fight;

import org.hasbro.transformers.resource.Cybertronian;
import org.hasbro.transformers.utils.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RandomUtils.class)
public class FightMenuPresenterTest {
    @Mock
    private Cybertronian player;
    @Mock
    private Cybertronian enemy;
    @Mock
    private FightView fightView;

    @InjectMocks
    private FightMenuPresenter fightMenuPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RandomUtils.class);
    }

    @Test
    public void shouldVerifyTheFlowOfPlayerAttackedButEnemyDefended() {
        RandomUtils randomUtils = mock(RandomUtils.class);
        when(RandomUtils.getInstance()).thenReturn(randomUtils);
        when(randomUtils.fromArray(any(FightMode[].class))).thenReturn(FightMode.DEFENSE);

        fightMenuPresenter.onAttack();

        verify(player).attack(enemy);
        verify(enemy, times(2)).defend();
        verify(enemy).attack(player);
    }

    @Test
    public void shouldVerifyTheFlowOfPlayerAttack() {
        RandomUtils randomUtils = mock(RandomUtils.class);
        when(RandomUtils.getInstance()).thenReturn(randomUtils);
        when(randomUtils.fromArray(any(FightMode[].class))).thenReturn(FightMode.ATTACK);

        fightMenuPresenter.onAttack();

        verify(player).attack(enemy);
        verify(enemy, times(0)).defend();
        verify(enemy).attack(player);
    }

    @Test
    public void shouldVarifyTheFlowOfPlayerDefense() {
        fightMenuPresenter.onDefense();

        verify(player).defend();
        verify(enemy).attack(player);
    }

    @Test
    public void shouldVarifyTheFlowOfPlayerStayStill() {
        fightMenuPresenter.onStayStill();

        verifyNoMoreInteractions(player);
        verify(enemy).attack(player);
    }

    @Test
    public void shouldVerifyTheFlowOfPlayerRetreat() {
        fightMenuPresenter.onRetreat();

        verify(player).retreat();
        verify(enemy).attack(player);
        assertFalse(fightMenuPresenter.shouldBattleContinue());
    }
}
