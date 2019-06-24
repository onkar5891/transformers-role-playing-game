package org.hasbro.transformers.activity.menu.battlefield.movement;

import org.hasbro.transformers.activity.menu.battlefield.Battlefield;
import org.hasbro.transformers.activity.menu.player.Position;
import org.hasbro.transformers.questionnaire.MenuChooser;
import org.hasbro.transformers.questionnaire.StateSaverMenuChooser;

public class PlayerMovementConsoleView implements PlayerMovementView {
    private final MenuChooser<Movement> movementMenuChooser;
    private Battlefield battleField;
    private Navigator navigator;

    public PlayerMovementConsoleView(Battlefield battleField) {
        this.movementMenuChooser = new StateSaverMenuChooser<>("Choose where to move", battleField, Movement.values());
        this.battleField = battleField;
    }

    @Override
    public void show() {
        do {
            battleField.show();
            movementMenuChooser.load();

            Position currentPosition = battleField.findPlayerPosition();
            Movement movement = movementMenuChooser.getItem();

            switch (movement) {
                case UP:
                    navigator.onMovedUp(currentPosition);
                    break;
                case DOWN:
                    navigator.onMovedDown(currentPosition);
                    break;
                case LEFT:
                    navigator.onMovedLeft(currentPosition);
                    break;
                case RIGHT:
                    navigator.onMovedRight(currentPosition);
                    break;
            }
        } while (battleField.shouldBattleContinue());
    }

    @Override
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }
}
