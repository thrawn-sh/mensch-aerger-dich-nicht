package de.shadowhunt.mensch;

public class GameInformation {

    private final Board board;

    public GameInformation(final Board board) {
        this.board = board;
    }

    public int getFiledSize() {
        return getPlayerCount() * Board.FIELDS_PER_PLAYER;
    }

    public int getNewPawnCount(final Player player) {
        return board.getNewPawnCount(player);
    }

    public Player[] getOverview() {
        return board.getFields();
    }

    public int getPawnCountPerPlayer() {
        return Board.PAWNS_PER_PLAYER;
    }

    public int getPlayerCount() {
        return board.getPlayerCount();
    }

    public int getPlayerIndex(final Player player) {
        return board.getPlayerIndex(player);
    }

    public int getPlayingPawnCount(final Player player) {
        return getPawnCountPerPlayer() - getSavePawnCount(player) - getNewPawnCount(player);
    }

    public int getSaveField(final Player player) {
        final int size = getFiledSize();
        return ((getStartField(player) - 1) + size) % size;
    }

    public int getSavePawnCount(final Player player) {
        return board.getSavePawnCount(player);
    }

    public int getStartField(final Player player) {
        return getPlayerIndex(player) * Board.FIELDS_PER_PLAYER;
    }
}
