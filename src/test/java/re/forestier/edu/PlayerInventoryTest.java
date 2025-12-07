package re.forestier.edu;

import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.*;
import re.forestier.edu.rpg.models.GameObject;
import static re.forestier.edu.rpg.models.GameObjectCatalog.*;

public class PlayerInventoryTest {

    private Adventurer createAdventurerWithMoneyAndInv(int money, ArrayList<GameObject> inv) {
        return new Adventurer("TestPlayer", "Avatar", money, inv);
    }

    /* SELL : vendre un objet de l’inventaire */
    @Test
    @DisplayName("sell : retire l'objet de l'inventaire et crédite l'argent du joueur")
    void sellRemovesObjectAndAddsMoney() {
        ArrayList<GameObject> inv = new ArrayList<>();
        inv.add(MAGIC_BOW);

        Adventurer p = createAdventurerWithMoneyAndInv(0, inv);

        int initialMoney = p.getMoney();
        int value = MAGIC_BOW.getValue();

        p.sell(MAGIC_BOW);

        assertFalse(p.getInventory().contains(MAGIC_BOW),
                "Après la vente, l'objet ne doit plus être dans l'inventaire");
        assertEquals(initialMoney + value, p.getMoney(), "La vente doit créditer le joueur de la valeur de l'objet");
    }

    @Test
    @DisplayName("sell : vendre un objet absent de l'inventaire provoque une erreur")
    void sellObjectNotInInventoryThrows() {
        Adventurer p = createAdventurerWithMoneyAndInv(0, new ArrayList<>());

        assertThrows(NoSuchElementException.class, () -> p.sell(MAGIC_BOW),
                "Vendre un objet absent de l'inventaire doit provoquer une NoSuchElementException");
    }

    /* LIMITE MAXIMALE DE POIDS */
    @Test
    @DisplayName("addItem : accepte les objets tant que le poids max n'est pas dépassé")
    void addToInventoryWithinMaxWeightIsAccepted() {
        Adventurer p = createAdventurerWithMoneyAndInv(0, new ArrayList<>());
        p.setMaxCarryWeight(3);
        p.addItem(LOOKOUT_RING);
        p.addItem(RUNE);
        assertTrue(p.getInventory().contains(LOOKOUT_RING), "L'inventaire doit contenir LOOKOUT_RING après addItem");
        assertTrue(p.getInventory().contains(RUNE), "L'inventaire doit contenir RUNE après addItem");
        int totalWeight = p.getInventory().stream()
                .mapToInt(GameObject::getWeight)
                .sum();

        assertEquals(3, totalWeight, "Le poids total de l'inventaire doit être égal au poids max configuré");
    }

    @Test
    @DisplayName("addToInventory : refuser un objet qui ferait dépasser le poids maximal")
    void addToInventoryBeyondMaxWeightIsRejected() {
        Adventurer p = createAdventurerWithMoneyAndInv(0, new ArrayList<>());
        p.setMaxCarryWeight(3);
        p.addItem(LOOKOUT_RING);
        p.addItem(RUNE);
        int weightBefore = p.getInventory().stream()
                .mapToInt(GameObject::getWeight)
                .sum();
        int sizeBefore = p.getInventory().size();
        assertThrows(IllegalArgumentException.class,
                () -> p.addItem(MAGIC_BOW),
                "Dépasser la capacité max doit provoquer une IllegalArgumentException");
        assertFalse(p.getInventory().contains(MAGIC_BOW),
                "L'objet qui dépasse la capacité ne doit pas être ajouté à l'inventaire");

        int weightAfter = p.getInventory().stream()
                .mapToInt(GameObject::getWeight)
                .sum();
        int sizeAfter = p.getInventory().size();

        assertEquals(weightBefore, weightAfter,
                "Le poids total ne doit pas changer si l'ajout dépasse la capacité max");
        assertEquals(sizeBefore, sizeAfter,
                "La taille de l'inventaire ne doit pas changer si l'ajout dépasse la capacité max");
        assertTrue(weightAfter <= p.getMaxCarryWeight(),
                "Après tentative échouée, le poids total ne doit jamais dépasser la capacité maximale");
    }

}
