package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static re.forestier.edu.rpg.Literaux.*;

public final class Dwarf extends Player {

    private static final HashMap<Integer, HashMap<String, Integer>> LEVEL_ABILITIES = new HashMap<>();

    static {
        LEVEL_ABILITIES.put(1, new HashMap<>() {
            {
                put(ALCHEMY, 4);
                put(INTELLIGENCE, 1);
                put(ATTACK, 3);
            }
        });
        LEVEL_ABILITIES.put(2, new HashMap<>() {
            {
                put(DEFENSE, 1);
                put(ALCHEMY, 5);
            }
        });
        LEVEL_ABILITIES.put(3, new HashMap<>() {
            {
                put(ATTACK, 4);
            }
        });
        LEVEL_ABILITIES.put(4, new HashMap<>() {
            {
                put(DEFENSE, 2);
            }
        });
        LEVEL_ABILITIES.put(5, new HashMap<>() {
            {
                put(CHANCE, 1);
            }
        });
    }

    public Dwarf(String playerName,
            String avatarName,
            String avatarClass,
            int money,
            ArrayList<String> inventory) {

        super(playerName, avatarName, DWARF, money, inventory);

        getAbilities().putAll(LEVEL_ABILITIES.get(1));

        int computed = obtenirNiveauDepuisXp(getXp());
        for (int lvl = 2; lvl <= computed; lvl++) {
            onLevelUp(lvl);
        }
        setLevel(computed);
    }

    @Override
    protected Map<String, Integer> getSubClassLevelAbilities(int level) {
        return LEVEL_ABILITIES.get(level);
    }

    @Override
    protected void onLevelUp(int lvl) {

        Random random = new Random();
        getInventory().add(OBJECT_LIST[random.nextInt(OBJECT_LIST.length)]);

        Map<String, Integer> abilitiesToAdd = LEVEL_ABILITIES.get(lvl);
        if (abilitiesToAdd != null) {
            abilitiesToAdd.forEach((ability, value) -> getAbilities().put(ability, value));
        }

        setLevel(lvl);
    }

    public boolean estKo() {
        return getCurrenthealthpoints() == 0;
    }

    @Override
    public int calculGainFinDeTour() {
        int gain = 1;
        if (getInventory().contains(OBJECT_HOLY_ELIXIR)) {
            gain += 1;
        }
        return gain;
    }
}
