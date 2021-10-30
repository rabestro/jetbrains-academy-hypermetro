package metro.entity;

abstract class NamedEntity {
    private final String name;

    NamedEntity(final String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

}
