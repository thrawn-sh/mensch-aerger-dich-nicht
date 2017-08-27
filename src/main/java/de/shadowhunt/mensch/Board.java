/**
 * This file is part of mensch-aerger-dich-nicht.
 *
 * mensch-aerger-dich-nicht is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mensch-aerger-dich-nicht is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mensch-aerger-dich-nicht.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.shadowhunt.mensch;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.annotation.CheckForNull;

import de.shadowhunt.mensch.moves.MoveAndKillMove;
import de.shadowhunt.mensch.moves.MoveMove;
import de.shadowhunt.mensch.moves.SaveAndKillMove;
import de.shadowhunt.mensch.moves.SaveMove;
import de.shadowhunt.mensch.moves.SpawnAndKillMove;
import de.shadowhunt.mensch.moves.SpawnMove;
import de.shadowhunt.mensch.player.FirstMovePlayer;
import de.shadowhunt.mensch.player.RandomDeterministicPlayer;

public final class Board {

    public static final int FIELDS_PER_PLAYER = 10;

    public static final int PAWNS_PER_PLAYER = 4;

    public static void main(final String... args) {

        final Board board = new Board(42L, new FirstMovePlayer("RED"), new RandomDeterministicPlayer("BLUE", new Random(23L)), new FirstMovePlayer("GREEN"), new RandomDeterministicPlayer("YELLOW", new Random(21L)));
        final Player winner = board.play();
        System.out.println();
        System.out.println("All pawns of player " + winner + " are safe: WINNER!");
    }

    private final Player[] fields;

    private final int[] pawns;

    private final Player[] players;

    private final Random random;

    private final int[] safePawns;

    public Board(final long seed, final Player... players) {
        this.players = Arrays.copyOf(players, players.length);
        fields = new Player[players.length * FIELDS_PER_PLAYER];
        random = new Random(seed);

        safePawns = new int[players.length];
        pawns = new int[players.length];
        Arrays.fill(pawns, PAWNS_PER_PLAYER);
    }

    private int getDiceRoll() {
        return 1 + random.nextInt(6);
    }

    Player[] getFields() {
        return Arrays.copyOf(fields, fields.length);
    }

    int getNewPawnCount(final Player player) {
        return pawns[getPlayerIndex(player)];
    }

    int getPlayerCount() {
        return players.length;
    }

    int getPlayerIndex(final Player player) {
        for (int i = 0; i < players.length; i++) {
            if (player.equals(players[i])) {
                return i;
            }
        }
        return -1;
    }

    private Set<Move> getPossibleMoves(final Player player, final int dice) {
        final int index = getPlayerIndex(player);
        final Set<Move> moves = new HashSet<>();
        if ((dice == 6) && (getNewPawnCount(player) > 0)) {
            final Player targetPawn = fields[FIELDS_PER_PLAYER * index];
            if (targetPawn == null) {
                moves.add(new SpawnMove(FIELDS_PER_PLAYER * index));
            } else if (!player.equals(targetPawn)) {
                moves.add(new SpawnAndKillMove(FIELDS_PER_PLAYER * index, targetPawn));
            }
        }

        final int save = (((FIELDS_PER_PLAYER * index) + fields.length) - 1) % fields.length;
        for (int from = 0; from < fields.length; from++) {
            final Player pawn = fields[from];
            if (player.equals(pawn)) {
                final int to = (from + dice + fields.length) % fields.length;
                final Player targetPawn = fields[to];
                if (to == save) {
                    if (targetPawn == null) {
                        moves.add(new SaveMove(from, to));
                    } else {
                        moves.add(new SaveAndKillMove(from, to, targetPawn));
                    }
                } else {
                    if (targetPawn == null) {
                        moves.add(new MoveMove(from, to));
                    } else {
                        moves.add(new MoveAndKillMove(from, to, targetPawn));
                    }
                }
            }
        }
        return moves;
    }

    int getSavePawnCount(final Player player) {
        return safePawns[getPlayerIndex(player)];
    }

    protected Player play() {
        final GameInformation gameInformation = new GameInformation(this);
        while (true) {
            for (final Player player : players) {
                final int dice = getDiceRoll();
                System.out.println("--------------------------------------------------------------------------------");
                System.out.println("Player " + player + " turn: " + getNewPawnCount(player) + "/" + getSavePawnCount(player));
                System.out.println("  rolled: " + dice);
                final Set<Move> possibleMoves = getPossibleMoves(player, dice);
                if (possibleMoves.isEmpty()) {
                    System.out.println("  can not make a move!");
                } else {
                    final Move move = player.choose(possibleMoves, gameInformation);
                    if (move == null) {
                        System.out.println("  don't want to move");
                    } else if (!possibleMoves.contains(move)) {
                        System.out.println("  cheated -> no move");
                    } else {
                        move.perform(player, this);
                        System.out.println("  moves: " + move);
                    }
                }

                if (getSavePawnCount(player) >= PAWNS_PER_PLAYER) {
                    return player;
                }
            }
        }
    }

    public void returnPawn(final Player player) {
        pawns[getPlayerIndex(player)]++;
    }

    public void savePawn(final Player player) {
        safePawns[getPlayerIndex(player)]++;
    }

    public Player setField(final int field, @CheckForNull final Player player) {
        final Player old = fields[field];
        fields[field] = player;
        return old;
    }

    public Player spawnPawn(final Player player) {
        pawns[getPlayerIndex(player)]--;
        return player;
    }
}
