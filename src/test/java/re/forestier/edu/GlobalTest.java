package re.forestier.edu;

import static org.approvaltests.Approvals.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.*;
//import static re.forestier.edu.rpg.models.Objects.*;
import static re.forestier.edu.rpg.models.Ability.*;
import static re.forestier.edu.rpg.models.GameObjectCatalog.*;
//import static re.forestier.edu.rpg.models.Objects.*;

public class GlobalTest {

    @Test
    void testAffichageBase() {
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

        assertEquals(normalizedExpected, normalizedActual);
    }

    // Vérifie qu’une montée de plusieurs niveaux (jusqu’au niveau 4) est
    // correctement gérée et que l’affichage reflète bien le nouveau niveau et
    // l’inventaire
    @Test
    @DisplayName("Global : Chaîne de montées de niveau (jusqu’à 4) puis affichage")
    void scenarioMonteeNiveauJusqua4EtAffichage() {
        Adventurer p = new Adventurer("Alice", "Héroïne", 0, new ArrayList<>());

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
        Archer p = new Archer("Robin", "Ranger", 0, new ArrayList<>());
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(40);
        p.getInventory().add(MAGIC_BOW);

        p.addXp(10);
        p.majFinDeTour();

        assertEquals(45, p.getCurrenthealthpoints());

        String out = p.toString();
        assertTrue(out.contains("Niveau : 2"), "L'affichage doit indiquer le niveau 2");
        assertTrue(out.contains("\n\nInventaire :"));
        assertTrue(out.contains(MAGIC_BOW.getName()), "L'inventaire doit contenir Magic Bow");
        assertTrue(p.getInventory().contains(MAGIC_BOW));

    }

    // Vérifie qu’un nain avec < 50% de PV et équipé d’un Élixir sacré gagne bien +2
    // PV (bonus nain + élixir)
    @Test
    @DisplayName("Global : Nain < 50% PV avec Élixir sacré → +2 PV")
    void scenarioNainElixirSoin() {
        Dwarf p = new Dwarf("Gimli", "Nain", 0, new ArrayList<>());
        p.setHealthpoints(41);
        p.setCurrenthealthpoints(19);
        p.getInventory().add(HOLY_ELIXIR);
        p.addXp(10);
        p.majFinDeTour();

        assertEquals(21, p.getCurrenthealthpoints());

        String out = p.toString();
        assertTrue(out.contains(HOLY_ELIXIR.getName()));
        assertTrue(p.getInventory().contains(HOLY_ELIXIR));
        assertTrue(out.contains("Niveau : 2"));
    }

    // Vérifie qu’un joueur ne peut pas dépasser ses PV max (clamp à la valeur
    // maximum)
    @Test
    @DisplayName("Global : Limitation des PV au maximum autorisé")
    void scenarioClampPvAuMax() {
        Adventurer p = new Adventurer("Any", "Héros", 0, new ArrayList<>());
        p.setHealthpoints(50);
        p.setCurrenthealthpoints(60);

        p.majFinDeTour();

        assertEquals(50, p.getCurrenthealthpoints());
    }

}
