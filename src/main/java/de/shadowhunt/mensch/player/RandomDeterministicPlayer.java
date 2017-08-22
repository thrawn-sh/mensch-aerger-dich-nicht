package de.shadowhunt.mensch.player;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import de.shadowhunt.mensch.GameInformation;
import de.shadowhunt.mensch.Move;
import de.shadowhunt.mensch.Player;

public class RandomDeterministicPlayer extends Player {

    private final Random random;

    public RandomDeterministicPlayer(final String name, final Random random) {
        super(name);
        this.random = random;
    }

    @Override
    public Move choose(final Collection<Move> possibleMoves, final GameInformation information) {
        int moveIndex = random.nextInt(possibleMoves.size());

        final Iterator<Move> iterator = possibleMoves.iterator();
        while (moveIndex > 0) {
            iterator.next();
            moveIndex--;
        }
        return iterator.next();
    }
}
