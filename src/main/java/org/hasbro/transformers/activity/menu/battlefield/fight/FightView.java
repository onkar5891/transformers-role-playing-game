package org.hasbro.transformers.activity.menu.battlefield.fight;

import org.hasbro.transformers.activity.View;

interface FightView extends View<FightView.Navigator> {
    void setFightStrategy(FightStrategy fightStrategy);

    interface Navigator {
        void onAttack();

        void onDefense();

        void onRetreat();

        void onStayStill();
    }
}
