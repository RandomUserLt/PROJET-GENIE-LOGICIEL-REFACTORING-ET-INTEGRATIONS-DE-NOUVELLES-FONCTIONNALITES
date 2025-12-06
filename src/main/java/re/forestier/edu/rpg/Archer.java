package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static re.forestier.edu.rpg.models.Objects.*;
import static re.forestier.edu.rpg.models.Ability.*;
import static re.forestier.edu.rpg.config.ArcherAbilityConfig.*;

public final class Archer extends Player {

    private static final HashMap<Integer, HashMap<String, Integer>> LEVEL_ABILITIES = new HashMap<>();

    static {
        LEVEL_ABILITIES.put(1, new HashMap<>() {
            {
                put(INTELLIGENCE.name(), Level_1_INTELLIGENCE.value());
                put(ATTACK.name(), Level_1_ATTACK.value());
                put(CHANCE.name(), Level_1_CHANCE.value());
                put(VISION.name(), Level_1_VISION.value());
            }
        });

        LEVEL_ABILITIES.put(2, new HashMap<>() {
            {
                put(DEFENSE.name(), Level_2_DEFENSE.value());
                put(CHANCE.name(), Level_2_CHANCE.value());
            }
        });

        LEVEL_ABILITIES.put(3, new HashMap<>() {
            {
                put(ATTACK.name(), Level_3_ATTACK.value());
            }
        });

        LEVEL_ABILITIES.put(4, new HashMap<>() {
            {
                put(DEFENSE.name(), Level_4_DEFENSE.value());
            }
        });

        LEVEL_ABILITIES.put(5, new HashMap<>() {
            {
                put(ATTACK.name(), Level_5_ATTACK.value());
            }
        });
    }

    @Override
    protected Map<String, Integer> getSubClassLevelAbilities(int level) {
        return LEVEL_ABILITIES.get(level);
    }

    public Archer(String playerName,
            String avatarName,
            int money,
            ArrayList<String> inventory) {

        super(playerName, avatarName, money, inventory);
        initBaseAbilities(LEVEL_ABILITIES);
    }

    @Override
    public int calculGainFinDeTour() {
        int hpApresPlusUn = getCurrenthealthpoints() + 1;
        int gain = 1;

        if (getInventory().contains(MAGIC_BOW.getLabel())) {
            gain += (hpApresPlusUn / 8) - 1;
        }

        return gain;
    }
}
