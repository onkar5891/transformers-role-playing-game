package org.hasbro.transformers.activity.menu.battlefield.movement;

import org.hasbro.transformers.activity.View;
import org.hasbro.transformers.activity.menu.player.Position;

interface PlayerMovementView extends View<PlayerMovementView.Navigator> {
    interface Navigator {
        void onMovedUp(Position currentPosition);

        void onMovedDown(Position currentPosition);

        void onMovedLeft(Position currentPosition);

        void onMovedRight(Position currentPosition);
    }
}
