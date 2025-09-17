package re.forestier.edu;

import static org.approvaltests.Approvals.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;

public class GlobalTest {

    // Vérifie que l’affichage de base du joueur correspond bien au format attendu
    /*
     * @Test
     * 
     * @DisplayName("Global : Vérification de l’affichage de base")
     * void testAffichageDeBase() {
     * player player = new player("Florian", "Gnognak le Barbare", "ADVENTURER",
     * 200, new ArrayList<>());
     * UpdatePlayer.addXp(player, 20);
     * player.inventory = new ArrayList<>();
     * 
     * verify(Affichage.afficherJoueur(player));
     * }
     */

    @Test
    void testAffichageBase() {
        player player = new player("Florian", "Gnognak le Barbare", "ADVENTURER", 200, new ArrayList<>());
        UpdatePlayer.addXp(player, 20);
        player.inventory = new ArrayList<>();

        verify(Affichage.afficherJoueur(player));
    }

    // Vérifie qu’une montée de plusieurs niveaux (jusqu’au niveau 4) est
    // correctement gérée et que l’affichage reflète bien le nouveau niveau et
    // l’inventaire
    @Test
    @DisplayName("Global : Chaîne de montées de niveau (jusqu’à 4) puis affichage")
    void scenarioMonteeNiveauJusqua4EtAffichage() {
        player p = new player("Alice", "Héroïne", "ADVENTURER", 0, new ArrayList<>());

        // 57 XP : seuils 10, 27, 57 → arrive niveau 4
        UpdatePlayer.addXp(p, 57);
        assertEquals(4, p.retrieveLevel(), "Le joueur doit être niveau 4");

        String out = Affichage.afficherJoueur(p);
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
        player p = new player("Robin", "Ranger", "ARCHER", 0, new ArrayList<>());
        p.healthpoints = 100;
        p.currenthealthpoints = 40; // < 50%
        p.inventory.add("Magic Bow");

        UpdatePlayer.addXp(p, 10); // passage au niveau 2 et ajout d’un objet
        UpdatePlayer.majFinDeTour(p);

        // 40 -> +1 (archer) + (40/8 - 1) = +5 → 45
        assertEquals(45, p.currenthealthpoints);

        String out = Affichage.afficherJoueur(p);
        assertTrue(out.contains("Niveau : 2"), "L'affichage doit indiquer le niveau 2");
        assertTrue(out.contains("\n\nInventaire :"));
        assertTrue(out.contains("Magic Bow"), "L'inventaire doit contenir Magic Bow");
    }

    // Vérifie qu’un nain avec < 50% de PV et équipé d’un Élixir sacré gagne bien +2
    // PV (bonus nain + élixir)
    @Test
    @DisplayName("Global : Nain < 50% PV avec Élixir sacré → +2 PV")
    void scenarioNainElixirSoin() {
        player p = new player("Gimli", "Nain", "DWARF", 0, new ArrayList<>());
        p.healthpoints = 41;
        p.currenthealthpoints = 19; // < 41/2 = 20
        p.inventory.add("Holy Elixir");

        UpdatePlayer.addXp(p, 10); // passage niveau 2
        UpdatePlayer.majFinDeTour(p);

        // +1 (élixir) +1 (bonus nain) = +2 → 21
        assertEquals(21, p.currenthealthpoints);

        String out = Affichage.afficherJoueur(p);
        assertTrue(out.contains("Holy Elixir"));
        assertTrue(out.contains("Niveau : 2"));
    }

    // Vérifie qu’un joueur ne peut pas dépasser ses PV max (clamp à la valeur
    // maximum)
    @Test
    @DisplayName("Global : Limitation des PV au maximum autorisé")
    void scenarioClampPvAuMax() {
        player p = new player("Any", "Héros", "ADVENTURER", 0, new ArrayList<>());
        p.healthpoints = 50;
        p.currenthealthpoints = 60; // déjà au-dessus du max

        UpdatePlayer.majFinDeTour(p);

        assertEquals(50, p.currenthealthpoints);
    }

} // fin de code
