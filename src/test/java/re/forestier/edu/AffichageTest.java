package re.forestier.edu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.*;
import re.forestier.edu.rpg.models.GameObject;
import static re.forestier.edu.rpg.models.GameObjectCatalog.*;

public class AffichageTest {
    private ArrayList<GameObject> inv;

    private ArrayList<GameObject> emptyInv() {
        return new ArrayList<>();
    }

    private Player creerJoueur(String nom, String avatar, String classe) {
        return switch (classe.toUpperCase()) {
            case "ADVENTURER" -> new Adventurer(nom, avatar, 0, emptyInv());
            case "ARCHER" -> new Archer(nom, avatar, 0, emptyInv());
            case "DWARF" -> new Dwarf(nom, avatar, 0, emptyInv());
            default -> throw new IllegalArgumentException("Classe inconnue : " + classe);
        };
    }

    @BeforeEach
    void setUp() {
        inv = new ArrayList<>();
        inv.add(DRAUPNIR);
        inv.add(RUNE);
    }

    // Vérifie que le nom du joueur est correctement stocké

    @Test
    @DisplayName("afficherJoueur : format de base avec xp=20, inventaire vide")
    void testAffichageBase() {
        ArrayList<GameObject> inv = new ArrayList<>();
        Adventurer p = new Adventurer("Florian", "Gnognak le Barbare", 0, inv);
        p.addXp(20);
        p.getInventory().clear();
        String actual = p.toString();

        String expected = "Joueur Gnognak le Barbare joué par Florian" +
                "\nNiveau : 2 (XP totale : 20)" +
                "\n\nCapacités :" +
                "\n   INTELLIGENCE : 2" +
                "\n   DEFENSE : 1" +
                "\n   ATTACK : 3" +
                "\n   CHANCE : 3" +
                "\n\nInventaire :";
        assertEquals(expected, actual);
    }

    // Vérifie que l’affichage de l’inventaire montre bien chaque item

    @Test
    @DisplayName("afficherJoueur : inventaire non vide → items affichés ligne par ligne")
    void testAffichageInventaire() {
        ArrayList<GameObject> inv = new ArrayList<>();
        inv.add(DRAUPNIR);
        inv.add(MAGIC_BOW);
        Archer p = new Archer("Alice", "Ranger", 0, inv);
        String s = p.toString();

        assertTrue(s.contains("\n\nInventaire :"));
        assertTrue(s.contains("GameObject{name='Draupnir'"));
        assertTrue(s.contains("GameObject{name='Magic Bow'"));
    }

}
