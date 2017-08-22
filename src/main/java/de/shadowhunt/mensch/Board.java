package de.shadowhunt.mensch;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import de.shadowhunt.mensch.moves.MoveAndKillMove;
import de.shadowhunt.mensch.moves.MoveMove;
import de.shadowhunt.mensch.moves.SaveAndKillMove;
import de.shadowhunt.mensch.moves.SaveMove;
import de.shadowhunt.mensch.moves.SpawnAndKillMove;
import de.shadowhunt.mensch.moves.SpawnMove;
import de.shadowhunt.mensch.player.FirstMovePlayer;
import de.shadowhunt.mensch.player.RandomDeterministicPlayer;

public class Board {

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
        this.players = players;
        fields = new Player[players.length * FIELDS_PER_PLAYER];
        random = new Random(seed);

        safePawns = new int[players.length];
        pawns = new int[players.length];
        Arrays.fill(pawns, PAWNS_PER_PLAYER);
    }

    protected int getDiceRoll() {
        return 1 + random.nextInt(6);
    }

    public Player[] getFields() {
        return Arrays.copyOf(fields, fields.length);
    }

    protected int getNewPawnCount(final Player player) {
        return pawns[getPlayerIndex(player)];
    }

    public int getPlayerCount() {
        return players.length;
    }

    public int getPlayerIndex(final Player player) {
        for (int i = 0; i < players.length; i++) {
            if (player.equals(players[i])) {
                return i;
            }
        }
        return -1;
    }

    protected Set<Move> getPossibleMoves(final Player player, final int dice) {
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

    protected int getSavePawnCount(final Player player) {
        return safePawns[getPlayerIndex(player)];
    }

    public Player play() {
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
                    assert possibleMoves.contains(move);
                    move.perform(player, this);
                    System.out.println("  moves: " + move);
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

    public Player setField(final int field, final Player player) {
        final Player old = fields[field];
        fields[field] = player;
        return old;
    }

    public Player spawnPawn(final Player player) {
        pawns[getPlayerIndex(player)]--;
        return player;
    }
}
