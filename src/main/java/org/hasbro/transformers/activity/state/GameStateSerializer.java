package org.hasbro.transformers.activity.state;

import java.nio.file.Path;

public interface GameStateSerializer<T> {
    void serialize(T state, Path path);

    T deserialize(Path path);
}
