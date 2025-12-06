package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static re.forestier.edu.rpg.config.GoblinAbilityConfig.*;
import static re.forestier.edu.rpg.models.Ability.*;

public final class Goblin extends Player {

    private static final HashMap<Integer, HashMap<String, Integer>> LEVEL_ABILITIES = new HashMap<>();

    static {
        LEVEL_ABILITIES.put(1, new HashMap<>() {
            {
                put(INTELLIGENCE.name(), Level_1_INTELLIGENCE.value());
                put(ATTACK.name(), Level_1_ATTACK.value());
                put(ALCHEMY.name(), Level_1_ALCHEMY.value());
            }
        });

        LEVEL_ABILITIES.put(2, new HashMap<>() {
            {
                put(ATTACK.name(), Level_2_ATTACK.value());
                put(ALCHEMY.name(), Level_2_ALCHEMY.value());
            }
        });

        LEVEL_ABILITIES.put(3, new HashMap<>() {
            {
                put(VISION.name(), Level_3_VISION.value());
            }
        });

        LEVEL_ABILITIES.put(4, new HashMap<>() {
            {
                put(DEFENSE.name(), Level_4_DEFENSE.value());
            }
        });

        LEVEL_ABILITIES.put(5, new HashMap<>() {
            {
                put(DEFENSE.name(), Level_5_DEFENSE.value());
                put(ATTACK.name(), Level_5_ATTACK.value());
            }
        });
    }

    public Goblin(String playerName,
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
    public int calculGainFinDeTour() { // non spécifiée
        return 1;
    }
}
