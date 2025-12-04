package re.forestier.edu.rpg;

public enum Ability {
    INTELLIGENCE,
    DEFENSE,
    CHANCE,
    ATTACK,
    ALCHEMY,
    VISION;

    @Override
    public String toString() {
        return name();
    }
}
