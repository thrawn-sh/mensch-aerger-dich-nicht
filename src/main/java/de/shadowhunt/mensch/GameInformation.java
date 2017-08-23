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

public final class GameInformation {

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
