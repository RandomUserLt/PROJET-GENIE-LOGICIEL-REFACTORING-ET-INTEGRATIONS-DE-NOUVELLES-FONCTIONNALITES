package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;
import static re.forestier.edu.rpg.Literaux.*;

public class Player {

    private String playerName;
    private String avatarName;

    private String avatarClass;

    private Integer money;

    private int level;
    private int healthpoints;
    private int currenthealthpoints;
    private int xp;

    private HashMap<String, Integer> abilities;
    private ArrayList<String> inventory;

    public Player(String playerName, String avatarName, String avatarClass, int money, ArrayList<String> inventory) {

        if (!ARCHER.equals(avatarClass) && !ADVENTURER.equals(avatarClass) && !DWARF.equals(avatarClass)) {
            return;
        }

        this.playerName = playerName;
        this.avatarName = avatarName;
        this.avatarClass = avatarClass;
        var perType_A = UpdatePlayer.abilitiesPerTypeAndLevel().get(this.avatarClass);
        this.abilities = (perType_A != null && perType_A.get(1) != null)
                ? perType_A.get(1)
                : new java.util.HashMap<>();

        this.money = money;
        this.inventory = (inventory != null) ? inventory : new ArrayList<>();
        var perType_B = UpdatePlayer.abilitiesPerTypeAndLevel().get(avatarClass);
        this.abilities = (perType_B != null && perType_B.get(1) != null) ? perType_B.get(1) : new java.util.HashMap<>();
    }

    @Override
    public String toString() {
        StringBuilder affichage = new StringBuilder();
        affichage.append(JOUEUR_MOT).append(this.getAvatarName())
                .append(JOUE_PAR).append(this.getPlayerName());

        affichage.append(NIVEAU).append(this.retrieveLevel())
                .append(XP_TOTALE).append(this.getXp()).append(")");

        affichage.append(CAPACITE);
        this.getAbilities().forEach((name, level) -> {
            affichage.append(BLANK).append(name).append(COLUMN).append(level);
        });

        affichage.append(INVENTAIRE_MOT);
        this.getInventory().forEach(item -> {
            affichage.append(BLANK).append(item);
        });

        return affichage.toString();
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public String getAvatarClass() {
        return avatarClass;
    }

    public Integer getMoney() {
        return money;
    }

    public int getHealthpoints() {
        return healthpoints;
    }

    public void setHealthpoints(int healthpoints) {
        this.healthpoints = healthpoints;
    }

    public int getCurrenthealthpoints() {
        return currenthealthpoints;
    }

    public void setCurrenthealthpoints(int currenthealthpoints) {
        this.currenthealthpoints = currenthealthpoints;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public HashMap<String, Integer> getAbilities() {
        return abilities;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    public void removeMoney(int amount) throws IllegalArgumentException {
        if (money - amount < 0) {
            throw new IllegalArgumentException(NEGATIVE_MONEY_EXCEPTION);
        }
        money = money - amount;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    private static final java.util.NavigableMap<Integer, Integer> XP_TO_LEVEL = new java.util.TreeMap<>();

    static {
        enregistrerPalier(0, 1);
        enregistrerPalier(10, 2);
        enregistrerPalier(27, 3);
        enregistrerPalier(57, 4);
        enregistrerPalier(111, 5);
        // TODO : les niveaux suivants
    }

    private static void enregistrerPalier(int xpMinInclus, int niveau) {
        XP_TO_LEVEL.put(xpMinInclus, niveau);
    }

    public static int obtenirNiveauDepuisXp(int xp) {
        int safeXp = Math.max(0, xp);
        return XP_TO_LEVEL.floorEntry(safeXp).getValue();
    }

    public int retrieveLevel() {
        return obtenirNiveauDepuisXp(this.xp);
    }

}
