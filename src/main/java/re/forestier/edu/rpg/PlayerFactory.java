package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.Locale;

import static re.forestier.edu.rpg.Literaux.*;

public final class PlayerFactory {

    private PlayerFactory() {
    }

    public static Player fromType(String userType,
            String playerName,
            String avatarName,
            int money,
            ArrayList<String> inventory) {
        if (userType == null)
            return null;

        String t = userType.trim().toUpperCase(Locale.ROOT);
        switch (t) {
            case ARCHER:
                // return new Archer(playerName, avatarName, ARCHER, money, inventory);
                return new Archer(playerName, avatarName, money, inventory);
            case ADVENTURER:
                // return new Adventurer(playerName, avatarName, ADVENTURER, money, inventory);
                return new Adventurer(playerName, ADVENTURER, money, inventory);
            case DWARF:
                // return new Dwarf(playerName, avatarName, DWARF, money, inventory);
                return new Dwarf(playerName, avatarName, money, inventory);
            default:
                return null;
        }
    }

    public static boolean isSupported(String userType) {
        if (userType == null)
            return false;
        String t = userType.trim().toUpperCase(Locale.ROOT);
        return ARCHER.equals(t) || ADVENTURER.equals(t) || DWARF.equals(t);
    }
}
