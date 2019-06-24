package org.hasbro.transformers.activity.menu.main;

import org.hasbro.transformers.questionnaire.MenuChooser;
import org.hasbro.transformers.utils.MessageWriter;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hasbro.transformers.utils.MessageReader.readFromFile;
import static org.hasbro.transformers.utils.MessageWriter.prettyPrintln;
import static org.hasbro.transformers.utils.RPGSettings.shouldPrettyPrint;
import static org.hasbro.transformers.utils.StreamUtils.evaluateTernaryViaSupplier;

public class MainMenuConsoleView implements MainMenuView {
    private MenuChooser<MainMenu> mainMenuMenuChooser = new MenuChooser<>("Choose Battle Mode", MainMenu.values());
    private Navigator navigator;

    @Override
    public void show() {
        while (true) {
            loadStoryLine();
            mainMenuMenuChooser.load();
            MainMenu mainMenuOption = mainMenuMenuChooser.getItem();
            switch (mainMenuOption) {
                case START:
                    navigator.onGameStart();
                    break;
                case LOAD:
                    navigator.onGameLoad();
                    break;
                case EXIT:
                    prettyPrintln(MessageWriter.ColorMode.CYAN, "\nThank you!");
                    return;
            }
        }
    }

    private void loadStoryLine() {
        Path path = evaluateTernaryViaSupplier(
            () -> Paths.get("src/main/resources/story-line-pretty-print.txt"),
            () -> Paths.get("src/main/resources/story-line.txt"),
            p -> shouldPrettyPrint(),
            null
        );

        readFromFile(path, "Your Mission: Kill both the Decepticons and take the Cube.");
    }

    @Override
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }
}
