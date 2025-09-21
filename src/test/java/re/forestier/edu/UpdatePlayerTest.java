package re.forestier.edu;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;

public class UpdatePlayerTest {
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
    @DisplayName("retrieveLevel : seuils d’XP → niveaux 1 à 5")
    void testNiveauxParSeuilsXp() {
        player p = new player("Gina", "GinaAvatar", "ADVENTURER", 0, new ArrayList<>(inv));

        UpdatePlayer.addXp(p, 0);
        assertEquals(1, p.retrieveLevel());
        UpdatePlayer.addXp(p, 10);
        assertEquals(2, p.retrieveLevel());
        UpdatePlayer.addXp(p, 17);
        assertEquals(3, p.retrieveLevel());
        UpdatePlayer.addXp(p, 30);
        assertEquals(4, p.retrieveLevel());
        UpdatePlayer.addXp(p, 54);
        assertEquals(5, p.retrieveLevel());
    }

    // Vérifie que getXp renvoie bien la valeur actuelle d’XP

    @Test
    @DisplayName("getXp : valeur renvoyée après ajout d’XP")
    void testGetXp() {
        player p = new player("Hank", "HankAvatar", "DWARF", 0, new ArrayList<>(inv));
        UpdatePlayer.addXp(p, 42);
        assertEquals(42, p.getXp());
    }

    // Vérifie l’affichage de base avec xp=20 et inventaire vide

    @Test
    @DisplayName("abilitiesPerTypeAndLevel : niveau 1 présent pour toutes les classes")
    void testAbilitiesNiveau1Present() {
        HashMap<String, HashMap<Integer, HashMap<String, Integer>>> map = UpdatePlayer.abilitiesPerTypeAndLevel();
        assertNotNull(map.get("ADVENTURER"));
        assertNotNull(map.get("ARCHER"));
        assertNotNull(map.get("DWARF"));
        assertNotNull(map.get("ADVENTURER").get(1));
        assertNotNull(map.get("ARCHER").get(1));
        assertNotNull(map.get("DWARF").get(1));
    }

    // Vérifie qu’ajouter de l’XP sans franchir de seuil renvoie false et ne modifie
    // pas l’inventaire

    @Test
    @DisplayName("addXp : pas de montée de niveau → false, inventaire inchangé")
    void testAddXpSansNiveau() {
        player p = creerJoueur("A", "Ava", "ADVENTURER");
        int inv0 = p.inventory.size();
        boolean leveled = UpdatePlayer.addXp(p, 5);
        assertFalse(leveled);
        assertEquals(5, p.getXp());
        assertEquals(inv0, p.inventory.size());
    }

    // Vérifie qu’une montée de niveau donne true, met à jour le niveau et ajoute un
    // item

    @Test
    @DisplayName("addXp : montée au niveau 2 → true, niveau=2, inventaire+1")
    void testAddXpMonteeNiveau2() {
        player p = creerJoueur("B", "Bee", "ADVENTURER");
        int inv0 = p.inventory.size();
        boolean leveled = UpdatePlayer.addXp(p, 10);
        assertTrue(leveled);
        assertEquals(2, p.retrieveLevel());
        assertEquals(inv0 + 1, p.inventory.size());
        assertNotNull(UpdatePlayer.abilitiesPerTypeAndLevel().get(p.getAvatarClass()).get(2));
    }

    // Vérifie que majFinDeTour affiche "KO" si le joueur est à 0 PV

