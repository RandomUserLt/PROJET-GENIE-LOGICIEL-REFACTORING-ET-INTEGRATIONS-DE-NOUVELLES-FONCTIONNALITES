package re.forestier.edu;

import static org.approvaltests.Approvals.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.*;
import re.forestier.edu.rpg.UpdatePlayer;

public class GlobalTest {

    @Test
    void testAffichageBase() {
        // Création du joueur
        Adventurer player = new Adventurer("Florian", "Gnognak le Barbare", "ADVENTURER", 200, new ArrayList<>());
        player.setXp(200);
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

        // Vérification
        assertEquals(normalizedExpected, normalizedActual);
    }

    // Vérifie qu’une montée de plusieurs niveaux (jusqu’au niveau 4) est
    // correctement gérée et que l’affichage reflète bien le nouveau niveau et
    // l’inventaire
    @Test
    @DisplayName("Global : Chaîne de montées de niveau (jusqu’à 4) puis affichage")
    void scenarioMonteeNiveauJusqua4EtAffichage() {
        Adventurer p = new Adventurer("Alice", "Héroïne", "ADVENTURER", 0, new ArrayList<>());

        // 57 XP : seuils 10, 27, 57 → arrive niveau 4
        p.addXp(57);
        assertEquals(4, p.retrieveLevel(), "Le joueur doit être niveau 4");

        String out = p.toString();
        assertTrue(out.contains("Niveau : 4"), "L'affichage doit indiquer le niveau 4");
        assertTrue(out.contains("\n\nInventaire :"), "La section Inventaire doit apparaître");
        assertTrue(out.split("\n").length >= 8, "L'affichage doit comporter des lignes d'items");
    }

    // Vérifie qu’un archer avec < 50% de PV et équipé d’un Arc magique récupère
    // correctement des PV et que l’affichage indique bien son niveau et son
    // inventaire
    @Test
    @DisplayName("Global : Archer soigné avec Arc magique puis affichage")
    void scenarioArcherArcMagiqueSoinEtAffichage() {
        Archer p = new Archer("Robin", "Ranger", "ARCHER", 0, new ArrayList<>());
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(40); // < 50%
        p.getInventory().add("Magic Bow");

        p.addXp(10); // passage au niveau 2 et ajout d’un objet
        p.majFinDeTour();

        // 40 -> +1 (archer) + (40/8 - 1) = +5 → 45
        assertEquals(45, p.getCurrenthealthpoints());

        String out = p.toString();
        assertTrue(out.contains("Niveau : 2"), "L'affichage doit indiquer le niveau 2");
        assertTrue(out.contains("\n\nInventaire :"));
        assertTrue(out.contains("Magic Bow"), "L'inventaire doit contenir Magic Bow");
    }

    // Vérifie qu’un nain avec < 50% de PV et équipé d’un Élixir sacré gagne bien +2
    // PV (bonus nain + élixir)
    @Test
    @DisplayName("Global : Nain < 50% PV avec Élixir sacré → +2 PV")
    void scenarioNainElixirSoin() {
        Dwarf p = new Dwarf("Gimli", "Nain", "DWARF", 0, new ArrayList<>());
        p.setHealthpoints(41);
        p.setCurrenthealthpoints(19); // < 41/2 = 20
        p.getInventory().add("Holy Elixir");

        p.addXp(10); // passage niveau 2
        p.majFinDeTour();

        // +1 (élixir) +1 (bonus nain) = +2 → 21
        assertEquals(21, p.getCurrenthealthpoints());

        String out = p.toString();
        assertTrue(out.contains("Holy Elixir"));
        assertTrue(out.contains("Niveau : 2"));
    }

    // Vérifie qu’un joueur ne peut pas dépasser ses PV max (clamp à la valeur
    // maximum)
    @Test
    @DisplayName("Global : Limitation des PV au maximum autorisé")
    void scenarioClampPvAuMax() {
        Adventurer p = new Adventurer("Any", "Héros", "ADVENTURER", 0, new ArrayList<>());
        p.setHealthpoints(50);
        p.setCurrenthealthpoints(60); // déjà au-dessus du max

        p.majFinDeTour();

        assertEquals(50, p.getCurrenthealthpoints());
    }

}
