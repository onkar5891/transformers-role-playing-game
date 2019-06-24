package org.hasbro.transformers.activity;

public interface Selectable<T extends Enum> {
    T selectedOption();
}
