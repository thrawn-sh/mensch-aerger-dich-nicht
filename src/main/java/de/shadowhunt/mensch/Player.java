package de.shadowhunt.mensch;

import java.util.Collection;

public abstract class Player {

    private final String name;

    protected Player(final String name) {
        this.name = name;
    }

    /**
     * Choose a move from collections of all possible moves
     *
     * @param possibleMoves
     *            collections of all possible moves
     * @return chosen move
     */
    public abstract Move choose(final Collection<Move> possibleMoves, final GameInformation information);

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**
     * Returns the name of the player
     *
     * @return name of the player
     */
    public final String getName() {
        return name;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public final String toString() {
        return name;
    }
}
