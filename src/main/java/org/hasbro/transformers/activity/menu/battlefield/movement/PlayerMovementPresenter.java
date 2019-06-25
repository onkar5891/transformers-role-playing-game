package org.hasbro.transformers.activity.menu.battlefield.movement;

import org.hasbro.transformers.activity.Presenter;
import org.hasbro.transformers.activity.menu.battlefield.Battlefield;
import org.hasbro.transformers.activity.menu.player.Position;
import org.hasbro.transformers.questionnaire.Question;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hasbro.transformers.utils.MessageReader.readFromFile;
import static org.hasbro.transformers.utils.RPGSettings.shouldPrettyPrint;
import static org.hasbro.transformers.utils.StreamUtils.evaluateTernaryViaSupplier;

public class PlayerMovementPresenter implements Presenter, PlayerMovementView.Navigator {
    private final Battlefield battleField;
    private final PlayerMovementView playerMovementView;

    public PlayerMovementPresenter(PlayerMovementConsoleView playerMovementView, Battlefield battleField) {
        this.battleField = battleField;
        this.playerMovementView = playerMovementView;
        this.playerMovementView.setNavigator(this);
    }

    @Override
    public void init() {
        playerMovementView.show();

        if (battleField.isPlayerAlive()) {
            loadBattleWonMessage();
        } else {
            loadBattleLostMessage();
        }
        new Question<>("\nPress any key to continue", t -> true).ask();
    }

    @Override
    public void onMovedUp(Position currentPosition) {
        Position newPosition = new Position(currentPosition.getRow() - 1, currentPosition.getColumn());
        battleField.movePlayer(currentPosition, newPosition);
    }

    @Override
    public void onMovedDown(Position currentPosition) {
        Position newPosition = new Position(currentPosition.getRow() + 1, currentPosition.getColumn());
        battleField.movePlayer(currentPosition, newPosition);
    }

    @Override
    public void onMovedLeft(Position currentPosition) {
        Position newPosition = new Position(currentPosition.getRow(), currentPosition.getColumn() - 1);
        battleField.movePlayer(currentPosition, newPosition);
    }

    @Override
    public void onMovedRight(Position currentPosition) {
        Position newPosition = new Position(currentPosition.getRow(), currentPosition.getColumn() + 1);
        battleField.movePlayer(currentPosition, newPosition);
    }

    private void loadBattleWonMessage() {
        Path path = evaluateTernaryViaSupplier(
            () -> Paths.get("src/main/resources/battle-won-pretty-print.txt"),
            () -> Paths.get("src/main/resources/battle-won.txt"),
            p -> shouldPrettyPrint(),
            null
        );

        readFromFile(path, "You won the battle!");
    }

    private void loadBattleLostMessage() {
        Path path = evaluateTernaryViaSupplier(
            () -> Paths.get("src/main/resources/battle-lost-pretty-print.txt"),
            () -> Paths.get("src/main/resources/battle-lost.txt"),
            p -> shouldPrettyPrint(),
            null
        );

        readFromFile(path, "You lost the battle!");
    }
}
