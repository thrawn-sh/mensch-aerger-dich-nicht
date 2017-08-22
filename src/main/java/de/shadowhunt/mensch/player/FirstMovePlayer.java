package de.shadowhunt.mensch.player;

import java.util.Collection;

import de.shadowhunt.mensch.GameInformation;
import de.shadowhunt.mensch.Move;
import de.shadowhunt.mensch.Player;

public class FirstMovePlayer extends Player {

    public FirstMovePlayer(final String name) {
        super(name);
    }

    @Override
    public Move choose(final Collection<Move> possibleMoves, final GameInformation information) {
        return possibleMoves.iterator().next();
    }
}
