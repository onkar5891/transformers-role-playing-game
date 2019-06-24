package org.hasbro.transformers.activity.state;

import org.hasbro.transformers.resource.Cybertronian;

import java.io.*;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.hasbro.transformers.utils.MessageWriter.println;

public final class BattlefieldStateSerializer implements GameStateSerializer<List<List<Cybertronian>>> {
    private static volatile BattlefieldStateSerializer instance;

    private BattlefieldStateSerializer() {
    }

    public static BattlefieldStateSerializer getInstance() {
        if (instance == null) {
            synchronized (BattlefieldStateSerializer.class) {
                if (instance == null) {
                    instance = new BattlefieldStateSerializer();
                }
            }
        }
        return instance;
    }

    @Override
    public void serialize(List<List<Cybertronian>> state, Path path) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            objectOutputStream.writeObject(state);
        } catch (IOException e) {
            println("Error in saving battlefield state");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<List<Cybertronian>> deserialize(Path path) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            return (List<List<Cybertronian>>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return Collections.emptyList();
        }
    }
}
