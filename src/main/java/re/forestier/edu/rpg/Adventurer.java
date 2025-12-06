package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//import re.forestier.edu.rpg.Ability;
import re.forestier.edu.rpg.models.Ability;
import static re.forestier.edu.rpg.models.Objects.*;
import static re.forestier.edu.rpg.models.Ability.*;
import static re.forestier.edu.rpg.config.AdventurerAbilityConfig.*;

public final class Adventurer extends Player {

    private record AbilityValue(Ability ability, int points) {

    }

    private record LevelConfig(int level, AbilityValue... abilities) {

    }

    private static final HashMap<Integer, HashMap<String, Integer>> LEVEL_ABILITIES = new HashMap<>();

    static {
        LEVEL_ABILITIES.put(1, new HashMap<>() {
            {
                put(INTELLIGENCE.name(), Level_1_INTELLIGENCE.value());
                put(DEFENSE.name(), Level_1_DEFENSE.value());
                put(ATTACK.name(), Level_1_ATTACK.value());
                put(CHANCE.name(), Level_1_CHANCE.value());
            }
        });
        LEVEL_ABILITIES.put(2, new HashMap<>() {
            {
                put(INTELLIGENCE.name(), Level_2_INTELLIGENCE.value());
                put(CHANCE.name(), Level_2_CHANCE.value());
            }
        });
        LEVEL_ABILITIES.put(3, new HashMap<>() {
            {
                put(ATTACK.name(), Level_3_ATTACK.value());
                put(ALCHEMY.name(), Level_3_ALCHEMY.value());
            }
        });
        LEVEL_ABILITIES.put(4, new HashMap<>() {
            {
                put(DEFENSE.name(), Level_4_DEFENSE.value());
            }
        });
        LEVEL_ABILITIES.put(5, new HashMap<>() {
            {
                put(VISION.name(), Level_5_VISION.value());
                put(DEFENSE.name(), Level_5_DEFENSE.value());
            }
        });
    }

    public Adventurer(String playerName,
            String avatarName,
            int money,
            ArrayList<String> inventory) {

        super(playerName, avatarName, money, inventory);
        initBaseAbilities(LEVEL_ABILITIES);
    }

    @Override
    protected Map<String, Integer> getSubClassLevelAbilities(int level) {
        return LEVEL_ABILITIES.get(level);
    }

    @Override
    public int calculGainFinDeTour() {
        int gain = 2;
        if (retrieveLevel() < 3) {
            gain -= 1;
        }
        return gain;
    }
}
