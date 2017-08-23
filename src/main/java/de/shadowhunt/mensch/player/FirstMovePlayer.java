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
package de.shadowhunt.mensch.player;

import java.util.Collection;

import de.shadowhunt.mensch.GameInformation;
import de.shadowhunt.mensch.Move;
import de.shadowhunt.mensch.Player;

public class FirstMovePlayer extends Player {

    public FirstMovePlayer(final String name) {
        super(name);
    }

    @Override
    public Move choose(final Collection<Move> possibleMoves, final GameInformation information) {
        return possibleMoves.iterator().next();
    }
}
