package de.shadowhunt.mensch.moves;

import de.shadowhunt.mensch.Board;
import de.shadowhunt.mensch.Player;

public class MoveAndKillMove extends MoveMove {

    public MoveAndKillMove(final int from, final int to, final Player target) {
        super(Type.MOVE_KILL, from, to, target);
    }

    @Override
    public void perform(final Player player, final Board board) {
        final Player pawn = board.setField(to, null);
        board.returnPawn(pawn);
        super.perform(player, board);
    }
}
