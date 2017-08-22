package de.shadowhunt.mensch.moves;

import de.shadowhunt.mensch.Board;
import de.shadowhunt.mensch.Move;
import de.shadowhunt.mensch.Player;

public class MoveMove extends Move {

    public MoveMove(final int from, final int to) {
        this(Type.MOVE, from, to, null);
    }

    MoveMove(final Type type, final int from, final int to, final Player target) {
        super(type, from, to, target);
    }

    @Override
    public void perform(final Player player, final Board board) {
        final Player pawn = board.setField(from, null);
        board.setField(to, pawn);
    }

}
