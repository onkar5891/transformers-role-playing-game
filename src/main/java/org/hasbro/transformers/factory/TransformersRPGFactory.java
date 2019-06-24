package org.hasbro.transformers.factory;

import org.hasbro.transformers.activity.Presenter;
import org.hasbro.transformers.activity.menu.battlefield.Battlefield;
import org.hasbro.transformers.activity.menu.battlefield.fight.FightConsoleView;
import org.hasbro.transformers.activity.menu.battlefield.fight.FightMenuPresenter;
import org.hasbro.transformers.activity.menu.battlefield.movement.PlayerMovementConsoleView;
import org.hasbro.transformers.activity.menu.battlefield.movement.PlayerMovementPresenter;
import org.hasbro.transformers.activity.menu.main.MainMenuConsoleView;
import org.hasbro.transformers.activity.menu.main.MainMenuPresenter;
import org.hasbro.transformers.activity.menu.player.PlayerMenuConsoleView;
import org.hasbro.transformers.activity.menu.player.PlayerMenuPresenter;
import org.hasbro.transformers.activity.state.BattlefieldStateSerializer;
import org.hasbro.transformers.activity.state.GameStateSerializer;
import org.hasbro.transformers.resource.Cybertronian;
import org.hasbro.transformers.utils.RandomUtils;

import java.util.List;

public class TransformersRPGFactory {

    private TransformersRPGFactory() {
        // No-arg constructor
    }

    public static Presenter mainMenuPresenter() {
        return new MainMenuPresenter(new MainMenuConsoleView());
    }

    public static Presenter characterPresenter() {
        return new PlayerMenuPresenter(new PlayerMenuConsoleView());
    }

    public static Presenter playerMovementPresenter(Battlefield battleField) {
        return new PlayerMovementPresenter(new PlayerMovementConsoleView(battleField), battleField);
    }

    public static Presenter fightMenuPresenter(Cybertronian player, Cybertronian enemy, Battlefield battleField) {
        return new FightMenuPresenter(new FightConsoleView(battleField), player, enemy);
    }

    public static RandomUtils randomSupplier() {
        return RandomUtils.getInstance();
    }

    public static GameStateSerializer<List<List<Cybertronian>>> battleFieldSerializer() {
        return BattlefieldStateSerializer.getInstance();
    }

}
