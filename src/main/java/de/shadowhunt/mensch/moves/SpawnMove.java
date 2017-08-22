package de.shadowhunt.mensch.moves;

import de.shadowhunt.mensch.Board;
import de.shadowhunt.mensch.Move;
import de.shadowhunt.mensch.Player;

public class SpawnMove extends Move {

    public SpawnMove(final int to) {
        this(Type.SPAWN, to, null);
    }

    SpawnMove(final Type type, final int to, final Player target) {
        super(type, -1, to, target);
    }

    @Override
    public void perform(final Player player, final Board board) {
        final Player pawn = board.spawnPawn(player);
        board.setField(to, pawn);
    }
}
