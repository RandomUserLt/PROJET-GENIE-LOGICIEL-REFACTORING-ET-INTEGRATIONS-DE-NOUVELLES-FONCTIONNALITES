package re.forestier.edu.rpg;

import java.util.ArrayList;

import re.forestier.edu.rpg.Player;

import static re.forestier.edu.rpg.Literaux.ARCHER;

public final class Archer extends Player {

    public Archer(String playerName,
            String avatarName,
            String avatarClass,
            int money,
            ArrayList<String> inventory) {
        super(playerName, avatarName, ARCHER, money, inventory);
    }
}
