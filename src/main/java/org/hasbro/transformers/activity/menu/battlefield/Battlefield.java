package org.hasbro.transformers.activity.menu.battlefield;

import org.hasbro.transformers.activity.menu.player.Position;
import org.hasbro.transformers.activity.state.Savable;
import org.hasbro.transformers.resource.Cybertronian;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.text.MessageFormat.format;
import static java.util.stream.IntStream.range;
import static org.hasbro.transformers.factory.TransformersRPGFactory.battleFieldSerializer;
import static org.hasbro.transformers.factory.TransformersRPGFactory.fightMenuPresenter;
import static org.hasbro.transformers.utils.MessageWriter.ColorMode.GREEN;
import static org.hasbro.transformers.utils.MessageWriter.*;
import static org.hasbro.transformers.utils.RPGSettings.*;

public class Battlefield implements Savable {
    private Cybertronian player;
    private List<List<Cybertronian>> resources;
    private Path path = DEFAULT_PATH;

    private Battlefield(Cybertronian player) {
        this.resources = createStructure(player);
        this.player = player;
    }

    private Battlefield(List<List<Cybertronian>> resources) {
        this.resources = resources;
        this.player = findPlayer();
    }

    public static Battlefield create(Cybertronian player) {
        return new Battlefield(player);
    }

    public static Battlefield restore(List<List<Cybertronian>> resources) {
        prettyPrintln(GREEN, format("\nLoading state of {0}", Battlefield.class.getSimpleName()));
        return new Battlefield(resources);
    }

    private Cybertronian findPlayer() {
        return
            resources
                .stream()
                .flatMap(List::stream)
                .filter(Cybertronian::isControlledByPlayer)
                .findFirst().orElseThrow(() -> new IllegalStateException("Player not found on battlefield"));
    }

    public void show() {
        println();
        resources.forEach(resource -> {
            resource.forEach(r -> tabSuffixedPrint(2, r.getPrettierName()));
            println();
        });
    }

    public boolean shouldBattleContinue() {
        return isPlayerAlive() && containsEnemies();
    }

    public boolean isPlayerAlive() {
        return player.isAlive();
    }

    @Override
    public void saveState() {
        battleFieldSerializer().serialize(resources, path);
    }

    public void movePlayer(Position currentPosition, Position newPosition) {
        // If player is already on the boundary, but decides to go beyond
        if (!inRange(newPosition)) {
            return;
        }

        Cybertronian cybertronian = findResourceOn(newPosition);
        if (cybertronian.isEnemyOf(player)) {
            fightMenuPresenter(player, cybertronian, this).init();
        }

        // If player retreated from last battle or is blocked by an object, don't change the position
        if (player.hasRetreated()) {
            player.prepareForBattle();
        } else if (!cybertronian.blocksPlayer()) {
            changePlayerPosition(currentPosition, newPosition);
        }
    }

    public Position findPlayerPosition() {
        return
            range(0, resources.size())
                .boxed()
                .flatMap(row -> positionStream(row, playerStreamInColumns(resources.get(row))))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format("{0} not found on battlefield", player.getName())));
    }

    private void changePlayerPosition(Position currentPosition, Position newPosition) {
        resources.get(currentPosition.getRow()).set(currentPosition.getColumn(), freePath());
        if (player.isAlive()) {
            resources.get(newPosition.getRow()).set(newPosition.getColumn(), player);
        }
    }

    public void setPath(Path path) {
        this.path = path;
    }

    private boolean containsEnemies() {
        return
            resources
                .stream()
                .flatMap(List::stream)
                .anyMatch(resource -> resource.isEnemyOf(player) && resource.isAlive());
    }

    private Stream<Position> positionStream(int row, IntStream columnCoordinates) {
        return columnCoordinates.mapToObj(column -> new Position(row, column));
    }

    private IntStream playerStreamInColumns(List<Cybertronian> resources) {
        return range(0, resources.size()).filter(left -> resources.get(left).equals(player));
    }

    private boolean inRange(Position position) {
        return position.getRow() >= 0 && position.getRow() < resources.size()
            && position.getColumn() >= 0 && position.getColumn() < resources.get(position.getRow()).size();
    }

    private Cybertronian findResourceOn(Position newPosition) {
        return resources.get(newPosition.getRow()).get(newPosition.getColumn());
    }

    private List<List<Cybertronian>> createStructure(Cybertronian player) {
        return Arrays.asList(
            Arrays.asList(freePath(), freePath(), freePath(), freePath(), freePath()),
            Arrays.asList(megatron(), decepticonJet(), decepticonJet(), freePath(), freePath()),
            Arrays.asList(freePath(), decepticonJet(), decepticonJet(), freePath(), freePath()),
            Arrays.asList(slipstream(), freePath(), freePath(), player, freePath()),
            Arrays.asList(freePath(), freePath(), freePath(), freePath(), freePath())
        );
    }
}
