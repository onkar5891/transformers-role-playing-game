package org.hasbro.transformers.questionnaire;

import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.text.MessageFormat.format;
import static org.hasbro.transformers.utils.MessageReader.singletonScanner;
import static org.hasbro.transformers.utils.MessageWriter.print;
import static org.hasbro.transformers.utils.MessageWriter.println;

public class MenuChooser<T extends Enum> {
    private final Function<T, String> menuItemFn = menuItem -> format("{0}. {1}", menuItem.ordinal() + 1, menuItem);
    private final Scanner scanner = singletonScanner();

    private final String title;
    private final T[] menuItems;
    private T item;

    @SafeVarargs
    public MenuChooser(String title, T... menuItems) {
        this.title = title;
        this.menuItems = menuItems;
    }

    public void load() {
        boolean showWarning = false;
        int option = Integer.MIN_VALUE;

        do {
            loadHeader();
            loadItems();
            loadFooter(showWarning);

            String line = scanner.nextLine();
            try {
                option = Integer.parseInt(line);
                if (option == 0) {
                    saveState();
                } else if (rangeInvalid(option)) {
                    showWarning = true;
                }
            } catch (NumberFormatException e) {
                showWarning = true;
            }
        } while (rangeInvalid(option));

        this.item = menuItems[option - 1];
    }

    public T getItem() {
        return item;
    }

    protected void saveState() {
        println("Cannot save the battle state from this menu");
    }

    private boolean rangeInvalid(int option) {
        return option < 1 || option > menuItems.length;
    }

    private void loadItems() {
        Stream.of(menuItems).map(menuItemFn).forEach(System.out::println);
    }

    private void loadHeader() {
        println(format("\n** {0} **", title));
    }

    private void loadFooter(boolean showWarning) {
        if (showWarning) {
            println("You provided invalid option.");
        }
        print("Please choose the option: ");
    }
}
