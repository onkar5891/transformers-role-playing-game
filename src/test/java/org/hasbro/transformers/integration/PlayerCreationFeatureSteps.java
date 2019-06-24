package org.hasbro.transformers.integration;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.hasbro.transformers.activity.menu.battlefield.movement.PlayerMovementPresenter;
import org.hasbro.transformers.activity.menu.player.Gender;
import org.hasbro.transformers.activity.menu.player.PlayerMenuConsoleView;
import org.hasbro.transformers.activity.menu.player.PlayerMenuPresenter;
import org.hasbro.transformers.factory.TransformersRPGFactory;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.hasbro.transformers.utils.MessageReader.resetScanner;
import static org.hasbro.transformers.utils.StreamUtils.mapAsMenuOptions;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class PlayerCreationFeatureSteps {
    private PlayerMenuPresenter playerMenuPresenter;

    @Mock
    private PlayerMovementPresenter playerMovementPresenter;

    @Before
    public void beforeScenario() {
        MockitoAnnotations.initMocks(this);
        resetScanner();
    }

    @After
    public void afterScenario() {
        System.setIn(System.in);
    }

    @When("Player's incomplete information is given")
    public void incompletePlayerInformation(DataTable dataTable) {
        Map<String, String> playerMappings = dataTable.asMap(String.class, String.class);
        String[] playerCreationInputs = createIncompleteInputsFrom(playerMappings);
        triggerPlayerMenuFlow(playerCreationInputs);
    }

    @Then("Placing the player on battlefield should be erroneous")
    public void tryBeginningTheBattle() {
        boolean erroneous = false;
        try {
            playerMenuPresenter.onPlayerCreated();
        } catch (NullPointerException e) {
            erroneous = true;
        }
        assertTrue(erroneous);
    }

    @When("Player's complete information is given")
    public void playerSCompleteInformationIsGiven(DataTable dataTable) {
        PowerMockito.mockStatic(TransformersRPGFactory.class);
        PowerMockito.when(TransformersRPGFactory.playerMovementPresenter(any())).thenReturn(playerMovementPresenter);

        Map<String, String> playerMappings = dataTable.asMap(String.class, String.class);
        String[] playerCreationInputs = createCompleteInputsFrom(playerMappings);
        triggerPlayerMenuFlow(playerCreationInputs);
    }

    @Then("Placing the player on battlefield should trigger battlefield view")
    public void placingThePlayerOnBattlefieldShouldTriggerBattlefieldView() {
        verify(playerMovementPresenter).init();
    }

    private void triggerPlayerMenuFlow(String[] playerCreationInputs) {
        String joined = String.join(System.lineSeparator(), playerCreationInputs);
        ByteArrayInputStream interactiveStream = new ByteArrayInputStream(joined.getBytes());
        System.setIn(interactiveStream);

        try {
            playerMenuPresenter = new PlayerMenuPresenter(new PlayerMenuConsoleView());
            playerMenuPresenter.init();
        } catch (NoSuchElementException e) {
            // End of test data
        }
    }

    private String[] createIncompleteInputsFrom(Map<String, String> playerMappings) {
        Map<String, Integer> genderMappings = mapAsMenuOptions(Gender.values());
        String genderValue = genderMappings.get(playerMappings.get("Gender")).toString();

        return new String[] {
            playerMappings.get("Name"),
            genderValue,
            playerMappings.get("Rifle Power"),
            playerMappings.get("Rifle Accuracy"),
            playerMappings.get("Sword Power")
        };
    }

    private String[] createCompleteInputsFrom(Map<String, String> playerMappings) {
        Map<String, Integer> genderMappings = mapAsMenuOptions(Gender.values());
        String genderValue = genderMappings.get(playerMappings.get("Gender")).toString();

        return new String[] {
            playerMappings.get("Name"),
            genderValue,
            playerMappings.get("Rifle Power"),
            playerMappings.get("Rifle Accuracy"),
            playerMappings.get("Sword Power"),
            playerMappings.get("Sword Accuracy"),
            playerMappings.get("Alternate Mode Identity"),
            playerMappings.get("Alternate Mode Power Impact")
        };
    }
}
