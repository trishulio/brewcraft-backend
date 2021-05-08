package io.company.brewcraft.security.idp;

/**
 * Contains information about attribute that is used stored in idp's user pool
 */
public class UserAttributeType {

    private String name;

    private String value;

    public UserAttributeType(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
