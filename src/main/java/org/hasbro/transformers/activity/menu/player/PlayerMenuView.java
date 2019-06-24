package org.hasbro.transformers.activity.menu.player;

import org.hasbro.transformers.activity.View;
import org.hasbro.transformers.resource.Cybertronian;

interface PlayerMenuView extends View<PlayerMenuView.Navigator> {
    Cybertronian getPlayer();

    interface Navigator {
        void onPlayerCreated();
    }
}
