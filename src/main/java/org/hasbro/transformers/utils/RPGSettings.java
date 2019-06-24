package org.hasbro.transformers.utils;

import org.hasbro.transformers.activity.menu.battlefield.Battlefield;
import org.hasbro.transformers.factory.TransformersRPGFactory;
import org.hasbro.transformers.resource.CybertronResource;
import org.hasbro.transformers.resource.Cybertronian;
import org.hasbro.transformers.resource.TransformerMode;
import org.hasbro.transformers.resource.Weapon;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.hasbro.transformers.activity.menu.player.Gender.FEMALE;
import static org.hasbro.transformers.activity.menu.player.Gender.MALE;
import static org.hasbro.transformers.resource.Allegiance.DECEPTICON;

public class RPGSettings {
    // Default health booster after killing opponent
    // But can be made dynamic based on the strength of killed opponent
    public static final int HEALTH_BOOSTER = 25;

    public static final Path DEFAULT_PATH = Paths.get("/tmp/transformers-rpg/transformers-state.rpg");

    private RPGSettings() {
    }

    public static boolean shouldPrettyPrint() {
        return Boolean.parseBoolean(System.getProperty("prettyPrint", "false"));
    }

    public static Battlefield restoreBattlefield() {
        List<List<Cybertronian>> resources = deserializeFromDefaultPath();
        return restoreBattlefield(resources);
    }

    public static Battlefield restoreBattlefield(Path path) {
        List<List<Cybertronian>> resources = deserializeFrom(path);
        return restoreBattlefield(resources);
    }

    private static Battlefield restoreBattlefield(List<List<Cybertronian>> resources) {
        if (resources.isEmpty()) {
            throw new IllegalStateException();
        }
        return Battlefield.restore(resources);
    }

    private static List<List<Cybertronian>> deserializeFromDefaultPath() {
        return TransformersRPGFactory.battleFieldSerializer().deserialize(DEFAULT_PATH);
    }

    private static List<List<Cybertronian>> deserializeFrom(Path path) {
        return TransformersRPGFactory.battleFieldSerializer().deserialize(path);
    }

    public static Battlefield createBattlefield(Cybertronian player) {
        return Battlefield.create(player);
    }

    static InputStream inputMode() {
        return System.in;
    }

    static PrintStream outputMode() {
        return System.out;
    }

    public static Cybertronian slipstream() {
        return new CybertronResource(
            "Slipstream",
            FEMALE,
            DECEPTICON,
            Arrays.asList(
                new Weapon("Rifle", 20, 0.8f),
                new Weapon("Sword", 20, 0.7f)
            ),
            new TransformerMode("Jet", 50),
            true,
            false
        );
    }

    public static Cybertronian megatron() {
        return new CybertronResource(
            "Megatron",
            MALE,
            DECEPTICON,
            Arrays.asList(
                new Weapon("Rifle", 30, 0.9f),
                new Weapon("Sword", 20, 0.8f)
            ),
            new TransformerMode("Giant Cannon", 50),
            true,
            false
        );
    }

    public static Cybertronian freePath() {
        return new CybertronResource("FREE_PATH", false);
    }

    public static Cybertronian decepticonJet() {
        return new CybertronResource("Decepticon Jet", true);
    }
}
