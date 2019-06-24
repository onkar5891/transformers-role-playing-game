package org.hasbro.transformers.resource;

import org.hasbro.transformers.activity.menu.player.Gender;

import java.io.Serializable;
import java.util.List;

public interface Cybertronian extends Serializable {
    String getName();

    // Name of Cybertronians may be big or small
    // Due to this, placing them on battlefield affects the layout
    // Prettier name should pad (if small) or truncate (if big) for better view on battlefield
    String getPrettierName();

    Gender getGender();

    // For live Cybertronians (or Transformers), it must be either AUTOBOT or DECEPTICON
    // But there are some resources for which it may be NOT_APPLICABLE (E.g.: FREE_PATH, JET)
    Allegiance getAllegiance();

    // Transformers attack with these weapons
    List<Weapon> getWeapons();

    int getHealth();

    // If transformer kills the opponent, health reward should be given
    void boostHealth();

    void attack(Cybertronian prey);

    // Every transformer has alternate mode, by which a deadly attack can be made on the opponent
    AlternateMode transform();

    // If transformer is attacked, health needs to be reduced
    void impactedBy(int attackPower);

    boolean isAlive();

    // Set the defense mode of transformer to void opponents attack
    void defend();

    boolean isDefended();

    void backToAttackingPosition();

    // If transformer thinks to retreat due to superpower of opponents, he can leave current battle
    void retreat();

    boolean hasRetreated();

    void prepareForBattle();

    // Identifies if battle has to begin if they collide with each other
    boolean isEnemyOf(Cybertronian cybertronian);

    // Identifies actual player
    boolean isControlledByPlayer();

    // Cybertronian resources (other than transformers), which can block the path of player
    // In this case, player needs to figure out alternate path
    boolean blocksPlayer();

    // Defines alternate mode details of transformers
    interface AlternateMode extends Serializable {
        String getIdentity();

        int powerImpact();
    }
}
