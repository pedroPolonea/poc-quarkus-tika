package org.acme.domain;

public enum CustomersEnum {

    NAME("Name", 0, true),
    HASH("hash", 1, false),
    AGE("Age", 2, false);

    private String name;

    private int position;

    private boolean autoSize;

    CustomersEnum(final String name, final int position, final boolean autoSize) {
        this.name = name;
        this.position = position;
        this.autoSize = autoSize;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}
