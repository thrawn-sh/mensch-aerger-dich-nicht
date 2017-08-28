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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.shadowhunt.mensch.player.FirstMovePlayer;
import de.shadowhunt.mensch.player.RandomDeterministicPlayer;
import de.shadowhunt.mensch.player.SpawnOrMoveFirstPawnPlayer;

public class Statistic {

    private final int numberOfIterations;
    private final List<Player> players;
    private final Random random;

    public static void main(final String... args) {

        Random random = new Random(23L);

        List<Player> players = new ArrayList<>();
        players.add(new FirstMovePlayer("P1"));
        players.add(new RandomDeterministicPlayer("P2", new Random(random.nextLong())));
        players.add(new SpawnOrMoveFirstPawnPlayer("P3"));

        new Statistic(50, players, random).evaluate();
    }

    public Statistic(int numberOfIterations, List<Player> players) {
        this.numberOfIterations = numberOfIterations;
        this.players = players;
        this.random = new Random();
    }

    public Statistic(int numberOfIterations, List<Player> players, Random random) {
        this.numberOfIterations = numberOfIterations;
        this.players = players;
        this.random = random;
    }

    public void evaluate() {
        List<Player> winners = evaluateWinners();
        winsBy(winners, p -> p.getClass().getSimpleName());
    }

    private <T> void winsBy(List<Player> winners, Function<Player, T> classifier) {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Winner statistic:");
        System.out.println("#\tPlayer");
        winners.stream() //
                .collect(Collectors.groupingBy(classifier, LinkedHashMap::new, Collectors.counting())) //
                .entrySet() //
                .stream() //
                .sorted((e1, e2) -> -Long.compare(e1.getValue(), e2.getValue())) //
                .forEach(e -> {
                    System.out.println(e.getValue() + "\t" + e.getKey());
                });
    }

    private List<Player> evaluateWinners() {
        List<Player> winners = new ArrayList<>(numberOfIterations);
        for (int i = 0; i < numberOfIterations; i++) {
            Board game = new Board(random.nextLong(), players.toArray(new Player[players.size()]));
            winners.add(game.play());
        }
        return winners;
    }
}
