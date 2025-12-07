package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import re.forestier.edu.rpg.models.Ability;
import re.forestier.edu.rpg.models.GameObject;
import re.forestier.edu.rpg.models.GameObjectCatalog;
import static re.forestier.edu.rpg.models.GameObjectCatalog.*;

import static re.forestier.edu.rpg.view.Literaux.*;
import re.forestier.edu.rpg.models.GameObject;
import re.forestier.edu.rpg.progression.*;
import re.forestier.edu.rpg.progression.LevelProgression;
import static re.forestier.edu.rpg.models.Ability.*;

public abstract class Player {
    private String playerName;
    private String avatarName;
    private Integer money;
    protected int level;
    private int xp;
    private int healthpoints;
    private int currenthealthpoints;
    protected HashMap<String, Integer> abilities;
    protected ArrayList<GameObject> inventory = new ArrayList<>();
    private int maxCarryWeight = Integer.MAX_VALUE;

    // --- CONSTRUCTEUR ----------------------------------------------------------

    public Player(String playerName, String avatarName, int money, ArrayList<GameObject> inventory) {

        this.playerName = playerName;
        this.avatarName = avatarName;
        this.money = money;
        this.inventory = (inventory != null) ? inventory : new ArrayList<>();
        this.abilities = new HashMap<>();
        this.level = LevelProgression.obtenirNiveauDepuisXp(this.xp);
    }

    public String toMarkdown() {
        return "";
    }; // à implémenter

    public void addItem(GameObject o) {
        if (o == null) {
            throw new IllegalArgumentException("item is null");
        }
        int newWeight = getInventoryWeight() + o.getWeight();
        if (newWeight > getMaxCarryWeight()) {
            throw new IllegalArgumentException("Max weight exceeded");
        }
        inventory.add(o);
    }

    public void sell(GameObject o) {
        if (o == null) {
            throw new IllegalArgumentException("item is null");
        }
        int pos = inventory.indexOf(o);
        if (pos < 0) {
            throw new java.util.NoSuchElementException("item not in inventory");
        }
        GameObject removed = inventory.remove(pos);
        addMoney(removed.getValue());
    }

    protected void initBaseAbilities(Map<Integer, ? extends Map<String, Integer>> levelAbilities) {
        // Capacités de base niveau 1
        Map<String, Integer> baseAbilities = levelAbilities.get(1);
        if (baseAbilities != null) {
            getAbilities().putAll(baseAbilities);
        }

        int current = LevelProgression.obtenirNiveauDepuisXp(getXp());
        for (int lvl = 2; lvl <= current; lvl++) {
            onLevelUp(lvl);
        }
        setLevel(current);
    }

    public final void majFinDeTour() {
        if (estKo()) {
            System.out.println(MSG_IS_KO);
            return;
        }

        regenererSiBlesse();
        limiterPointsDeVieAuMax();
    }

    private void regenererSiBlesse() {
        if (getCurrenthealthpoints() < getHealthpoints() / 2) {
            int gain = calculGainFinDeTour();
            setCurrenthealthpoints(getCurrenthealthpoints() + gain);
        }
    }

    private void limiterPointsDeVieAuMax() {
        if (getCurrenthealthpoints() > getHealthpoints()) {
            setCurrenthealthpoints(getHealthpoints());
        }
    }

    protected abstract int calculGainFinDeTour();

    public boolean estKo() {
        return getCurrenthealthpoints() == 0;
    }

    protected void onLevelUp(int lvl) {
        GameObject loot = GameObjectCatalog.random();
        try {
            addItem(loot);
        } catch (IllegalArgumentException ignore) {
        }

        Map<String, Integer> abilitiesToAdd = getSubClassLevelAbilities(lvl);
        if (abilitiesToAdd != null) {
            abilitiesToAdd.forEach((ability, value) -> getAbilities().put(ability, value));
        }
        setLevel(lvl);
    }

    public void addXp(int xp) {
        int oldLevel = retrieveLevel();
        setXp(getXp() + xp);
        int newLevel = retrieveLevel();

        if (newLevel > oldLevel) {
            for (int lvl = oldLevel + 1; lvl <= newLevel; lvl++) {
                onLevelUp(lvl);
            }
        }
    }

    public boolean addXpWithLevelCheck(int xp) {
        int oldLevel = retrieveLevel();
        addXp(xp);
        return retrieveLevel() > oldLevel;
    }

    public Map<String, Integer> getLevelAbilities(int level) {
        return getSubClassLevelAbilities(level);
    }

    protected Map<String, Integer> getSubClassLevelAbilities(int level) {
        throw new UnsupportedOperationException("Chaque sous-classe doit implémenter getSubClassLevelAbilities");
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

    /*----------------------------GETTERS ET SETTERS ---------------------------------------------*/

    public ArrayList<GameObject> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<GameObject> inventory) {
        this.inventory = (inventory != null) ? inventory : new ArrayList<>();
    }

    public int getMaxCarryWeight() {
        return maxCarryWeight;
    }

    public void setMaxCarryWeight(int w) {
        if (w < 0)
            throw new IllegalArgumentException("maxCarryWeight must be >= 0");
        this.maxCarryWeight = w;
    }

    public int getInventoryWeight() {
        int sum = 0;
        for (var it : inventory)
            sum += it.getWeight();
        return sum;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public Integer getMoney() {
        return money;
    }

    public void removeMoney(int amount) {
        if (money - amount < 0) {
            throw new IllegalArgumentException(NEGATIVE_MONEY_EXCEPTION);
        }
        money -= amount;
    }

    public void addMoney(int amount) {
        money += amount;
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

    public void setCurrenthealthpoints(int hp) {
        this.currenthealthpoints = hp;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int lvl) {
        this.level = lvl;
    }

    public HashMap<String, Integer> getAbilities() {
        return abilities;
    }

    public int retrieveLevel() {
        return LevelProgression.obtenirNiveauDepuisXp(this.xp);
    }

}
