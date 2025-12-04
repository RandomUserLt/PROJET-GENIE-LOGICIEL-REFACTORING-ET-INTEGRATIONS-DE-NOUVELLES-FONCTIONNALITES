package re.forestier.edu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import re.forestier.edu.rpg.Player;
import re.forestier.edu.rpg.Archer;
import re.forestier.edu.rpg.Dwarf;
import re.forestier.edu.rpg.Adventurer;

public class Main {
    public static void main(String[] args) {

        Adventurer player = new Adventurer("Florian", "Gnognak le Barbare", 200, new ArrayList<>());
        player.setXp(200);
        player.addXp(100);
        player.setInventory(new ArrayList<>());

        String actual = player.toString();

        String expected = "Joueur Gnognak le Barbare joué par Florian\n" +
                "Niveau : 5 (XP totale : 300)\n\n" +
                "Capacités :\n" +
                "   INTELLIGENCE : 1\n" +
                "   DEFENSE : 1\n" +
                "   ATTACK : 3\n" +
                "   CHANCE : 2\n\n" +
                "Inventaire :";

        String normalizedActual = Arrays.stream(actual.split("\\R"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.joining("\n"));

        String normalizedExpected = Arrays.stream(expected.split("\\R"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.joining("\n"));

        System.out.println("=== Actual ===");
        System.out.println(normalizedActual);
        System.out.println("\n=== Expected ===");
        System.out.println(normalizedExpected);

        if (normalizedActual.equals(normalizedExpected)) {
            System.out.println("\nTest réussi !");
        } else {
            System.out.println("\nTest échoué !");
        }

    }
}
