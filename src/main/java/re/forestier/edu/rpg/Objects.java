package re.forestier.edu.rpg;

public enum Objects {
    LOOKOUT_RING("Lookout Ring", "Prevents surprise attacks"),
    SCROLL_OF_STUPIDITY("Scroll of Stupidity", "INT-2 when applied to an enemy"),
    DRAUPNIR("Draupnir", "Increases XP gained by 100%"),
    MAGIC_BOW("Magic Bow", "Magic +10 for 5 rounds"),
    RUNE("Rune Staff of Curse", "May burn your ennemies... Or yourself. Who knows?"),
    COMBAT_EDGE("Combat Edge", "Well, that's an edge"),
    HOLY_ELIXIR("Holy Elixir", "Recover your HP");

    private final String label;
    private final String description;

    Objects(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return label;
    }
}
