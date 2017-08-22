package de.shadowhunt.mensch;

public abstract class Move {

    public static enum Type {
        MOVE, MOVE_KILL, SAVE, SAVE_KILL, SPAWN, SPAWN_KILL;
    }

    protected final int from;

    private final Player target;

    protected final int to;

    protected final Type type;

    protected Move(final Type type, final int from, final int to, final Player target) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.target = target;
    }

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
        final Move other = (Move) obj;
        if (from != other.from) {
            return false;
        }
        if (to != other.to) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }

    /**
     * Returns the filed index of the source of this move
     * 
     * @return filed index of the source of this move
     */
    public final int getFrom() {
        return from;
    }

    /**
     * Returns the owner of the pawn on the target field
     * 
     * @return owner of the pawn on the target field
     */
    public final Player getTarget() {
        return target;
    }

    /**
     * Returns the filed index of the destination of this move
     * 
     * @return filed index of the destination of this move
     */
    public final int getTo() {
        return to;
    }

    /**
     * Returns the type of the move
     * 
     * @return type of the move
     */
    public final Type getType() {
        return type;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + from;
        result = (prime * result) + to;
        result = (prime * result) + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    public abstract void perform(Player player, Board board);

    @Override
    public final String toString() {
        if (target == null) {
            return type + "(" + from + " -> " + to + ")";
        }
        return type + "(" + from + " -> " + to + "[" + target + "])";
    }
}
