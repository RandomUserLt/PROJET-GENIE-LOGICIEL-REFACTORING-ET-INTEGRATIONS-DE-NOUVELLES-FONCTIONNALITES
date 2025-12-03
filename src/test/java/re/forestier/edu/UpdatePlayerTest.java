package re.forestier.edu;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.*;
import re.forestier.edu.rpg.Literaux.*;

public class UpdatePlayerTest {
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
    @DisplayName("retrieveLevel : seuils d’XP → niveaux 1 à 5")
    void testNiveauxParSeuilsXp() {
        Adventurer p = new Adventurer("Gina", "GinaAvatar", 0, new ArrayList<>(inv));

        p.addXp(0);
        assertEquals(1, p.retrieveLevel());
        p.addXp(10);
        assertEquals(2, p.retrieveLevel());
        p.addXp(17);
        assertEquals(3, p.retrieveLevel());
        p.addXp(30);
        assertEquals(4, p.retrieveLevel());
        p.addXp(54);
        assertEquals(5, p.retrieveLevel());
    }

    // Vérifie que getXp renvoie bien la valeur actuelle d’XP

    @Test
    @DisplayName("getXp : valeur renvoyée après ajout d’XP")
    void testGetXp() {
        Dwarf p = new Dwarf("Hank", "HankAvatar", 0, new ArrayList<>(inv));
        p.addXp(42);
        assertEquals(42, p.getXp());
    }

    // Vérifie l’affichage de base avec xp=20 et inventaire vide

    /*
     * @Test
     * 
     * @DisplayName("abilitiesPerTypeAndLevel : niveau 1 présent pour toutes les classes"
     * )
     * void testAbilitiesNiveau1Present() {
     * // Mock local des abilities par type et niveau
     * HashMap<String, HashMap<Integer, HashMap<String, Integer>>> map = new
     * HashMap<>();
     * 
     * map.put("ADVENTURER", new Adventurer("", "", "", 0, new
     * ArrayList<>()).getAbilities());
     * map.put("ARCHER", new Archer("", "", "", 0, new
     * ArrayList<>()).getAbilities());
     * map.put("DWARF", new Dwarf("", "", "", 0, new ArrayList<>()).getAbilities());
     * 
     * assertNotNull(map.get("ADVENTURER"));
     * assertNotNull(map.get("ARCHER"));
     * assertNotNull(map.get("DWARF"));
     * assertNotNull(map.get("ADVENTURER").get(1));
     * assertNotNull(map.get("ARCHER").get(1));
     * assertNotNull(map.get("DWARF").get(1));
     * }
     */
    @Test
    @DisplayName("niveau 1 présent pour toutes les classes")
    void testAbilitiesNiveau1Present() {
        Adventurer adv = new Adventurer("A", "Hero", 0, new ArrayList<>());
        Archer arch = new Archer("B", "Hero", 0, new ArrayList<>());
        Dwarf dwarf = new Dwarf("C", "Hero", 0, new ArrayList<>());

        // Vérifier que le niveau 1 existe dans LEVEL_ABILITIES et est non vide
        assertNotNull(adv.getLevelAbilities(1));
        assertFalse(adv.getLevelAbilities(1).isEmpty());

        assertNotNull(arch.getLevelAbilities(1));
        assertFalse(arch.getLevelAbilities(1).isEmpty());

        assertNotNull(dwarf.getLevelAbilities(1));
        assertFalse(dwarf.getLevelAbilities(1).isEmpty());
    }

    /*
     * @Test
     * 
     * @DisplayName("abilitiesPerTypeAndLevel : niveau 1 présent pour toutes les classes"
     * )
     * void testAbilitiesNiveau1Present() {
     * HashMap<String, HashMap<Integer, HashMap<String, Integer>>> map =
     * UpdatePlayer.abilitiesPerTypeAndLevel();
     * assertNotNull(map.get("ADVENTURER"));
     * assertNotNull(map.get("ARCHER"));
     * assertNotNull(map.get("DWARF"));
     * assertNotNull(map.get("ADVENTURER").get(1));
     * assertNotNull(map.get("ARCHER").get(1));
     * assertNotNull(map.get("DWARF").get(1));
     * }
     */

    // Vérifie qu’ajouter de l’XP sans franchir de seuil renvoie false et ne modifie
    // pas l’inventaire

    @Test
    @DisplayName("addXp : pas de montée de niveau → false, inventaire inchangé")
    void testAddXpSansNiveau() {
        Player p = creerJoueur("A", "Ava", "ADVENTURER");
        int inv0 = p.getInventory().size();
        boolean leveled = p.addXpWithLevelCheck(5);
        assertFalse(leveled);
        assertEquals(5, p.getXp());
        assertEquals(inv0, p.getInventory().size());
    }

