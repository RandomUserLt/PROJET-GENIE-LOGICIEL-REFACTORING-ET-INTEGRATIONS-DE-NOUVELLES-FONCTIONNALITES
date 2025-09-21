package re.forestier.edu;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.player;

public class PlayerTest {
    private ArrayList<String> inv;

    private ArrayList<String> emptyInv() {
        return new ArrayList<>();
    }

    private player creerJoueur(String nom, String avatar, String classe) {
        return new player(nom, avatar, classe, 0, emptyInv());
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
        player joueur = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertThat(joueur.playerName, is("Florian"));
    }

    // Vérifie qu’on ne peut pas avoir un montant d’argent négatif

    @Test
    @DisplayName("Impossible d’avoir un solde d’argent négatif")
    void testArgentNegatif() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

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
        player p = new player("Alice", "AliceAvatar", "ADVENTURER", 10, new ArrayList<>(inv));
        assertEquals("Alice", p.playerName);
        assertEquals("AliceAvatar", p.Avatar_name);
        assertEquals("ADVENTURER", p.getAvatarClass());
        assertNotNull(p.money);
        assertEquals(10, p.money.intValue());
        assertEquals(2, p.inventory.size());
        assertNotNull(p.abilities, "Les capacités doivent être initialisées");
    }

    // Vérifie que le constructeur rejette une classe invalide (champ null)

    @Test
    @DisplayName("Constructeur : classe invalide → AvatarClass nul")
    void constructeurClasseInvalideRefuse() {
        player p = new player("Bob", "BobAvatar", "MAGE", 5, new ArrayList<>(inv));
        assertNull(p.getAvatarClass(), "Classe invalide non acceptée");
    }

    // Vérifie que getAvatarClass renvoie bien la classe stockée

    @Test
    @DisplayName("getAvatarClass renvoie la classe du joueur")
    void testGetAvatarClass() {
        player p = new player("Cara", "CaraAvatar", "ARCHER", 0, new ArrayList<>(inv));
        assertEquals("ARCHER", p.getAvatarClass());
    }

    // Vérifie que addMoney ajoute correctement de l’argent (y compris 0)

    @Test
    @DisplayName("addMoney : ajout d’argent positif ou nul")
    void testAjoutArgent() {
        player p = new player("Dan", "DanAvatar", "DWARF", 7, new ArrayList<>(inv));
        p.addMoney(0);
        assertEquals(7, p.money.intValue());
        p.addMoney(5);
        assertEquals(12, p.money.intValue());
    }

    // Vérifie que removeMoney retire l’argent si le solde est suffisant

    @Test
    @DisplayName("removeMoney : soustraction valide")
    void testRetraitArgentOk() {
        player p = new player("Eve", "EveAvatar", "ADVENTURER", 10, new ArrayList<>(inv));
        p.removeMoney(4);
        assertEquals(6, p.money.intValue());
    }

    // Vérifie que removeMoney lève une exception si le solde deviendrait négatif

    @Test
    @DisplayName("removeMoney : exception si résultat négatif")
    void testRetraitArgentNegatif() {
        player p = new player("Fred", "FredAvatar", "ARCHER", 3, new ArrayList<>(inv));
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> p.removeMoney(4));
        assertTrue(ex.getMessage() == null || ex.getMessage().toLowerCase().contains("negative"));
    }

    // Vérifie que retrieveLevel renvoie le bon niveau en fonction de l’XP cumulée
}
