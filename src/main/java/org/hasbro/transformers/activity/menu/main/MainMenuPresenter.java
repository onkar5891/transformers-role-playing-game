package org.hasbro.transformers.activity.menu.main;

import org.hasbro.transformers.activity.Presenter;
import org.hasbro.transformers.activity.menu.battlefield.Battlefield;
import org.hasbro.transformers.factory.TransformersRPGFactory;
import org.hasbro.transformers.utils.RPGSettings;

import static org.hasbro.transformers.utils.MessageWriter.println;

public class MainMenuPresenter implements Presenter, MainMenuView.Navigator {
    private MainMenuView view;

    public MainMenuPresenter(MainMenuView view) {
        this.view = view;
        this.view.setNavigator(this);
    }

    @Override
    public void init() {
        view.show();
    }

    @Override
    public void onGameStart() {
        TransformersRPGFactory.playerMenuPresenter().init();
    }

    @Override
    public void onGameLoad() {
        try {
            Battlefield battleField = RPGSettings.restoreBattlefield();
            TransformersRPGFactory.playerMovementPresenter(battleField).init();
        } catch (IllegalStateException e) {
            println("Could not load battlefield state");
        }
    }
}
