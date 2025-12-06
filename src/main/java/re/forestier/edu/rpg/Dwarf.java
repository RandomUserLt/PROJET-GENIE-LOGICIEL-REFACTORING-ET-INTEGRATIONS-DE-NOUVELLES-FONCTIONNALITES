package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static re.forestier.edu.rpg.models.Objects.*;
import static re.forestier.edu.rpg.models.Ability.*;
import static re.forestier.edu.rpg.config.DwarfAbilityConfig.*;

public final class Dwarf extends Player {

    private static final HashMap<Integer, HashMap<String, Integer>> LEVEL_ABILITIES = new HashMap<>();

    static {
        LEVEL_ABILITIES.put(1, new HashMap<>() {
            {
                put(ALCHEMY.name(), Level_1_ALCHEMY.value());
                put(INTELLIGENCE.name(), Level_1_INTELLIGENCE.value());
                put(ATTACK.name(), Level_1_ATTACK.value());
            }
        });
        LEVEL_ABILITIES.put(2, new HashMap<>() {
            {
                put(DEFENSE.name(), Level_2_DEFENSE.value());
                put(ALCHEMY.name(), Level_2_ALCHEMY.value());
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
                put(CHANCE.name(), Level_5_CHANCE.value());
            }
        });
    }

    public Dwarf(String playerName,
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
        int gain = 1;
        if (getInventory().contains(HOLY_ELIXIR.getLabel())) {
            gain += 1;
        }
        return gain;
    }
}
