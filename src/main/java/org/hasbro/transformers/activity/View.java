package org.hasbro.transformers.activity;

public interface View<T> {
    void show();

    void setNavigator(T navigator);
}
