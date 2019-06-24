package org.hasbro.transformers.activity.menu.main;

import org.hasbro.transformers.activity.View;

interface MainMenuView extends View<MainMenuView.Navigator> {
    interface Navigator {
        void onGameStart();

        void onGameLoad();
    }
}
