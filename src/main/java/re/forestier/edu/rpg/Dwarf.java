package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//import static re.forestier.edu.rpg.Literaux.*;
//import static re.forestier.edu.rpg.AvatarClass.*;
import static re.forestier.edu.rpg.Objects.*;
import static re.forestier.edu.rpg.Ability.*;

public final class Dwarf extends Player {

    private static final HashMap<Integer, HashMap<String, Integer>> LEVEL_ABILITIES = new HashMap<>();

    static {
        LEVEL_ABILITIES.put(1, new HashMap<>() {
            {
                put(ALCHEMY.name(), 4);
                put(INTELLIGENCE.name(), 1);
                put(ATTACK.name(), 3);
            }
        });
        LEVEL_ABILITIES.put(2, new HashMap<>() {
            {
                put(DEFENSE.name(), 1);
                put(ALCHEMY.name(), 5);
            }
        });
        LEVEL_ABILITIES.put(3, new HashMap<>() {
            {
                put(ATTACK.name(), 4);
            }
        });
        LEVEL_ABILITIES.put(4, new HashMap<>() {
            {
                put(DEFENSE.name(), 2);
            }
        });
        LEVEL_ABILITIES.put(5, new HashMap<>() {
            {
                put(CHANCE.name(), 1);
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
