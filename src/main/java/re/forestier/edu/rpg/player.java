package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;

public class Player { // la visibilité des attributs c'est n'importe quoi
    // liste des attributs
    private String playerName;
    private String Avatar_name;
    // protected String avatarName;
    private String avatarClass;

    private Integer money;
    private Float realMoney;

    private int level;
    private int healthpoints;
    private int currenthealthpoints;
    private int xp;

    private HashMap<String, Integer> abilities;
    private ArrayList<String> inventory;

    // constructeur
    public Player(String playerName, String avatarName, String avatarClass, int money, ArrayList<String> inventory) {
        if (!avatarClass.equals("ARCHER") && !avatarClass.equals("ADVENTURER") && !avatarClass.equals("DWARF")) {
            return;
        }

        this.playerName = playerName;
        this.Avatar_name = avatarName;
        this.avatarClass = avatarClass;
        this.money = money;
        this.inventory = inventory;
        this.abilities = UpdatePlayer.abilitiesPerTypeAndLevel().get(avatarClass).get(1);
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

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getAvatarName() {
        return Avatar_name;
    }

    public void setAvatarName(String avatarName) {
        this.Avatar_name = avatarName;
    }

    public String getAvatarClass() {
        return avatarClass;
    }

    public void setAvatarClass(String avatarClass) {
        this.avatarClass = avatarClass;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Float getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Float realMoney) {
        this.realMoney = realMoney;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public void setAbilities(HashMap<String, Integer> abilities) {
        this.abilities = abilities;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    // ---- voir plus tard si judicieux de fusionner les deux méthodes
    public void removeMoney(int amount) throws IllegalArgumentException {
        if (money - amount < 0) {
            throw new IllegalArgumentException("Player can't have a negative money!");
        }
        money = money - amount;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    /*
     * public int retrieveLevel() {
     * 
     * HashMap<
     * 
     * Integer, Integer> levels = new HashMap<>();
     * levels.put(2, 10);
     * levels.put(3, 27);
     * levels.put(4, 57);
     * levels.put(5, 111);
     * // TODO : ajouter les prochains niveaux
     * 
     * if (xp < levels.get(2))
     * return 1;
     * else if (xp < levels.get(3))
     * return 2;
     * else if (xp < levels.get(4))
     * return 3;
     * else if (xp < levels.get(5))
     * return 4;
     * return 5;
     * }
     */

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
