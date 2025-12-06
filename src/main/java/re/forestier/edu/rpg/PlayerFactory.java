package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.Locale;

import re.forestier.edu.rpg.Adventurer;
import re.forestier.edu.rpg.Archer;
import re.forestier.edu.rpg.Dwarf;
import re.forestier.edu.rpg.Player;

import static re.forestier.edu.rpg.AvatarClass.*;

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
        AvatarClass type = AvatarClass.valueOf(t);
        switch (type) {
            case ARCHER:
                return new Archer(playerName, avatarName, money, inventory);
            case ADVENTURER:
                return new Adventurer(playerName, avatarName, money, inventory);
            case DWARF:
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
