package re.forestier.edu.rpg;

public final class LevelProgression {

    // --- TABLE XP NIVEAU -----------------------------------------------------

    private static final java.util.NavigableMap<Integer, Integer> XP_TO_LEVEL = new java.util.TreeMap<>();

    static {
        enregistrerPalier(0, 1);
        enregistrerPalier(10, 2);
        enregistrerPalier(27, 3);
        enregistrerPalier(57, 4);
        enregistrerPalier(111, 5);
    }

    private static void enregistrerPalier(int xpMinInclus, int niveau) {
        XP_TO_LEVEL.put(xpMinInclus, niveau);
    }

    public static int obtenirNiveauDepuisXp(int xp) {
        return XP_TO_LEVEL.floorEntry(Math.max(0, xp)).getValue();
    }

}
