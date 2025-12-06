package re.forestier.edu.rpg.progression;

import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public final class LevelProgression {

    private static final int MAX_LEVEL = 5;
    private static final NavigableMap<Integer, Integer> XP_TO_LEVEL = buildTable(MAX_LEVEL);

    private static NavigableMap<Integer, Integer> buildTable(int maxLevel) {
        NavigableMap<Integer, Integer> map = new TreeMap<>();
        int prev = 0;
        map.put(0, 1);

        for (int L = 2; L <= maxLevel; L++) {
            int xpL = (L - 1) * 10 + (L * prev) / 4;
            map.put(xpL, L);
            prev = xpL;
        }
        return map;
    }

    private LevelProgression() {

    }

    public static int obtenirNiveauDepuisXp(int xp) {
        return XP_TO_LEVEL.floorEntry(Math.max(0, xp)).getValue();
    }

}
