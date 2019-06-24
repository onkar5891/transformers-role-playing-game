package org.hasbro.transformers.activity.menu.player;

import org.hasbro.transformers.activity.Presenter;
import org.hasbro.transformers.activity.menu.battlefield.Battlefield;
import org.hasbro.transformers.factory.TransformersRPGFactory;
import org.hasbro.transformers.resource.Cybertronian;
import org.hasbro.transformers.utils.RPGSettings;

public class PlayerMenuPresenter implements Presenter, PlayerMenuView.Navigator {
    private PlayerMenuView playerMenuView;

    public PlayerMenuPresenter(PlayerMenuView playerMenuView) {
        this.playerMenuView = playerMenuView;
        this.playerMenuView.setNavigator(this);
    }

    @Override
    public void init() {
        playerMenuView.show();
    }

    @Override
    public void onPlayerCreated() {
        Cybertronian player = playerMenuView.getPlayer();
        Battlefield battleField = RPGSettings.createBattlefield(player);
        TransformersRPGFactory.playerMovementPresenter(battleField).init();
    }
}