    // Vérifie qu’une montée de niveau donne true, met à jour le niveau et ajoute un
    // item
    // refonte du test, conservation de la logique juste adaptation aux nouvelles
    // classes et hiérarchies introduites
    /*
     * @Test
     * 
     * @DisplayName("addXp : montée au niveau 2 → true, niveau=2, inventaire+1")
     * void testAddXpMonteeNiveau2() {
     * Adventurer p = new Adventurer("Bee", "B", Literaux.ADVENTURER, 0, new
     * ArrayList<>());
     * 
     * int inv0 = p.getInventory().size();
     * boolean leveled = p.addXpWithLevelCheck(10); // ajoute XP et update abilities
     * 
     * assertTrue(leveled);
     * assertEquals(2, p.retrieveLevel());
     * assertEquals(inv0 + 1, p.getInventory().size());
     * 
     * // Vérifie que les abilities du niveau 2 sont présentes dans le joueur
     * Map<String, Integer> lvl2 = Adventurer.LEVEL_ABILITIES.get(2);
     * for (String ability : lvl2.keySet()) {
     * assertTrue(p.getAbilities().containsKey(ability));
     * assertEquals(lvl2.get(ability), p.getAbilities().get(ability));
     * }
     * }
     */
    @Test
    @DisplayName("addXp : montée au niveau 2 → true, niveau=2, inventaire+1")
    void testAddXpMonteeNiveau2() {
        Adventurer p = new Adventurer("Bee", "B", 0, new ArrayList<>());

        int inv0 = p.getInventory().size();
        boolean leveled = p.addXpWithLevelCheck(10); // ajoute XP et update abilities

        assertTrue(leveled);
        assertEquals(2, p.retrieveLevel());
        assertEquals(inv0 + 1, p.getInventory().size());

        // Vérifie que les abilities du niveau 2 sont présentes dans le joueur
        Map<String, Integer> lvl2 = p.getLevelAbilities(2);
        for (String ability : lvl2.keySet()) {
            assertTrue(p.getAbilities().containsKey(ability));
            assertEquals(lvl2.get(ability), p.getAbilities().get(ability));
        }
    }

    /*
     * @Test
     * 
     * @DisplayName("addXp : montée au niveau 2 → true, niveau=2, inventaire+1")
     * void testAddXpMonteeNiveau2() {
     * Player p = creerJoueur("B", "Bee", "ADVENTURER");
     * int inv0 = p.getInventory().size();
     * boolean leveled = p.addXpWithLevelCheck(10);
     * assertTrue(leveled);
     * assertEquals(2, p.retrieveLevel());
     * assertEquals(inv0 + 1, p.getInventory().size());
     * assertNotNull(UpdatePlayer.abilitiesPerTypeAndLevel().get(p.getAvatarClass())
     * .get(2));
     * }
     */

    // Vérifie que majFinDeTour affiche "KO" si le joueur est à 0 PV

