package org.hasbro.transformers.activity.menu.battlefield.fight;

import org.hasbro.transformers.activity.menu.battlefield.Battlefield;
import org.hasbro.transformers.questionnaire.MenuChooser;
import org.hasbro.transformers.questionnaire.StateSaverMenuChooser;

public class FightConsoleView implements FightView {
    private final MenuChooser<FightMode> attackModeMenuChooser;
    private FightStrategy fightStrategy;
    private Navigator navigator;

    public FightConsoleView(Battlefield battleField) {
        attackModeMenuChooser = new StateSaverMenuChooser<>("Choose Fight Mode", battleField, FightMode.values());
    }

    @Override
    public void show() {
        do {
            attackModeMenuChooser.load();
            FightMode fightMode = attackModeMenuChooser.getItem();

            switch (fightMode) {
                case ATTACK:
                    navigator.onAttack();
                    break;
                case DEFENSE:
                    navigator.onDefense();
                    break;
                case STAY_STILL:
                    navigator.onStayStill();
                    break;
                case RETREAT:
                    navigator.onRetreat();
                    break;
            }
        } while (fightStrategy.shouldBattleContinue());
    }

    @Override
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void setFightStrategy(FightStrategy fightStrategy) {
        this.fightStrategy = fightStrategy;
    }
}
