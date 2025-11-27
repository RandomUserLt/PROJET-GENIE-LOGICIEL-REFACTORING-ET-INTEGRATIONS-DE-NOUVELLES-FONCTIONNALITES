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

        // Création du joueur
        Adventurer player = new Adventurer("Florian", "Gnognak le Barbare", "ADVENTURER", 200, new ArrayList<>());
        player.setXp(200);
        // UpdatePlayer.addXp(player, 100);
        player.addXp(100);
        player.setInventory(new ArrayList<>());

        // Valeur réelle
        String actual = player.toString();

        // Chaîne attendue
        String expected = "Joueur Gnognak le Barbare joué par Florian\n" +
                "Niveau : 5 (XP totale : 300)\n\n" +
                "Capacités :\n" +
                "   INTELLIGENCE : 1\n" +
                "   DEFENSE : 1\n" +
                "   ATTACK : 3\n" +
                "   CHANCE : 2\n\n" +
                "Inventaire :";

        // Normaliser les chaînes : trim et suppression des lignes vides superflues
        String normalizedActual = Arrays.stream(actual.split("\\R"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.joining("\n"));

        String normalizedExpected = Arrays.stream(expected.split("\\R"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.joining("\n"));

        // Affichage pour debug
        System.out.println("=== Actual ===");
        System.out.println(normalizedActual);
        System.out.println("\n=== Expected ===");
        System.out.println(normalizedExpected);

        // Vérification simple
        if (normalizedActual.equals(normalizedExpected)) {
            System.out.println("\nTest réussi !");
        } else {
            System.out.println("\nTest échoué !");
        }

    }
}
