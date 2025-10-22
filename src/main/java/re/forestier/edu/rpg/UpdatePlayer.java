package re.forestier.edu.rpg;

import java.util.HashMap;
import java.util.Random;

public class UpdatePlayer {

    private final static String[] objectList = { "Lookout Ring : Prevents surprise attacks",
            "Scroll of Stupidity : INT-2 when applied to an enemy", "Draupnir : Increases XP gained by 100%",
            "Magic Charm : Magic +10 for 5 rounds",
            "Rune Staff of Curse : May burn your ennemies... Or yourself. Who knows?",
            "Combat Edge : Well, that's an edge", "Holy Elixir : Recover your HP"
    };

    private static HashMap<String, Integer> createAbilityMap(Object[][] data) {
        HashMap<String, Integer> map = new HashMap<>();
        for (Object[] entry : data) {
            map.put((String) entry[0], (Integer) entry[1]);
        }
        return map;
    }

    private static HashMap<Integer, HashMap<String, Integer>> createClassAbilities(Object[][][] levels) {
        HashMap<Integer, HashMap<String, Integer>> levelMap = new HashMap<>();
        for (int i = 0; i < levels.length; i++) {
            levelMap.put(i + 1, createAbilityMap(levels[i]));
        }
        return levelMap;
    }

    public static HashMap<String, HashMap<Integer, HashMap<String, Integer>>> abilitiesPerTypeAndLevel() {
        HashMap<String, HashMap<Integer, HashMap<String, Integer>>> allAbilities = new HashMap<>();

        allAbilities.put("ADVENTURER", createClassAbilities(new Object[][][] {
                { { "INT", 1 }, { "DEF", 1 }, { "ATK", 3 }, { "CHA", 2 } },
                { { "INT", 2 }, { "CHA", 3 } },
                { { "ATK", 5 }, { "ALC", 1 } },
                { { "DEF", 3 } },
                { { "VIS", 1 }, { "DEF", 4 } }
        }));

        allAbilities.put("ARCHER", createClassAbilities(new Object[][][] {
                { { "INT", 1 }, { "ATK", 3 }, { "CHA", 1 }, { "VIS", 3 } },
                { { "DEF", 1 }, { "CHA", 2 } },
                { { "ATK", 3 } },
                { { "DEF", 2 } },
                { { "ATK", 4 } }
        }));

        allAbilities.put("DWARF", createClassAbilities(new Object[][][] {
                { { "ALC", 4 }, { "INT", 1 }, { "ATK", 3 } },
                { { "DEF", 1 }, { "ALC", 5 } },
                { { "ATK", 4 } },
                { { "DEF", 2 } },
                { { "CHA", 1 } }
        }));

        return allAbilities;
    }

    public static boolean addXp(Player player, int xp) {

        if (player.getInventory() == null) {
            player.setInventory(new java.util.ArrayList<>());
        }
        int currentLevel = player.retrieveLevel();
        player.setXp(player.getXp() + xp);

        int newLevel = player.retrieveLevel();

        if (newLevel != currentLevel) {

            Random random = new Random();
            player.getInventory().add(objectList[random.nextInt(objectList.length)]);
            HashMap<String, Integer> abilities = abilitiesPerTypeAndLevel().get(player.getAvatarClass()).get(newLevel);
            abilities.forEach((ability, level) -> {

                player.getAbilities().put(ability, abilities.get(ability));

            });
            return true;
        }
        return false;
    }

    private static boolean estKo(Player joueur) {
        return joueur.getCurrenthealthpoints() == 0;
    }

    private static void afficherMessageKo() {
        System.out.println("Le joueur est KO !");
    }

    private static void regenererSiBlesse(Player joueur) {
        if (joueur.getCurrenthealthpoints() < joueur.getHealthpoints() / 2) {
            int gain = calculGainFinDeTour(joueur);
            joueur.setCurrenthealthpoints(joueur.getCurrenthealthpoints() + gain);
        }
    }

    private static void limiterPointsDeVie(Player joueur) {
        if (joueur.getCurrenthealthpoints() >= joueur.getHealthpoints()) {
            joueur.setCurrenthealthpoints(joueur.getHealthpoints());
        }
    }

    public static void majFinDeTour(Player joueur) {
        if (estKo(joueur)) {
            afficherMessageKo();
            return;
        }

        regenererSiBlesse(joueur);
        limiterPointsDeVie(joueur);
    }

    private static int calculGainFinDeTour(Player p) {
        String cls = p.getAvatarClass();
        switch (cls) {
            case "DWARF":
                int gain = 1;
                if (p.getInventory().contains("Holy Elixir")) {
                    gain += 1;
                }
                return gain;

            case "ADVENTURER":
                int adv = 2;
                if (p.retrieveLevel() < 3) {
                    adv -= 1;
                }
                return adv;

            case "ARCHER":

                int hpApresPlusUn = p.getCurrenthealthpoints() + 1;
                int archer = 1;

                if (p.getInventory().contains("Magic Bow")) {
                    archer += (hpApresPlusUn / 8) - 1;
                }
                return archer;

            default:

                return 0;
        }
    }

}