    @Test
    @DisplayName("majFinDeTour : joueur KO → message et aucun changement")
    void testMajFinDeTourKO() {
        player p = creerJoueur("C", "See", "DWARF");
        p.healthpoints = 100;
        p.currenthealthpoints = 0;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));
        try {
            UpdatePlayer.majFinDeTour(p);
        } finally {
            System.setOut(old);
        }
        String printed = out.toString().trim();
        assertTrue(printed.contains("Le joueur est KO"));
        assertEquals(0, p.currenthealthpoints);
    }

    // Vérifie que majFinDeTour rend bien +2 PV à un nain < 50% PV avec Élixir

    @Test
    @DisplayName("majFinDeTour : nain < 50% PV avec Élixir sacré → +2 PV")
    void testMajFinDeTourNainAvecElixir() {
        player p = creerJoueur("D", "Dee", "DWARF");
        p.healthpoints = 100;
        p.currenthealthpoints = 40;
        p.inventory.add("Holy Elixir");
        UpdatePlayer.majFinDeTour(p);
        assertEquals(42, p.currenthealthpoints);
    }

    // Vérifie que majFinDeTour rend bien +5 PV à un archer < 50% PV avec Arc
    // magique

    @Test
    @DisplayName("majFinDeTour : archer < 50% PV avec Arc magique → +5 PV")
    void testMajFinDeTourArcherAvecArcMagique() {
        player p = creerJoueur("E", "Eee", "ARCHER");
        p.healthpoints = 100;
        p.currenthealthpoints = 40;
        p.inventory.add("Magic Bow");
        UpdatePlayer.majFinDeTour(p);
        assertEquals(45, p.currenthealthpoints);
    }

    // Vérifie que majFinDeTour limite bien les PV au maximum

    @Test
    @DisplayName("majFinDeTour : PV limités au maximum")
    void testMajFinDeTourClampPvMax() {
        player p = creerJoueur("F", "Eff", "ADVENTURER");
        p.healthpoints = 50;
        p.currenthealthpoints = 60;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(50, p.currenthealthpoints);
    }

    // Vérifie que majFinDeTour donne +1 PV à un aventurier < 50% PV et niveau < 3

    @Test
    @DisplayName("majFinDeTour : aventurier < 50% PV, niveau < 3 → +1 PV")
    void testMajFinDeTourAventurierBasNiveau() {
        player p = new player("A", "Hero", "ADVENTURER", 0, new ArrayList<>());
        p.healthpoints = 100;
        p.currenthealthpoints = 40;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(41, p.currenthealthpoints);
    }

    // Vérifie que majFinDeTour donne +2 PV à un aventurier < 50% PV et niveau ≥ 3

    @Test
    @DisplayName("majFinDeTour : aventurier < 50% PV, niveau ≥ 3 → +2 PV")
    void testMajFinDeTourAventurierHautNiveau() {
        player p = new player("A", "Hero", "ADVENTURER", 0, new ArrayList<>());
        p.healthpoints = 100;
        p.currenthealthpoints = 40;
        UpdatePlayer.addXp(p, 27);
        assertEquals(3, p.retrieveLevel());
        UpdatePlayer.majFinDeTour(p);
        assertEquals(42, p.currenthealthpoints);
    }

    // Vérifie que majFinDeTour donne +1 PV à un nain < 50% PV sans Élixir

    @Test
    @DisplayName("majFinDeTour : nain < 50% PV sans Élixir → +1 PV")
    void testMajFinDeTourNainSansElixir() {
        player p = new player("D", "Dwarf", "DWARF", 0, new ArrayList<>());
        p.healthpoints = 100;
        p.currenthealthpoints = 40;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(41, p.currenthealthpoints);
    }

    // Vérifie que majFinDeTour donne +1 PV à un archer < 50% PV sans Arc magique

    @Test
    @DisplayName("majFinDeTour : archer < 50% PV sans Arc magique → +1 PV")
    void testMajFinDeTourArcherSansArc() {
        player p = new player("R", "Archer", "ARCHER", 0, new ArrayList<>());
        p.healthpoints = 100;
        p.currenthealthpoints = 40;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(41, p.currenthealthpoints);
    }

    // Vérifie qu’un joueur avec ≥ 50% PV et < PV max ne change pas

    @Test
    @DisplayName("majFinDeTour : PV ≥ 50% et < max → aucun changement")
    void testMajFinDeTourPasDeChangement() {
        player p = new player("X", "Any", "ADVENTURER", 0, new ArrayList<>());
        p.healthpoints = 100;
        p.currenthealthpoints = 60;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(60, p.currenthealthpoints);
    }

    // Vérifie que addXp fait monter un archer au niveau 2 et ajoute un item

    @Test
    @DisplayName("addXp : archer monte au niveau 2 → true + inventaire+1")
    void testAddXpArcherNiveau2() {
        player p = new player("R", "Archer", "ARCHER", 0, new ArrayList<>());
        int inv0 = p.inventory.size();
        boolean up = UpdatePlayer.addXp(p, 10);
        assertTrue(up);
        assertEquals(2, p.retrieveLevel());
        assertEquals(inv0 + 1, p.inventory.size());
    }

    // Vérifie que addXp fait monter un nain au niveau 2 et ajoute un item

    @Test
    @DisplayName("addXp : nain monte au niveau 2 → true + inventaire+1")
    void testAddXpNainNiveau2() {
        player p = new player("D", "Dwarf", "DWARF", 0, new ArrayList<>());
        int inv0 = p.inventory.size();
        boolean up = UpdatePlayer.addXp(p, 10);
        assertTrue(up);
        assertEquals(2, p.retrieveLevel());
        assertEquals(inv0 + 1, p.inventory.size());
    }

    // Vérifie que majFinDeTour avec PV exactement au max ne change rien

    @Test
    @DisplayName("majFinDeTour : PV exactement au maximum → inchangé")
    void testMajFinDeTourPvMax() {
        player p = new player("Z", "Any", "ADVENTURER", 0, new ArrayList<>());
        p.healthpoints = 100;
        p.currenthealthpoints = 100;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(100, p.currenthealthpoints);
    }

    // Vérifie que addXp avec 0 XP ne change rien

    @Test
    @DisplayName("addXp : 0 XP → false, XP et inventaire inchangés")
    void testAddXpZero() {
        player p = new player("N", "None", "DWARF", 0, new ArrayList<>());
        int inv0 = p.inventory.size();
        boolean up = UpdatePlayer.addXp(p, 0);
        assertFalse(up);
        assertEquals(0, p.getXp());
        assertEquals(inv0, p.inventory.size());
    }

    // Vérifie les cas limites des seuils XP pour retrieveLevel

    @Test
    @DisplayName("retrieveLevel : valeurs limites 26→2, 56→3, 110→4")
    void testRetrieveLevelBords() {
        player p1 = new player("E1", "Edge", "ADVENTURER", 0, new ArrayList<>());
        UpdatePlayer.addXp(p1, 26);
        assertEquals(2, p1.retrieveLevel());

        player p2 = new player("E2", "Edge", "ADVENTURER", 0, new ArrayList<>());
        UpdatePlayer.addXp(p2, 56);
        assertEquals(3, p2.retrieveLevel());

        player p3 = new player("E3", "Edge", "ADVENTURER", 0, new ArrayList<>());
        UpdatePlayer.addXp(p3, 110);
        assertEquals(4, p3.retrieveLevel());
    }

    // Vérifie que abilitiesPerTypeAndLevel contient bien les entrées jusqu’au
    // niveau 5

    @Test
    @DisplayName("abilitiesPerTypeAndLevel : entrées présentes jusqu’au niveau 5")
    void testAbilitiesNiveaux1a5() {
        var map = UpdatePlayer.abilitiesPerTypeAndLevel();
        for (String cls : new String[] { "ADVENTURER", "ARCHER", "DWARF" }) {
            assertNotNull(map.get(cls));
            assertNotNull(map.get(cls).get(1));
            assertNotNull(map.get(cls).get(2));
            assertNotNull(map.get(cls).get(3));
            assertNotNull(map.get(cls).get(4));
            assertNotNull(map.get(cls).get(5));
        }
    }

    // Pour essayer d'augmenter avec PIT
    // 1) Archer à 50% PV AVEC Arc magique : ne soigne pas (tue < -> <=)

    @Test
    @DisplayName("majFinDeTour : Archer à 50% PV avec Magic Bow → pas de soin")
    void archer_magicbow_exactement_moitie_pas_de_soin() {
        player p = new player("A", "Archer", "ARCHER", 0, new ArrayList<>());
        p.healthpoints = 100;
        p.currenthealthpoints = 50; // exactement 50%
        p.inventory.add("Magic Bow");
        UpdatePlayer.majFinDeTour(p);
        assertEquals(50, p.currenthealthpoints);
    }

    // 2) Nain à 50% PV AVEC Élixir : ne soigne pas (tue < -> <=)

    @Test
    @DisplayName("majFinDeTour : Nain à 50% PV avec Élixir sacré → pas de soin")
    void nain_elixir_exactement_moitie_pas_de_soin() {
        player p = new player("D", "Dwarf", "DWARF", 0, new ArrayList<>());
        p.healthpoints = 100;
        p.currenthealthpoints = 50; // exactement 50%
        p.inventory.add("Holy Elixir");
        UpdatePlayer.majFinDeTour(p);
        assertEquals(50, p.currenthealthpoints);
    }

    // 3) Seuils d’XP : juste avant / juste après 10, 27, 57 (tue >= / > et signale
    // correctement leveled)

    @Test
    @DisplayName("addXp : croisement des seuils 10/27/57 → leveled vrai uniquement en franchissant")
    void addXp_croisements_seuils() {
        player p = new player("S", "Edge", "ADVENTURER", 0, new ArrayList<>());

        boolean up;

        up = UpdatePlayer.addXp(p, 9);
        assertFalse(up);
        assertEquals(1, p.retrieveLevel());
        up = UpdatePlayer.addXp(p, 1);
        assertTrue(up);
        assertEquals(2, p.retrieveLevel()); // 10

        up = UpdatePlayer.addXp(p, 16);
        assertFalse(up);
        assertEquals(2, p.retrieveLevel()); // 26
        up = UpdatePlayer.addXp(p, 1);
        assertTrue(up);
        assertEquals(3, p.retrieveLevel()); // 27

        up = UpdatePlayer.addXp(p, 29);
        assertFalse(up);
        assertEquals(3, p.retrieveLevel()); // 56
        up = UpdatePlayer.addXp(p, 1);
        assertTrue(up);
        assertEquals(4, p.retrieveLevel()); // 57
    }

    // 4) Formule archer : vérifier le calcul exact (tue mutations MATH)

    @Test
    @DisplayName("majFinDeTour : Archer <50% avec Magic Bow, hp=48 → +6 (1 + (48/8 - 1))")
    void archer_magicbow_calcul_bonus_precis() {
        player p = new player("R", "Archer", "ARCHER", 0, new ArrayList<>());
        p.healthpoints = 100;
        p.currenthealthpoints = 48;
        p.inventory.add("Magic Bow");
        UpdatePlayer.majFinDeTour(p);
        assertEquals(54, p.currenthealthpoints); // 48 + 1 + (6-1) = 54
    }

    // 5) Clamp au max : guérir pile jusqu’au max mais pas au-delà (tue > -> >=)
    /*
     * @Test
     * 
     * @DisplayName("majFinDeTour : soin qui atteint exactement le max → clampé au max"
     * )
     * void clamp_apres_soin_atteint_max() {
     * player p = new player("C", "Any", "ADVENTURER", 0, new ArrayList<>());
     * p.healthpoints = 50;
     * p.currenthealthpoints = 49; // < 50% mais niveau 1 → +1
     * UpdatePlayer.majFinDeTour(p);
     * assertEquals(50, p.currenthealthpoints); // pas 51
     * }
     */

    // 6) Seuil niveau 3 : comportements différents pour niv.2 vs niv.3 (tue < ->
    // <=)

    @Test
    @DisplayName("Aventurier : niv.2 → +1 PV ; niv.3 → +2 PV (seuil exact)")
    void aventurier_seuil_niveau3_differe() {
        // niveau 2 (26 XP)
        player p2 = new player("A", "Hero", "ADVENTURER", 0, new ArrayList<>());
        p2.healthpoints = 100;
        p2.currenthealthpoints = 40;
        UpdatePlayer.addXp(p2, 26); // reste niv.2
        UpdatePlayer.majFinDeTour(p2);
        assertEquals(41, p2.currenthealthpoints);

        // niveau 3 (27 XP)
        player p3 = new player("B", "Hero", "ADVENTURER", 0, new ArrayList<>());
        p3.healthpoints = 100;
        p3.currenthealthpoints = 40;
        UpdatePlayer.addXp(p3, 27); // passe niv.3
        UpdatePlayer.majFinDeTour(p3);
        assertEquals(42, p3.currenthealthpoints);
    }

    // --- Mutants "conditional boundary" & "math" ciblés ---

    // Archer + Magic Bow : cas limites de division entière
    // 7 -> 7 + 1 + (0-1) = 7 (pas de gain)
    /*
     * @Test
     * 
     * @DisplayName("majFinDeTour : Archer + Magic Bow, hp=7  → +0 (division entière)"
     * )
     * void archer_magicbow_hp7_pas_de_gain() {
     * player p = new player("R", "Archer", "ARCHER", 0, new ArrayList<>());
     * p.healthpoints = 100;
     * p.currenthealthpoints = 7; // < 50%
     * p.inventory.add("Magic Bow");
     * UpdatePlayer.majFinDeTour(p);
     * assertEquals(7, p.currenthealthpoints);
     * }
     */

    // 8 -> 8 + 1 + (1-1) = 9

    @Test
    @DisplayName("majFinDeTour : Archer + Magic Bow, hp=8  → +1")
    void archer_magicbow_hp8_plus1() {
        player p = new player("R", "Archer", "ARCHER", 0, new ArrayList<>());
        p.healthpoints = 100;
        p.currenthealthpoints = 8; // < 50%
        p.inventory.add("Magic Bow");
        UpdatePlayer.majFinDeTour(p);
        assertEquals(9, p.currenthealthpoints);
    }

    // 16 -> 16 + 1 + (2-1) = 18

    @Test
    @DisplayName("majFinDeTour : Archer + Magic Bow, hp=16 → +2")
    void archer_magicbow_hp16_plus2() {
        player p = new player("R", "Archer", "ARCHER", 0, new ArrayList<>());
        p.healthpoints = 100;
        p.currenthealthpoints = 16; // < 50%
        p.inventory.add("Magic Bow");
        UpdatePlayer.majFinDeTour(p);
        assertEquals(18, p.currenthealthpoints);
    }

    // Seuil 50% pile : pas de soin (tue < -> <=) pour Aventurier

    @Test
    @DisplayName("majFinDeTour : Aventurier à 50% PV pile → pas de soin")
    void aventurier_exactement_moitie_pas_de_soin() {
        player p = new player("C", "Any", "ADVENTURER", 0, new ArrayList<>());
        p.healthpoints = 50;
        p.currenthealthpoints = 25; // exactement 50%
        UpdatePlayer.majFinDeTour(p);
        assertEquals(25, p.currenthealthpoints);
    }

    // Seuil niveau 3 : niv.2 → +1 ; niv.3 → +2 (tue < -> <=)

    @Test
    @DisplayName("Aventurier : seuil niveau 3 — niv.2:+1  vs  niv.3:+2")
    void aventurier_seuil_niveau3() {
        player p2 = new player("A", "Hero", "ADVENTURER", 0, new ArrayList<>());
        p2.healthpoints = 100;
        p2.currenthealthpoints = 40;
        UpdatePlayer.addXp(p2, 26); // niveau 2
        UpdatePlayer.majFinDeTour(p2);
        assertEquals(41, p2.currenthealthpoints);

        player p3 = new player("B", "Hero", "ADVENTURER", 0, new ArrayList<>());
        p3.healthpoints = 100;
        p3.currenthealthpoints = 40;
        UpdatePlayer.addXp(p3, 27); // niveau 3
        UpdatePlayer.majFinDeTour(p3);
        assertEquals(42, p3.currenthealthpoints);
    }

    // Saut de plusieurs niveaux en un seul add (tue mutants sur la boucle de
    // seuils)

    @Test
    @DisplayName("addXp : 0→120 en un coup → niveau 5")
    void addXp_saut_multi_niveaux() {
        player p = new player("S", "Edge", "ADVENTURER", 0, new ArrayList<>());
        boolean leveled = UpdatePlayer.addXp(p, 120);
        assertTrue(leveled);
        assertEquals(5, p.retrieveLevel()); // 111+ → niv.5
    }

} // fin de code
  // }
