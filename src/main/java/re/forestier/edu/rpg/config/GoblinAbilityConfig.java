package re.forestier.edu.rpg.config;

public final class GoblinAbilityConfig {

    private GoblinAbilityConfig() {
    }

    public static final AbilityPoints Level_1_INTELLIGENCE = AbilityPoints.of(2);
    public static final AbilityPoints Level_1_ATTACK = AbilityPoints.of(2);
    public static final AbilityPoints Level_1_ALCHEMY = AbilityPoints.of(1);

    public static final AbilityPoints Level_2_ATTACK = AbilityPoints.of(3);
    public static final AbilityPoints Level_2_ALCHEMY = AbilityPoints.of(4);

    public static final AbilityPoints Level_3_VISION = AbilityPoints.of(1);

    public static final AbilityPoints Level_4_DEFENSE = AbilityPoints.of(1);

    public static final AbilityPoints Level_5_DEFENSE = AbilityPoints.of(2);
    public static final AbilityPoints Level_5_ATTACK = AbilityPoints.of(4);
}
