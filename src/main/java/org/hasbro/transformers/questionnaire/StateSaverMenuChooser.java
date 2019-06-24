package org.hasbro.transformers.questionnaire;

import org.hasbro.transformers.activity.state.Savable;

import static java.text.MessageFormat.format;
import static org.hasbro.transformers.utils.MessageWriter.ColorMode.GREEN;
import static org.hasbro.transformers.utils.MessageWriter.prettyPrintln;

public class StateSaverMenuChooser<T extends Enum> extends MenuChooser<T> {
    private final Savable savable;

    @SafeVarargs
    public StateSaverMenuChooser(String title, Savable savable, T... menuItems) {
        super(title, menuItems);
        this.savable = savable;
    }

    @Override
    protected void saveState() {
        prettyPrintln(GREEN, format("\nSaving state of {0}", savable.getClass().getSimpleName()));
        savable.saveState();
    }
}
