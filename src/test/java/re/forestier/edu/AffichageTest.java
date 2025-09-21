package re.forestier.edu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.Player;
import re.forestier.edu.rpg.UpdatePlayer;

public class AffichageTest {
    private ArrayList<String> inv;

    private ArrayList<String> emptyInv() {
        return new ArrayList<>();
    }

    private Player creerJoueur(String nom, String avatar, String classe) {
        return new Player(nom, avatar, classe, 0, emptyInv());
    }

    @BeforeEach
    void setUp() {
        inv = new ArrayList<>();
        inv.add("Torch");
        inv.add("Rope");
    }

    // Vérifie que le nom du joueur est correctement stocké

    @Test
    @DisplayName("afficherJoueur : format de base avec xp=20, inventaire vide")
    void testAffichageBase() {
        ArrayList<String> inv = new ArrayList<>();
        Player p = new Player("Florian", "Gnognak le Barbare", "ADVENTURER", 0, inv);
        UpdatePlayer.addXp(p, 20);
        p.getInventory().clear();
        String actual = Affichage.afficherJoueur(p);

        String expected = "Joueur Gnognak le Barbare joué par Florian" +
                "\nNiveau : 2 (XP totale : 20)" +
                "\n\nCapacités :" +
                "\n   DEF : 1" +
                "\n   ATK : 3" +
                "\n   CHA : 3" +
                "\n   INT : 2" +
                "\n\nInventaire :";
        assertEquals(expected, actual);
    }

    // Vérifie que l’affichage de l’inventaire montre bien chaque item

    @Test
    @DisplayName("afficherJoueur : inventaire non vide → items affichés ligne par ligne")
    void testAffichageInventaire() {
        ArrayList<String> inv = new ArrayList<>();
        inv.add("Torch");
        inv.add("Magic Bow");
        Player p = new Player("Alice", "Ranger", "ARCHER", 0, inv);
        String s = Affichage.afficherJoueur(p);

        assertTrue(s.contains("\n\nInventaire :"));
        assertTrue(s.contains("\n   Torch"));
        assertTrue(s.contains("\n   Magic Bow"));
    }

    // Vérifie que la map abilitiesPerTypeAndLevel contient bien les niveaux pour
    // toutes les classes
}
