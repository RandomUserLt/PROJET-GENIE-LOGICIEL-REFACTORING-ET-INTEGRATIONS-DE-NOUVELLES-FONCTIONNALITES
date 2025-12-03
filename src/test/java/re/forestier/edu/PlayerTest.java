package re.forestier.edu;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.*;

public class PlayerTest {
    private ArrayList<String> inv;

    private ArrayList<String> emptyInv() {
        return new ArrayList<>();
    }

    /*
     * private Player creerJoueur(String nom, String avatar, String classe) {
     * return new Player(nom, avatar, classe, 0, emptyInv());
     * }
     */

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
        inv.add("Torch");
        inv.add("Rope");
    }

    // Vérifie que le nom du joueur est correctement stocké

    @Test
    @DisplayName("Nom du joueur bien initialisé")
    void testNomDuJoueur() {
        Adventurer joueur = new Adventurer("Florian", "Grognak le barbare", 100, new ArrayList<>());
        assertThat(joueur.getPlayerName(), is("Florian"));
    }

    // Vérifie qu’on ne peut pas avoir un montant d’argent négatif

    @Test
    @DisplayName("Impossible d’avoir un solde d’argent négatif")
    void testArgentNegatif() {
        Adventurer p = new Adventurer("Florian", "Grognak le barbare", 100, new ArrayList<>());

        try {
            p.removeMoney(200);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }

    // Vérifie que le constructeur avec une classe valide initialise bien tous les
    // champs

    @Test
    @DisplayName("Constructeur : classe valide → champs et capacités initialisés")
    void constructeurClasseValideInitialise() {
        Adventurer p = new Adventurer("Alice", "AliceAvatar", 10, new ArrayList<>(inv));
        assertEquals("Alice", p.getPlayerName());
        assertEquals("AliceAvatar", p.getAvatarName());
        // assertEquals("ADVENTURER", p.getAvatarClass());
        assertTrue(p instanceof Adventurer);
        assertNotNull(p.getMoney());
        assertEquals(10, p.getMoney().intValue());
        assertEquals(2, p.getInventory().size());
        assertNotNull(p.getAbilities(), "Les capacités doivent être initialisées");
    }

    // Vérifie que le constructeur rejette une classe invalide (champ null)
    // obsolète

    /*
     * @Test
     * 
     * @DisplayName("Constructeur : classe invalide → AvatarClass nul")
     * void constructeurClasseInvalideRefuse() {
     * Player p = new Player("Bob", "BobAvatar", "MAGE", 5, new ArrayList<>(inv));
     * assertNull(p.getAvatarClass(), "Classe invalide non acceptée");
     * }
     */

    // Vérifie que getAvatarClass renvoie bien la classe stockée

    @Test
    @DisplayName("getAvatarClass renvoie la classe du joueur")
    void testGetAvatarClass() {
        Archer p = new Archer("Cara", "CaraAvatar", 0, new ArrayList<>(inv));
        // assertEquals("ARCHER", p.getAvatarClass());
        assertTrue(p instanceof Archer);
    }

    // Vérifie que addMoney ajoute correctement de l’argent (y compris 0)

    @Test
    @DisplayName("addMoney : ajout d’argent positif ou nul")
    void testAjoutArgent() {
        Dwarf p = new Dwarf("Dan", "DanAvatar", 7, new ArrayList<>(inv));
        p.addMoney(0);
        assertEquals(7, p.getMoney().intValue());
        p.addMoney(5);
        assertEquals(12, p.getMoney().intValue());
    }

    // Vérifie que removeMoney retire l’argent si le solde est suffisant

    @Test
    @DisplayName("removeMoney : soustraction valide")
    void testRetraitArgentOk() {
        Adventurer p = new Adventurer("Eve", "EveAvatar", 10, new ArrayList<>(inv));
        p.removeMoney(4);
        assertEquals(6, p.getMoney().intValue());
    }

    // Vérifie que removeMoney lève une exception si le solde deviendrait négatif

    @Test
    @DisplayName("removeMoney : exception si résultat négatif")
    void testRetraitArgentNegatif() {
        Archer p = new Archer("Fred", "FredAvatar", 3, new ArrayList<>(inv));
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> p.removeMoney(4));
        assertTrue(ex.getMessage() == null || ex.getMessage().toLowerCase().contains("negative"));
    }

}
