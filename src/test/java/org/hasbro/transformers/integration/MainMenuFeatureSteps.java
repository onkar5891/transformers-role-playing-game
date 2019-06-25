package org.hasbro.transformers.integration;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.hasbro.transformers.activity.menu.battlefield.movement.PlayerMovementPresenter;
import org.hasbro.transformers.activity.menu.main.MainMenu;
import org.hasbro.transformers.activity.menu.main.MainMenuConsoleView;
import org.hasbro.transformers.activity.menu.main.MainMenuPresenter;
import org.hasbro.transformers.activity.menu.player.PlayerMenuPresenter;
import org.hasbro.transformers.factory.TransformersRPGFactory;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.hasbro.transformers.integration.FeatureUtils.populateStream;
import static org.hasbro.transformers.integration.FeatureUtils.resetStream;
import static org.hasbro.transformers.utils.MessageReader.resetScanner;
import static org.hasbro.transformers.utils.StreamUtils.mapAsMenuOptions;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainMenuFeatureSteps implements En {
    @Mock
    private PlayerMovementPresenter playerMovementPresenter;
    @Mock
    private PlayerMenuPresenter playerMenuPresenter;

    public MainMenuFeatureSteps() {
        Given(
            "^Battle start mode is mocked$",
            this::mockBattleStartMode
        );

        Given(
            "^Battle load mode is mocked$",
            this::mockBattleLoadMode
        );

        When(
            "^Player chooses to \"([^\"]*)\" battle$",
            this::choosePlayerBattleType
        );

        Then(
            "^Player should be redirected to player creation menu$",
            () -> verify(playerMenuPresenter).init()
        );

        Then(
            "^Player should be redirected to battlefield view$",
            () -> verify(playerMovementPresenter).init()
        );

        And(
            "^A Sample battle was previously saved$",
            this::saveSampleBattle
        );
    }

    private void saveSampleBattle() {
        List<String> inputs = Arrays.asList(
            "1",
            "Optimus",
            "1",
            "20",
            "1",
            "30",
            "1",
            "HMV",
            "60",
            "0",
            System.lineSeparator()
        );

        String joined = String.join(System.lineSeparator(), inputs);
        populateStream(joined);

        when(TransformersRPGFactory.playerMenuPresenter()).thenCallRealMethod();
        when(TransformersRPGFactory.playerMovementPresenter(any())).thenCallRealMethod().thenReturn(playerMovementPresenter);
        try {
            new MainMenuPresenter(new MainMenuConsoleView()).init();
        } catch (NoSuchElementException e) {
            resetScanner();
        }
    }

    @Before
    public void beforeScenario() {
        MockitoAnnotations.initMocks(this);
        resetScanner();
    }

    @After
    public void afterScenario() {
        resetStream();
    }

    private void mockBattleLoadMode() {
        PowerMockito.mockStatic(TransformersRPGFactory.class);
        when(TransformersRPGFactory.battleFieldSerializer()).thenCallRealMethod();
    }

    private void mockBattleStartMode() {
        PowerMockito.mockStatic(TransformersRPGFactory.class);
        when(TransformersRPGFactory.playerMenuPresenter()).thenReturn(playerMenuPresenter);
    }

    private void choosePlayerBattleType(String menuOption) {
        Map<String, Integer> mainMenuMappings = mapAsMenuOptions(MainMenu.values());
        String joined = mainMenuMappings.get(menuOption).toString() + System.lineSeparator();
        populateStream(joined);

        try {
            new MainMenuPresenter(new MainMenuConsoleView()).init();
        } catch (NoSuchElementException e) {
            // Do nothing, end of test data input
        }
    }
}
