package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.List;

import re.forestier.edu.rpg.Player;

import static re.forestier.edu.rpg.Literaux.ADVENTURER;

public final class Adventurer extends Player {

    public Adventurer(String playerName,
            String avatarName,
            String avatarClass,
            int money,
            ArrayList<String> inventory) {
        super(playerName, avatarName, ADVENTURER, money, inventory);
    }
} 