package re.forestier.edu.rpg;

public final class AbilityPoints {
    private final int value;

    private AbilityPoints(int value) {
        this.value = value;
    }

    public static AbilityPoints of(int value) {
        return new AbilityPoints(value);
    }

    public int value() {
        return value;
    }
}
