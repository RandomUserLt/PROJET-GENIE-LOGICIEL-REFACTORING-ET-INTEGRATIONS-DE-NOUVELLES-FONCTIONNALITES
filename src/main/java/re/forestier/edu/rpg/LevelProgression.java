package re.forestier.edu.rpg;

import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public final class LevelProgression {

    private static final List<LevelStep> LEVEL_STEPS = List.of(
            new LevelStep(0, 1),
            new LevelStep(10, 2),
            new LevelStep(27, 3),
            new LevelStep(57, 4),
            new LevelStep(111, 5));

    private LevelProgression() {

    }

    private static final NavigableMap<Integer, Integer> XP_TO_LEVEL = new TreeMap<>();

    static {
        for (LevelStep step : LEVEL_STEPS) {
            XP_TO_LEVEL.put(step.minXp(), step.level());
        }
    }

    public static int obtenirNiveauDepuisXp(int xp) {
        return XP_TO_LEVEL.floorEntry(Math.max(0, xp)).getValue();
    }

}
