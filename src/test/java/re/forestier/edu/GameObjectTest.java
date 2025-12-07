package re.forestier.edu;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.models.GameObject;

@DisplayName("GameObject – vrai objet (nom, description, poids, valeur)")
class GameObjectTest {

    private GameObject mk(String n, String d, int w, int v) {
        return new GameObject(n, d, w, v);
    }

    @Nested
    @DisplayName("Création & getters")
    class Creation {

        @Test
        @DisplayName("Expose correctement name/description/weight/value")
        void exposes_properties() {
            var obj = mk("Magic Bow", "Arc ancien aux runes bleues", 3, 120);

            assertEquals("Magic Bow", obj.getName());
            assertEquals("Arc ancien aux runes bleues", obj.getDescription());
            assertEquals(3, obj.getWeight());
            assertEquals(120, obj.getValue());
        }

        @Test
        @DisplayName("Description null -> chaîne vide")
        void null_description_becomes_empty() {
            var obj = mk("Rock", null, 1, 0);
            assertEquals("", obj.getDescription());
        }
    }

    @Nested
    @DisplayName("Validation")
    class Validation {

        @Test
        @DisplayName("Nom vide ou blanc -> IllegalArgumentException")
        void blank_name_rejected() {
            assertThrows(IllegalArgumentException.class, () -> mk("", "x", 1, 1));
            assertThrows(IllegalArgumentException.class, () -> mk("   ", "x", 1, 1));
        }

        @Test
        @DisplayName("Poids négatif -> IllegalArgumentException")
        void negative_weight_rejected() {
            assertThrows(IllegalArgumentException.class, () -> mk("Anvil", "Too heavy", -1, 50));
        }

        @Test
        @DisplayName("Valeur négative -> IllegalArgumentException")
        void negative_value_rejected() {
            assertThrows(IllegalArgumentException.class, () -> mk("Junk", "worthless", 0, -5));
        }
    }

    @Nested
    @DisplayName("Immutabilité & représentation")
    class ImmutabilityAndToString {

        @Test
        @DisplayName("Champs non statiques déclarés final")
        void fields_are_final() {
            var fields = GameObject.class.getDeclaredFields();
            for (var f : fields) {
                if (!Modifier.isStatic(f.getModifiers())) {
                    assertTrue(Modifier.isFinal(f.getModifiers()),
                            () -> "Field should be final: " + f.getName());
                }
            }
        }

        @Test
        @DisplayName("toString() contient au moins le nom")
        void toString_contains_name() {
            var obj = mk("Holy Elixir", "Heal", 1, 50);
            assertTrue(obj.toString().contains("Holy Elixir"));
        }
    }
}
