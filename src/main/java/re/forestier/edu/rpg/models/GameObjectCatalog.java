package re.forestier.edu.rpg.models;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class GameObjectCatalog {
    private GameObjectCatalog() {
    }

    public static final GameObject LOOKOUT_RING = new GameObject("Lookout Ring", "Prevents surprise attacks", 1, 40);
    public static final GameObject SCROLL_OF_STUPIDITY = new GameObject("Scroll of Stupidity",
            "INT-2 when applied to an enemy", 1, 25);
    public static final GameObject DRAUPNIR = new GameObject("Draupnir", "Increases XP gained by 100%", 2, 200);
    public static final GameObject MAGIC_BOW = new GameObject("Magic Bow", "Magic +10 for 5 rounds", 3, 120);
    public static final GameObject RUNE = new GameObject("Rune Staff of Curse", "May burn your enemies... Or yourself.",
            2, 60);
    public static final GameObject COMBAT_EDGE = new GameObject("Combat Edge", "Well, that's an edge", 4, 80);
    public static final GameObject HOLY_ELIXIR = new GameObject("Holy Elixir", "Recover your HP", 1, 50);

    public static final List<GameObject> ALL_GAME_OBJECTS = List.of(
            LOOKOUT_RING, SCROLL_OF_STUPIDITY, DRAUPNIR,
            MAGIC_BOW, RUNE, COMBAT_EDGE, HOLY_ELIXIR);


   public static GameObject random() {
    var list = ALL_GAME_OBJECTS;
    return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }
}
