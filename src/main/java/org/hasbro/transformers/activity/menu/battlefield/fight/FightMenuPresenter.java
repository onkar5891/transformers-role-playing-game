package org.hasbro.transformers.activity.menu.battlefield.fight;

import org.hasbro.transformers.activity.Presenter;
import org.hasbro.transformers.resource.Cybertronian;

import static java.text.MessageFormat.format;
import static org.hasbro.transformers.factory.TransformersRPGFactory.randomSupplier;
import static org.hasbro.transformers.utils.MessageWriter.ColorMode.*;
import static org.hasbro.transformers.utils.MessageWriter.*;

public class FightMenuPresenter implements Presenter, FightView.Navigator, FightStrategy {
    private Cybertronian player;
    private Cybertronian enemy;
    private FightView fightView;

    public FightMenuPresenter(FightView fightView, Cybertronian player, Cybertronian enemy) {
        this.player = player;
        this.enemy = enemy;
        this.fightView = fightView;
        this.fightView.setNavigator(this);
        this.fightView.setFightStrategy(this);
    }

    @Override
    public void init() {
        prettyPrintln(CYAN, "\n--- A BATTLE HAS BEGUN ---");

        String playerInfo = format("{0} {1} ({2})", player.getGender(), player.getName(), player.getHealth());
        String enemyInfo = format("{0} {1} ({2})", enemy.getGender(), enemy.getName(), enemy.getHealth());
        String battleInfo = String.join(" VS ", coloredMessage(GREEN, playerInfo), coloredMessage(RED, enemyInfo));
        println(battleInfo);

        fightView.show();
    }

    @Override
    public void onAttack() {
        boolean enemyDefenseModeSet = tryEnemyDefenseOnPlayerAttack();

        player.attack(enemy);

        if (enemyDefenseModeSet) {
            enemy.defend();
        }
        enemy.attack(player);
    }

    @Override
    public void onDefense() {
        player.defend();
        enemy.attack(player);
    }

    @Override
    public void onRetreat() {
        player.retreat();
        enemy.attack(player);
    }

    @Override
    public void onStayStill() {
        enemy.attack(player);
    }

    @Override
    public boolean shouldBattleContinue() {
        return player.isAlive() && enemy.isAlive() && !player.hasRetreated();
    }

    private boolean tryEnemyDefenseOnPlayerAttack() {
        FightMode cpuFightMode = randomSupplier().fromArray(FightMode.values());
        if (cpuFightMode == FightMode.DEFENSE) {
            enemy.defend();
            return true;
        }
        return false;
    }
}
