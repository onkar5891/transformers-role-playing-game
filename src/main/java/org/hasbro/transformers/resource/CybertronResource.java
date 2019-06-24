package org.hasbro.transformers.resource;

import org.hasbro.transformers.activity.menu.player.Gender;
import org.hasbro.transformers.utils.RPGSettings;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.text.MessageFormat.format;
import static org.hasbro.transformers.factory.TransformersRPGFactory.randomSupplier;
import static org.hasbro.transformers.resource.Allegiance.*;
import static org.hasbro.transformers.utils.MessageWriter.ColorMode.GREEN;
import static org.hasbro.transformers.utils.MessageWriter.ColorMode.RED;
import static org.hasbro.transformers.utils.MessageWriter.*;
import static org.hasbro.transformers.utils.RPGSettings.shouldPrettyPrint;
import static org.hasbro.transformers.utils.StreamUtils.evaluateTernaryViaFunction;

public class CybertronResource implements Cybertronian {
    private static final int PRETTIER_DISPLAY_LIMIT = 15;

    private String name;
    private Gender gender;
    private Allegiance allegiance;
    private List<Weapon> weapons;
    private AlternateMode alternateMode;
    private boolean canDefend;
    private boolean player;

    private int health;
    private boolean defended;
    private boolean retreated;
    private boolean blocksPlayer;

    public CybertronResource(String name, Gender gender, Allegiance allegiance, List<Weapon> weapons, AlternateMode alternateMode, boolean canDefend, boolean player) {
        this.name = name;
        this.gender = gender;
        this.allegiance = allegiance;
        this.weapons = weapons;
        this.alternateMode = alternateMode;
        this.canDefend = canDefend;
        this.player = player;

        this.health = 100;
        this.defended = false;
        this.blocksPlayer = false;
    }

    public CybertronResource(String name, boolean blocksPlayer) {
        this.name = name;
        this.blocksPlayer = blocksPlayer;

        this.allegiance = NOT_APPLICABLE;
        this.weapons = Collections.emptyList();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPrettierName() {
        String paddedOrTruncatedName = paddedOrTruncated();
        if (shouldPrettyPrint()) {
            if (isAutobot()) {
                return coloredMessage(GREEN, paddedOrTruncatedName);
            } else if (isDecepticon()) {
                return coloredMessage(RED, paddedOrTruncatedName);
            }
        }
        return paddedOrTruncatedName;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public Allegiance getAllegiance() {
        return allegiance;
    }

    @Override
    public List<Weapon> getWeapons() {
        return weapons;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void boostHealth() {
        health = Math.min(100, health + RPGSettings.HEALTH_BOOSTER);
    }

    @Override
    public AlternateMode transform() {
        return alternateMode;
    }

    @Override
    public void impactedBy(int attackPower) {
        health = Math.max(0, health - attackPower);
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void defend() {
        if (canDefend) {
            defended = true;
        }
    }

    @Override
    public boolean isDefended() {
        return defended;
    }

    @Override
    public void backToAttackingPosition() {
        defended = false;
    }

    @Override
    public void retreat() {
        retreated = true;
    }

    @Override
    public boolean hasRetreated() {
        return retreated;
    }

    @Override
    public void prepareForBattle() {
        retreated = false;
    }

    @Override
    public boolean isEnemyOf(Cybertronian cybertronian) {
        return allegiance.isEnemyOf(cybertronian.getAllegiance());
    }

    @Override
    public boolean isControlledByPlayer() {
        return player;
    }

    @Override
    public boolean blocksPlayer() {
        return blocksPlayer;
    }

    @Override
    public void attack(Cybertronian prey) {
        if (cannotAttack(prey)) {
            return;
        }

        int attackImpact;
        boolean transform = randomSupplier().yesOrNo();
        if (transform) {
            attackImpact = attackInAlternateMode(prey);
        } else {
            attackImpact = attackInNormalMode(prey);
        }

        if (!inDefenseMode(prey)) {
            prey.impactedBy(attackImpact);
            prettyPrintln(GREEN, format("New Health of {0} ({1})", prey.getName(), prey.getHealth()));

            boostIfPreyKilled(prey);
        }
    }

    private boolean inDefenseMode(Cybertronian prey) {
        if (prey.isDefended()) {
            println();
            prettyPrintln(GREEN, format("But look what! {0} has defended", prey.getName()));
            prey.backToAttackingPosition();
            return true;
        }
        return false;
    }

    private int attackInNormalMode(Cybertronian prey) {
        int power;
        Weapon weapon = randomSupplier().fromList(weapons);
        power = weapon.getPowerImpact();
        prettyPrintln(RED, format("\n{0} ({1}) attacked {2} ({3}) with {4}", this.name, this.health, prey.getName(), prey.getHealth(), weapon));
        return power;
    }

    private int attackInAlternateMode(Cybertronian prey) {
        AlternateMode transformedMode = transform();
        prettyPrintln(RED, format("\n{0} thought to TRANSFORM as {1}", this.name, transformedMode.getIdentity()));
        prettyPrintln(RED, format("DEADLY! {0} ({1}) attacked {2} ({3}) BY TRANSFORMING", transformedMode.getIdentity(), this.health, prey.getName(), prey.getHealth()));
        return transformedMode.powerImpact();
    }

    private void boostIfPreyKilled(Cybertronian prey) {
        if (!prey.isAlive()) {
            prettyPrintln(RED, format("\n{0} is dead", prey.getName()));
            boostHealth();
            prettyPrintln(GREEN, format("Health of {0} got boosted by {1}, New Health is {2}", this.name, RPGSettings.HEALTH_BOOSTER, this.health));
        }
    }

    private boolean cannotAttack(Cybertronian prey) {
        if (!isAlive()) {
            return true;
        } else if (isDefended()) {
            prettyPrintln(GREEN, format("{0} cannot attack {1} while defending", name, prey.getName()));
            backToAttackingPosition();
            return true;
        }
        return false;
    }

    private String paddedOrTruncated() {
        return evaluateTernaryViaFunction(
            cName -> cName.substring(0, PRETTIER_DISPLAY_LIMIT),
            cName -> cName + IntStream.rangeClosed(1, PRETTIER_DISPLAY_LIMIT - cName.length()).boxed().map(i -> " ").collect(Collectors.joining()),
            cName -> cName.length() > PRETTIER_DISPLAY_LIMIT,
            this.name
        );
    }

    private boolean isDecepticon() {
        return allegiance == DECEPTICON;
    }

    private boolean isAutobot() {
        return allegiance == AUTOBOT;
    }
}
