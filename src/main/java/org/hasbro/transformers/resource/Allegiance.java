package org.hasbro.transformers.resource;

import java.util.Collections;
import java.util.List;

public enum Allegiance {
    AUTOBOT {
        @Override
        List<Allegiance> enemies() {
            return Collections.singletonList(DECEPTICON);
        }
    },
    DECEPTICON {
        @Override
        List<Allegiance> enemies() {
            return Collections.singletonList(AUTOBOT);
        }
    },
    NOT_APPLICABLE {
        @Override
        List<Allegiance> enemies() {
            return Collections.emptyList();
        }
    };

    boolean isEnemyOf(Allegiance allegiance) {
        return enemies().contains(allegiance);
    }

    abstract List<Allegiance> enemies();
}
