package org.hasbro.transformers;

import org.hasbro.transformers.resource.CybertronResource;
import org.hasbro.transformers.resource.Cybertronian;
import org.hasbro.transformers.resource.TransformerMode;
import org.hasbro.transformers.resource.Weapon;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hasbro.transformers.activity.menu.player.Gender.FEMALE;
import static org.hasbro.transformers.activity.menu.player.Gender.MALE;
import static org.hasbro.transformers.resource.Allegiance.AUTOBOT;
import static org.hasbro.transformers.resource.Allegiance.DECEPTICON;
import static org.hasbro.transformers.utils.RPGSettings.freePath;

public class RPGTestData {
    private RPGTestData() {
    }

    public static CybertronResource anAutobot() {
        return new CybertronResource(
            "Autobot",
            MALE,
            AUTOBOT,
            Collections.singletonList(
                new Weapon("Gun", 30, 0.8f)
            ),
            new TransformerMode("JCB", 50),
            true,
            true);
    }

    public static CybertronResource aLegendaryAutobot() {
        return new CybertronResource(
            "Legendary Autobot",
            MALE,
            AUTOBOT,
            Collections.singletonList(
                new Weapon("Gun", 70, 0.8f)
            ),
            new TransformerMode("JCB", 60),
            true,
            true);
    }

    public static CybertronResource aDecepticon() {
        return new CybertronResource(
            "Decepticon",
            FEMALE,
            DECEPTICON,
            Collections.singletonList(
                new Weapon("Gun", 30, 0.8f)
            ),
            new TransformerMode("Jet", 50),
            true,
            false);
    }

    public static CybertronResource anAmateurDecepticon() {
        return new CybertronResource(
            "Amateur Decepticon",
            FEMALE,
            DECEPTICON,
            Collections.singletonList(
                new Weapon("Gun", 30, 0.5f)
            ),
            new TransformerMode("Jet", 20),
            false,
            false);
    }

    public static CybertronResource aLegendaryDecepticon() {
        return new CybertronResource(
            "Legendary Decepticon",
            FEMALE,
            DECEPTICON,
            Collections.singletonList(
                new Weapon("Gun", 50, 1f)
            ),
            new TransformerMode("Jet", 60),
            true,
            false);
    }

    public static List<List<Cybertronian>> createStructureOneOnOne(Cybertronian player, Cybertronian decepticon) {
        return Arrays.asList(
            Arrays.asList(decepticon, freePath(), freePath()),
            Arrays.asList(freePath(), player, freePath()),
            Arrays.asList(freePath(), freePath(), freePath())
        );
    }
}
