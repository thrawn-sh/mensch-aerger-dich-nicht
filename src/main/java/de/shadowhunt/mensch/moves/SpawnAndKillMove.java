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
