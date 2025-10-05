package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {

    // tentative de gestion des "magic strings"
    // pour les différents types de joueurs
    // l'alternative était une enum mais cela introduisait
    // trop de modification notamment sur les tests
    private static final String ARCHER = "ARCHER";
    private static final String ADVENTURER = "ADVENTURER";
    private static final String DWARF = "DWARF";

    private String playerName;
    private String avatarName;

    private String avatarClass;

    private Integer money;
    // private Float realMoney; // n'est jamais utilisé

    private int level;
    private int healthpoints;
    private int currenthealthpoints;
    private int xp;

    private HashMap<String, Integer> abilities;
    private ArrayList<String> inventory;

    public Player(String playerName, String avatarName, String avatarClass, int money, ArrayList<String> inventory) {
        // if (!avatarClass.equals("ARCHER") && !avatarClass.equals("ADVENTURER") &&
        // !avatarClass.equals("DWARF")) {
        // return;
        // }
        if (!ARCHER.equals(avatarClass) && !ADVENTURER.equals(avatarClass) && !DWARF.equals(avatarClass)) {
            return;
        }

        this.playerName = playerName;
        this.avatarName = avatarName;
        this.avatarClass = avatarClass;
        this.money = money;
        // this.inventory = inventory;
        this.inventory = (inventory != null) ? inventory : new ArrayList<>();
        // this.abilities =
        // UpdatePlayer.abilitiesPerTypeAndLevel().get(avatarClass).get(1);
        var perType = UpdatePlayer.abilitiesPerTypeAndLevel().get(avatarClass);
        this.abilities = (perType != null && perType.get(1) != null) ? perType.get(1) : new java.util.HashMap<>();
    }

    @Override
    public String toString() {
        StringBuilder affichage = new StringBuilder();
        affichage.append("Joueur ").append(this.getAvatarName())
                .append(" joué par ").append(this.getPlayerName());

        affichage.append("\nNiveau : ").append(this.retrieveLevel())
                .append(" (XP totale : ").append(this.getXp()).append(")");

        affichage.append("\n\nCapacités :");
        this.getAbilities().forEach((name, level) -> {
            affichage.append("\n   ").append(name).append(" : ").append(level);
        });

        affichage.append("\n\nInventaire :");
        this.getInventory().forEach(item -> {
            affichage.append("\n   ").append(item);
        });

        return affichage.toString();
    }

    // ---- Getters & Setters ----
    public String getPlayerName() {
        return playerName;
    }

    // public void setPlayerName(String playerName) {
    // this.playerName = playerName;
    // }

    public String getAvatarName() {
        return avatarName;
    }

    // public void setAvatarName(String avatarName) {
    // this.avatarName = avatarName;
    // }

    public String getAvatarClass() {
        return avatarClass;
    }

    // public void setAvatarClass(String avatarClass) {
    // this.avatarClass = avatarClass;
    // }

    public Integer getMoney() {
        return money;
    }

    // public void setMoney(Integer money) {
    // this.money = money;
    // }

    // public Float getRealMoney() {
    // return realMoney;
    // }

    // public void setRealMoney(Float realMoney) {
    // this.realMoney = realMoney;
    // }

    // public int getLevel() {
    // return level;
    // }

    // public void setLevel(int level) {
    // this.level = level;
    // }

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

    // public void setAbilities(HashMap<String, Integer> abilities) {
    // this.abilities = abilities;
    // }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    public void removeMoney(int amount) throws IllegalArgumentException {
        if (money - amount < 0) {
            throw new IllegalArgumentException("Player can't have a negative money!");
        }
        money = money - amount;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    private static final java.util.NavigableMap<Integer, Integer> XP_TO_LEVEL = new java.util.TreeMap<>();
    static {
        XP_TO_LEVEL.put(0, 1);
        XP_TO_LEVEL.put(10, 2);
        XP_TO_LEVEL.put(27, 3);
        XP_TO_LEVEL.put(57, 4);
        XP_TO_LEVEL.put(111, 5);

    }

    public int retrieveLevel() {

        int safeXp = Math.max(0, this.xp);
        return XP_TO_LEVEL.floorEntry(safeXp).getValue();
    }

}
