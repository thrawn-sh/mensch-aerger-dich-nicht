package de.shadowhunt.mensch.moves;

import de.shadowhunt.mensch.Board;
import de.shadowhunt.mensch.Move;
import de.shadowhunt.mensch.Player;

public class SaveMove extends Move {

    public SaveMove(final int from, final int to) {
        this(Type.SAVE, from, to, null);
    }

    SaveMove(final Type type, final int from, final int to, final Player target) {
        super(type, from, to, target);
    }

    @Override
    public void perform(final Player player, final Board board) {
        final Player pawn = board.setField(from, null);
        board.savePawn(pawn);
    }

}
