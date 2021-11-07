package metro.entity;

abstract class NamedEntity {
    private final String name;

    NamedEntity(final String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public boolean isNameEquals(String name) {
        return this.name.equalsIgnoreCase(name);
    }

}