    @Test
    @DisplayName("majFinDeTour : joueur KO → message et aucun changement")
    void testMajFinDeTourKO() {
        Player p = creerJoueur("C", "See", "DWARF");
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(0);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));
        try {
            p.majFinDeTour();
        } finally {
            System.setOut(old);
        }
        String printed = out.toString().trim();
        assertTrue(printed.contains("Le joueur est KO"));
        assertEquals(0, p.getCurrenthealthpoints());
    }

    // Vérifie que majFinDeTour rend bien +2 PV à un nain < 50% PV avec Élixir

    @Test
    @DisplayName("majFinDeTour : nain < 50% PV avec Élixir sacré → +2 PV")
    void testMajFinDeTourNainAvecElixir() {
        Player p = creerJoueur("D", "Dee", "DWARF");
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(40);
        p.getInventory().add("Holy Elixir");
        p.majFinDeTour();
        assertEquals(42, p.getCurrenthealthpoints());
    }

    // Vérifie que majFinDeTour rend bien +5 PV à un archer < 50% PV avec Arc
    // magique

    @Test
    @DisplayName("majFinDeTour : archer < 50% PV avec Arc magique → +5 PV")
    void testMajFinDeTourArcherAvecArcMagique() {
        Player p = creerJoueur("E", "Eee", "ARCHER");
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(40);
        p.getInventory().add("Magic Bow");
        p.majFinDeTour();
        assertEquals(45, p.getCurrenthealthpoints());
    }

    // Vérifie que majFinDeTour limite bien les PV au maximum

    @Test
    @DisplayName("majFinDeTour : PV limités au maximum")
    void testMajFinDeTourClampPvMax() {
        Player p = creerJoueur("F", "Eff", "ADVENTURER");
        p.setHealthpoints(50);
        p.setCurrenthealthpoints(60);
        p.majFinDeTour();
        assertEquals(50, p.getCurrenthealthpoints());
    }

    // Vérifie que majFinDeTour donne +1 PV à un aventurier < 50% PV et niveau < 3

    @Test
    @DisplayName("majFinDeTour : aventurier < 50% PV, niveau < 3 → +1 PV")
    void testMajFinDeTourAventurierBasNiveau() {
        Adventurer p = new Adventurer("A", "Hero", 0, new ArrayList<>());
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(40);
        p.majFinDeTour();
        assertEquals(41, p.getCurrenthealthpoints());
    }

    // Vérifie que majFinDeTour donne +2 PV à un aventurier < 50% PV et niveau ≥ 3

    @Test
    @DisplayName("majFinDeTour : aventurier < 50% PV, niveau ≥ 3 → +2 PV")
    void testMajFinDeTourAventurierHautNiveau() {
        Adventurer p = new Adventurer("A", "Hero", 0, new ArrayList<>());
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(40);
        p.addXp(27);
        assertEquals(3, p.retrieveLevel());
        p.majFinDeTour();
        assertEquals(42, p.getCurrenthealthpoints());
    }

    // Vérifie que majFinDeTour donne +1 PV à un nain < 50% PV sans Élixir

    @Test
    @DisplayName("majFinDeTour : nain < 50% PV sans Élixir → +1 PV")
    void testMajFinDeTourNainSansElixir() {
        Dwarf p = new Dwarf("D", "Dwarf", 0, new ArrayList<>());
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(40);
        p.majFinDeTour();
        assertEquals(41, p.getCurrenthealthpoints());
    }

    // Vérifie que majFinDeTour donne +1 PV à un archer < 50% PV sans Arc magique

    @Test
    @DisplayName("majFinDeTour : archer < 50% PV sans Arc magique → +1 PV")
    void testMajFinDeTourArcherSansArc() {
        Archer p = new Archer("R", "Archer", 0, new ArrayList<>());
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(40);
        p.majFinDeTour();
        assertEquals(41, p.getCurrenthealthpoints());
    }

    // Vérifie qu’un joueur avec ≥ 50% PV et < PV max ne change pas

    @Test
    @DisplayName("majFinDeTour : PV ≥ 50% et < max → aucun changement")
    void testMajFinDeTourPasDeChangement() {
        Adventurer p = new Adventurer("X", "Any", 0, new ArrayList<>());
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(60);
        p.majFinDeTour();
        assertEquals(60, p.getCurrenthealthpoints());
    }

    // Vérifie que addXp fait monter un archer au niveau 2 et ajoute un item

    @Test
    @DisplayName("addXp : archer monte au niveau 2 → true + inventaire+1")
    void testAddXpArcherNiveau2() {
        Archer p = new Archer("R", "Archer", 0, new ArrayList<>());
        int inv0 = p.getInventory().size();
        boolean up = p.addXpWithLevelCheck(10);
        assertTrue(up);
        assertEquals(2, p.retrieveLevel());
        assertEquals(inv0 + 1, p.getInventory().size());
    }

    // Vérifie que addXp fait monter un nain au niveau 2 et ajoute un item

    @Test
    @DisplayName("addXp : nain monte au niveau 2 → true + inventaire+1")
    void testAddXpNainNiveau2() {
        Dwarf p = new Dwarf("D", "Dwarf", 0, new ArrayList<>());
        int inv0 = p.getInventory().size();
        boolean up = p.addXpWithLevelCheck(10);
        assertTrue(up);
        assertEquals(2, p.retrieveLevel());
        assertEquals(inv0 + 1, p.getInventory().size());
    }

    // Vérifie que majFinDeTour avec PV exactement au max ne change rien

    @Test
    @DisplayName("majFinDeTour : PV exactement au maximum → inchangé")
    void testMajFinDeTourPvMax() {
        Adventurer p = new Adventurer("Z", "Any", 0, new ArrayList<>());
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(100);
        p.majFinDeTour();
        assertEquals(100, p.getCurrenthealthpoints());
    }

    // Vérifie que addXp avec 0 XP ne change rien

    @Test
    @DisplayName("addXp : 0 XP → false, XP et inventaire inchangés")
    void testAddXpZero() {
        Dwarf p = new Dwarf("N", "None", 0, new ArrayList<>());
        int inv0 = p.getInventory().size();
        boolean up = p.addXpWithLevelCheck(0);
        assertFalse(up);
        assertEquals(0, p.getXp());
        assertEquals(inv0, p.getInventory().size());
    }

    // Vérifie les cas limites des seuils XP pour retrieveLevel

    @Test
    @DisplayName("retrieveLevel : valeurs limites 26→2, 56→3, 110→4")
    void testRetrieveLevelBords() {
        Adventurer p1 = new Adventurer("E1", "Edge", 0, new ArrayList<>());
        p1.addXp(26);
        assertEquals(2, p1.retrieveLevel());

        Adventurer p2 = new Adventurer("E2", "Edge", 0, new ArrayList<>());
        p2.addXp(56);
        assertEquals(3, p2.retrieveLevel());

        Adventurer p3 = new Adventurer("E3", "Edge", 0, new ArrayList<>());
        p3.addXp(110);
        assertEquals(4, p3.retrieveLevel());
    }

    // Vérifie que abilitiesPerTypeAndLevel contient bien les entrées jusqu’au
    // niveau 5

    /*
     * @Test
     * 
     * @DisplayName("abilitiesPerTypeAndLevel : entrées présentes jusqu’au niveau 5"
     * )
     * void testAbilitiesNiveaux1a5() {
     * var map = UpdatePlayer.abilitiesPerTypeAndLevel();
     * for (String cls : new String[] { "ADVENTURER", "ARCHER", "DWARF" }) {
     * assertNotNull(map.get(cls));
     * assertNotNull(map.get(cls).get(1));
     * assertNotNull(map.get(cls).get(2));
     * assertNotNull(map.get(cls).get(3));
     * assertNotNull(map.get(cls).get(4));
     * assertNotNull(map.get(cls).get(5));
     * }
     * }
     */
    @Test
    @DisplayName("abilities : entrées présentes jusqu’au niveau 5")
    void testAbilitiesNiveaux1a5() {
        Player[] players = {
                new Adventurer("A", "Hero", 0, new ArrayList<>()),
                new Archer("B", "Hero", 0, new ArrayList<>()),
                new Dwarf("C", "Hero", 0, new ArrayList<>())
        };

        for (Player p : players) {
            String className = p.getClass().getSimpleName();
            for (int lvl = 1; lvl <= 5; lvl++) {
                Map<String, Integer> abilities = p.getLevelAbilities(lvl);
                assertNotNull(abilities, "Classe " + className + " niveau " + lvl + " manquant");
                assertFalse(abilities.isEmpty(), "Classe " + className + " niveau " + lvl + " vide");
            }
        }
    }

    // Pour essayer d'augmenter avec PIT
    // 1) Archer à 50% PV AVEC Arc magique : ne soigne pas (tue < -> <=)

    @Test
    @DisplayName("majFinDeTour : Archer à 50% PV avec Magic Bow → pas de soin")
    void archer_magicbow_exactement_moitie_pas_de_soin() {
        Archer p = new Archer("A", "Archer", 0, new ArrayList<>());
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(50); // exactement 50%
        p.getInventory().add("Magic Bow");
        p.majFinDeTour();
        assertEquals(50, p.getCurrenthealthpoints());
    }

    // 2) Nain à 50% PV AVEC Élixir : ne soigne pas (tue < -> <=)

    @Test
    @DisplayName("majFinDeTour : Nain à 50% PV avec Élixir sacré → pas de soin")
    void nain_elixir_exactement_moitie_pas_de_soin() {
        Dwarf p = new Dwarf("D", "Dwarf", 0, new ArrayList<>());
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(50);// exactement 50%
        p.getInventory().add("Holy Elixir");
        p.majFinDeTour();
        assertEquals(50, p.getCurrenthealthpoints());
    }

    // 3) Seuils d’XP : juste avant / juste après 10, 27, 57 (tue >= / > et signale
    // correctement leveled)

    @Test
    @DisplayName("addXp : croisement des seuils 10/27/57 → leveled vrai uniquement en franchissant")
    void addXp_croisements_seuils() {
        Adventurer p = new Adventurer("S", "Edge", 0, new ArrayList<>());

        boolean up;

        up = p.addXpWithLevelCheck(9);
        assertFalse(up);
        assertEquals(1, p.retrieveLevel());
        up = p.addXpWithLevelCheck(1);
        assertTrue(up);
        assertEquals(2, p.retrieveLevel()); // 10

        up = p.addXpWithLevelCheck(16);
        assertFalse(up);
        assertEquals(2, p.retrieveLevel()); // 26
        up = p.addXpWithLevelCheck(1);
        assertTrue(up);
        assertEquals(3, p.retrieveLevel()); // 27

        up = p.addXpWithLevelCheck(29);
        assertFalse(up);
        assertEquals(3, p.retrieveLevel()); // 56
        up = p.addXpWithLevelCheck(1);
        assertTrue(up);
        assertEquals(4, p.retrieveLevel()); // 57
    }

    // 4) Formule archer : vérifier le calcul exact (tue mutations MATH)

    @Test
    @DisplayName("majFinDeTour : Archer <50% avec Magic Bow, hp=48 → +6 (1 + (48/8 - 1))")
    void archer_magicbow_calcul_bonus_precis() {
        Archer p = new Archer("R", "Archer", 0, new ArrayList<>());
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(48);
        p.getInventory().add("Magic Bow");
        p.majFinDeTour();
        assertEquals(54, p.getCurrenthealthpoints()); // 48 + 1 + (6-1) = 54
    }

    @Test
    @DisplayName("Aventurier : niv.2 → +1 PV ; niv.3 → +2 PV (seuil exact)")
    void aventurier_seuil_niveau3_differe() {
        // niveau 2 (26 XP)
        Adventurer p2 = new Adventurer("A", "Hero", 0, new ArrayList<>());
        p2.setHealthpoints(100);
        p2.setCurrenthealthpoints(40);
        p2.addXp(26); // reste niv.2
        p2.majFinDeTour();
        assertEquals(41, p2.getCurrenthealthpoints());

        // niveau 3 (27 XP)
        Adventurer p3 = new Adventurer("B", "Hero", 0, new ArrayList<>());
        p3.setHealthpoints(100);
        p3.setCurrenthealthpoints(40);
        p3.addXp(27); // passe niv.3
        p3.majFinDeTour();
        assertEquals(42, p3.getCurrenthealthpoints());
    }

    @Test
    @DisplayName("majFinDeTour : Archer + Magic Bow, hp=8  → +1")
    void archer_magicbow_hp8_plus1() {
        Archer p = new Archer("R", "Archer", 0, new ArrayList<>());
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(8); // < 50%
        p.getInventory().add("Magic Bow");
        p.majFinDeTour();
        assertEquals(9, p.getCurrenthealthpoints());
    }

    // 16 -> 16 + 1 + (2-1) = 18

    @Test
    @DisplayName("majFinDeTour : Archer + Magic Bow, hp=16 → +2")
    void archer_magicbow_hp16_plus2() {
        Archer p = new Archer("R", "Archer", 0, new ArrayList<>());
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(16);// < 50%
        p.getInventory().add("Magic Bow");
        p.majFinDeTour();
        assertEquals(18, p.getCurrenthealthpoints());
    }

    // Seuil 50% pile : pas de soin (tue < -> <=) pour Aventurier

    @Test
    @DisplayName("majFinDeTour : Aventurier à 50% PV pile → pas de soin")
    void aventurier_exactement_moitie_pas_de_soin() {
        Adventurer p = new Adventurer("C", "Any", 0, new ArrayList<>());
        p.setHealthpoints(50);
        p.setCurrenthealthpoints(25); // exactement 50%
        p.majFinDeTour();
        assertEquals(25, p.getCurrenthealthpoints());
    }

    // Seuil niveau 3 : niv.2 → +1 ; niv.3 → +2 (tue < -> <=)

    @Test
    @DisplayName("Aventurier : seuil niveau 3 — niv.2:+1  vs  niv.3:+2")
    void aventurier_seuil_niveau3() {
        Adventurer p2 = new Adventurer("A", "Hero", 0, new ArrayList<>());
        p2.setHealthpoints(100);
        p2.setCurrenthealthpoints(40);
        p2.addXp(26); // niveau 2
        p2.majFinDeTour();
        assertEquals(41, p2.getCurrenthealthpoints());

        Adventurer p3 = new Adventurer("B", "Hero", 0, new ArrayList<>());
        p3.setHealthpoints(100);
        ;
        p3.setCurrenthealthpoints(40);
        p3.addXp(27); // niveau 3
        p3.majFinDeTour();
        assertEquals(42, p3.getCurrenthealthpoints());
    }

    // Saut de plusieurs niveaux en un seul add (tue mutants sur la boucle de
    // seuils)

    @Test
    @DisplayName("addXp : 0→120 en un coup → niveau 5")
    void addXp_saut_multi_niveaux() {
        Adventurer p = new Adventurer("S", "Edge", 0, new ArrayList<>());
        boolean leveled = p.addXpWithLevelCheck(120);
        assertTrue(leveled);
        assertEquals(5, p.retrieveLevel()); // 111+ → niv.5
    }

} // fin de code
  // }
