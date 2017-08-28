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
import de.shadowhunt.mensch.moves.SpawnMove;

public class SpawnOrMoveFirstPawnPlayer extends Player {

    public SpawnOrMoveFirstPawnPlayer(String name) {
        super(name);
    }

    @Override
    public Move choose(final Collection<Move> possibleMoves, final GameInformation info) {
        for (Move move : possibleMoves) {
            if (move instanceof SpawnMove) {
                return move;
            }
        }

        return possibleMoves.stream().sorted((m1, m2) -> -Integer.compare(relPos(m1, info), relPos(m2, info))).findFirst().orElse(null);
    }

    private int relPos(Move move, GameInformation information) {
        return (move.getTo() - information.getStartField(this) + information.getFiledSize()) % information.getFiledSize();
    }

}
