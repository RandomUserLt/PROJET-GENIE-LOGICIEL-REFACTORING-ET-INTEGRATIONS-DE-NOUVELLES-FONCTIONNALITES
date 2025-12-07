package re.forestier.edu.rpg.models;

public final class GameObject {

    private final String name;
    private final String description;
    private final int weight;
    private final int value;

    public GameObject(String name, String description, int weight, int value) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("le nom d'un objet ne peut pas etre vide");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("le poids d'un objet doit etre >= 0");
        }
        if (value < 0) {
            throw new IllegalArgumentException("la valeur d'un objet doit etre >= 0");
        }
        this.name = name;
        this.description = (description == null) ? "" : description;
        this.weight = weight;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "GameObject{name='" + name + "', weight=" + weight + ", value=" + value + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GameObject that))
            return false;
        return weight == that.weight
                && value == that.value
                && name.equals(that.name)
                && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int mixer = 31;
        int res = name.hashCode();
        res = mixer * res + description.hashCode();
        res = mixer * res + weight;
        res = mixer * res + value;
        return res;
    }

}
