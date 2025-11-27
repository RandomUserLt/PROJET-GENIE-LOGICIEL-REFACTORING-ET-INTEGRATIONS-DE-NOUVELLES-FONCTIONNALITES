package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static re.forestier.edu.rpg.Literaux.*;

public final class Archer extends Player {

    private static final HashMap<Integer, HashMap<String, Integer>> LEVEL_ABILITIES = new HashMap<>();

    static {
        LEVEL_ABILITIES.put(1, new HashMap<>() {
            {
                put(INTELLIGENCE, 1);
                put(ATTACK, 3);
                put(CHANCE, 1);
                put(VISION, 3);
            }
        });

        LEVEL_ABILITIES.put(2, new HashMap<>() {
            {
                put(DEFENSE, 1);
                put(CHANCE, 2);
            }
        });

        LEVEL_ABILITIES.put(3, new HashMap<>() {
            {
                put(ATTACK, 3);
            }
        });

        LEVEL_ABILITIES.put(4, new HashMap<>() {
            {
                put(DEFENSE, 2);
            }
        });

        LEVEL_ABILITIES.put(5, new HashMap<>() {
            {
                put(ATTACK, 4);
            }
        });
    }

    @Override
    protected Map<String, Integer> getSubClassLevelAbilities(int level) {
        return LEVEL_ABILITIES.get(level);
    }

    public Archer(String playerName,
            String avatarName,
            String avatarClass,
            int money,
            ArrayList<String> inventory) {

        super(playerName, avatarName, ARCHER, money, inventory);
        initBaseAbilities(LEVEL_ABILITIES);
    }

    @Override
    public int calculGainFinDeTour() {
        int hpApresPlusUn = getCurrenthealthpoints() + 1;
        int gain = 1;

        if (getInventory().contains(OBJECT_MAGIC_BOW)) {
            gain += (hpApresPlusUn / 8) - 1;
        }

        return gain;
    }
}
