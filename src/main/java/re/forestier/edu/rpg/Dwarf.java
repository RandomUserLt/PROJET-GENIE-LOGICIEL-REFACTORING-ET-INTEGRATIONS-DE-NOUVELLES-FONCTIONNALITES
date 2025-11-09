package re.forestier.edu.rpg;

import java.util.ArrayList;

import re.forestier.edu.rpg.Player;

import static re.forestier.edu.rpg.Literaux.DWARF;

public final class Dwarf extends Player {

    public Dwarf(String playerName,
            String avatarName,
            String avatarClass,
            int money,
            ArrayList<String> inventory) {
        super(playerName, avatarName, DWARF, money, inventory);
    }
}