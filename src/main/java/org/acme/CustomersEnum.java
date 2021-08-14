package org.acme;

public enum CustomersEnum {

    NAME("Name", 0),
    HASH("hash", 1),
    AGE("Age", 2);

    private String name;

    private int position;

    CustomersEnum(final String name, final int position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}
