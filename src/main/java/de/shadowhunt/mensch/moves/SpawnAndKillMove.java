package de.shadowhunt.mensch.moves;

import de.shadowhunt.mensch.Board;
import de.shadowhunt.mensch.Player;

public class SpawnAndKillMove extends SpawnMove {

    public SpawnAndKillMove(final int to, final Player target) {
        super(Type.SPAWN_KILL, to, target);
    }

    @Override
    public void perform(final Player player, final Board board) {
        final Player pawn = board.setField(to, null);
        board.returnPawn(pawn);
        super.perform(player, board);
    }
}
