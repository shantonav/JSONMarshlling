package org.custom.jackson.json;


public class SpecificTypeTwo extends GenericType {

    public String getPropertyName() {
        String className = this.getClass().getSimpleName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }
}
