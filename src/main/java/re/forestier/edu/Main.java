package re.forestier.edu;

import java.util.ArrayList;

import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.Player;
import re.forestier.edu.rpg.UpdatePlayer;

public class Main {
    public static void main(String[] args) {
        Player firstPlayer = new Player("Florian", "Ruzberg de Rivehaute", "DWARF", 200, new ArrayList<>());
        firstPlayer.addMoney(400);

        UpdatePlayer.addXp(firstPlayer, 15);
        System.out.println(Affichage.afficherJoueur(firstPlayer));
        System.out.println("------------------");
        UpdatePlayer.addXp(firstPlayer, 20);
        System.out.println(Affichage.afficherJoueur(firstPlayer));
    }
}
