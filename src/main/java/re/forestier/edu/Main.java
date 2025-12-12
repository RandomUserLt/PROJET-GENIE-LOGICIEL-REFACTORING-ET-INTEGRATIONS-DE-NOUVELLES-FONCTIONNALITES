package re.forestier.edu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import re.forestier.edu.rpg.AvatarClass;
import re.forestier.edu.rpg.PlayerFactory;
import re.forestier.edu.rpg.models.GameObject;
import static re.forestier.edu.rpg.models.GameObjectCatalog.*;
import re.forestier.edu.rpg.Player;
import re.forestier.edu.rpg.Archer;
import re.forestier.edu.rpg.Dwarf;
import re.forestier.edu.rpg.Adventurer;

public class Main {

        public static void main(String[] args) { // CE MAIN NE CONTIENT PAS DE TESTS , IL S'AGIT JUSTE DE CODE EXEMPLE A
                                                 // DES FINS DE VISUALISATION

                System.out.println(
                                "REMARQUE : CE MAIN NE CONTIENT PAS DE TESTS , IL S'AGIT JUSTE DE CODE EXEMPLE A DES FINS DE  VISUALISATION\n");
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

                System.out.println("\nVérif de PlayerFactory");

                Player archer = PlayerFactory.fromType(
                                AvatarClass.ARCHER.name(),
                                "B",
                                "A",
                                120,
                                new ArrayList<GameObject>());

                Player dwarf = PlayerFactory.fromType(
                                AvatarClass.DWARF.name(),
                                "D",
                                "C",
                                80,
                                new ArrayList<GameObject>());

                Player adventurer = PlayerFactory.fromType(
                                AvatarClass.ADVENTURER.name(),
                                "F",
                                "E",
                                150,
                                new ArrayList<GameObject>());

                List<Player> L = List.of(archer, dwarf, adventurer);
                L.forEach(p -> {
                        p.setXp(15);
                        p.addXp(20);
                });

                L.forEach(p -> {
                        System.out.println("\n--- " + p.getClass().getSimpleName() + " ---");
                        System.out.println(p.toString());
                });

                System.out.println("\nSupported(ARCHER)   = " + PlayerFactory.isSupported(AvatarClass.ARCHER.name()));
                System.out.println(
                                "Supported(ADVENTURER)= " + PlayerFactory.isSupported(AvatarClass.ADVENTURER.name()));
                System.out.println("Supported(DWARF)    = " + PlayerFactory.isSupported(AvatarClass.DWARF.name()));
                System.out.println("Supported(MAGE)     = " + PlayerFactory.isSupported("MAGE")); // doit renvoyer false
                System.out.println(
                                "Le refactoring réduit la surface testable moins de if/switch, logique partagée, données immuables");
                System.out.println(
                                "Du coup, PIT a moins de mutations pertinentes et davantage de mutants équivalents :");
                System.out.println("la baisse du score vient de la simplification, pas d’une perte de qualité.\n");

                var rng = new Random();

                for (int k = 0; k < 3; k++) {
                        GameObject item = random();
                        int t = 0;
                        while (player.getInventory().contains(item) && t < 50) {
                                item = random();
                                t++;
                        }
                        if (!player.getInventory().contains(item)) {
                                player.addItem(item);
                        }
                }
                System.out.println(player.toMarkdown());

        }
}
