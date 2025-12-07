package re.forestier.edu;

import static org.approvaltests.Approvals.verify;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.Adventurer;
import re.forestier.edu.rpg.Player;
import re.forestier.edu.rpg.models.GameObject;

public class PlayerMarkdownTest {

    private Player creerAventurier() {
        ArrayList<GameObject> inv = new ArrayList<>();
        Adventurer p = new Adventurer("Florian", "Gnognak le Barbare", 0, inv);
        p.addXp(20);
        p.getInventory().clear();
        return p;
    }

    @Test
    @DisplayName("toMarkdown : ne renvoie pas une chaîne nulle ou vide")
    void toMarkdownIsNotNullOrBlank() {
        Player p = creerAventurier();

        String markdown = p.toMarkdown();

        assertNotNull(markdown, "toMarkdown() ne doit jamais renvoyer null");
        assertFalse(markdown.isBlank(), "toMarkdown() ne doit pas renvoyer une chaîne vide ou blank");
    }

    @Test
    @DisplayName("toMarkdown : contient les sections principales (titre, capacités, inventaire)")
    void toMarkdownContainsMainSections() {
        Player p = creerAventurier();

        String markdown = p.toMarkdown();

        assertTrue(markdown.contains("#"), "Le Markdown doit contenir au moins un titre (#)");
        assertTrue(markdown.toLowerCase().contains("capacités")
                || markdown.toLowerCase().contains("capacites"),
                "Le Markdown doit contenir une section pour les capacités");
        assertTrue(markdown.toLowerCase().contains("inventaire"),
                "Le Markdown doit contenir une section pour l’inventaire");
        assertTrue(markdown.contains(p.getAvatarName()),
                "Le Markdown doit mentionner le nom du personnage (avatar)");
        assertTrue(markdown.contains(p.getPlayerName()),
                "Le Markdown doit mentionner le nom du joueur");
    }

    @Test
    @DisplayName("toMarkdown : fiche complète d’un aventurier de base validée par Approvals")
    void toMarkdownBaseAdventurerApproved() {
        Player p = creerAventurier();

        String markdown = p.toMarkdown();
        verify(markdown);
    }
}
