package re.forestier.edu.rpg.config;

public final class AdventurerAbilityConfig {

    private AdventurerAbilityConfig() {
    }

    public static final AbilityPoints Level_1_INTELLIGENCE = AbilityPoints.of(1);
    public static final AbilityPoints Level_1_DEFENSE = AbilityPoints.of(1);
    public static final AbilityPoints Level_1_ATTACK = AbilityPoints.of(3);
    public static final AbilityPoints Level_1_CHANCE = AbilityPoints.of(2);

    public static final AbilityPoints Level_2_INTELLIGENCE = AbilityPoints.of(2);
    public static final AbilityPoints Level_2_CHANCE = AbilityPoints.of(3);

    public static final AbilityPoints Level_3_ATTACK = AbilityPoints.of(5);
    public static final AbilityPoints Level_3_ALCHEMY = AbilityPoints.of(1);

    public static final AbilityPoints Level_4_DEFENSE = AbilityPoints.of(3);

    public static final AbilityPoints Level_5_VISION = AbilityPoints.of(1);
    public static final AbilityPoints Level_5_DEFENSE = AbilityPoints.of(4);
}
