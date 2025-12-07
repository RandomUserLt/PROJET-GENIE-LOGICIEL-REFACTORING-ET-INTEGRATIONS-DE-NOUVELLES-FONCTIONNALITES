package re.forestier.edu;

import static org.junit.jupiter.api.Assertions.*;
import static re.forestier.edu.rpg.models.Ability.*;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.Player;
import re.forestier.edu.rpg.PlayerFactory;
import re.forestier.edu.rpg.AvatarClass;
import re.forestier.edu.rpg.models.GameObject;
import static re.forestier.edu.rpg.models.GameObjectCatalog.*;

@DisplayName("Goblin  tests TDD")
class GoblinTest {

    private Player newGoblin() {
        return PlayerFactory.fromType(
                AvatarClass.GOBLIN.name(),
                "Gina", "GobAvatar", 0,
                new ArrayList<GameObject>());
    }

    @Nested
    @DisplayName("Factory")
    class Factory {

        @Test
        @DisplayName("GOBLIN est supporté")
        void supports_goblin() {
            assertTrue(PlayerFactory.isSupported(AvatarClass.GOBLIN.name()));
        }

        @Test
        @DisplayName("Création d'une instance concrète")
        void creates_concrete_instance() {
            Player p = newGoblin();
            assertNotNull(p);
            assertEquals("Goblin", p.getClass().getSimpleName());
        }
    }

    @Nested
    @DisplayName("Capacités par niveau")
    class Levels {

        @Test
        @DisplayName("Niveau 1 (XP=0) : INT=2, ATK=2, ALC=1")
        void level1() {
            Player p = newGoblin();
            assertEquals(1, p.retrieveLevel());
            Map<String, Integer> ab = p.getAbilities();

            assertEquals(2, ab.get(INTELLIGENCE.name()));
            assertEquals(2, ab.get(ATTACK.name()));
            assertEquals(1, ab.get(ALCHEMY.name()));
            assertFalse(ab.containsKey(VISION.name()));
            assertFalse(ab.containsKey(DEFENSE.name()));
        }

        @Test
        @DisplayName("Niveau 2 (XP=10) : ATK=3, ALC=4 (INT=2 conservé)")
        void level2() {
            Player p = newGoblin();
            p.addXp(10);
            assertEquals(2, p.retrieveLevel());

            Map<String, Integer> ab = p.getAbilities();
            assertEquals(2, ab.get(INTELLIGENCE.name()));
            assertEquals(3, ab.get(ATTACK.name()));
            assertEquals(4, ab.get(ALCHEMY.name()));
            assertFalse(ab.containsKey(VISION.name()));
            assertFalse(ab.containsKey(DEFENSE.name()));
        }

        @Test
        @DisplayName("Niveau 3 (XP=27) : +VIS=1")
        void level3() {
            Player p = newGoblin();
            p.addXp(27);
            assertEquals(3, p.retrieveLevel());

            Map<String, Integer> ab = p.getAbilities();
            assertEquals(2, ab.get(INTELLIGENCE.name()));
            assertEquals(3, ab.get(ATTACK.name()));
            assertEquals(4, ab.get(ALCHEMY.name()));
            assertEquals(1, ab.get(VISION.name()));
            assertFalse(ab.containsKey(DEFENSE.name()));
        }

        @Test
        @DisplayName("Niveau 4 (XP=57) : +DEF=1")
        void level4() {
            Player p = newGoblin();
            p.addXp(57);
            assertEquals(4, p.retrieveLevel());

            Map<String, Integer> ab = p.getAbilities();
            assertEquals(2, ab.get(INTELLIGENCE.name()));
            assertEquals(3, ab.get(ATTACK.name()));
            assertEquals(4, ab.get(ALCHEMY.name()));
            assertEquals(1, ab.get(VISION.name()));
            assertEquals(1, ab.get(DEFENSE.name()));
        }

        @Test
        @DisplayName("Niveau 5 (XP total 300) : DEF=2, ATK=4, acquis conservés")
        void level5() {
            Player p = newGoblin();
            p.addXp(200);
            p.addXp(100);
            assertEquals(5, p.retrieveLevel());

            Map<String, Integer> ab = p.getAbilities();
            assertEquals(2, ab.get(INTELLIGENCE.name()));
            assertEquals(4, ab.get(ALCHEMY.name()));
            assertEquals(1, ab.get(VISION.name()));
            assertEquals(2, ab.get(DEFENSE.name()));
            assertEquals(4, ab.get(ATTACK.name()));
        }
    }
}
