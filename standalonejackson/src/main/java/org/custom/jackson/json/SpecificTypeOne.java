package org.custom.jackson.json;


public class SpecificTypeOne extends GenericType{
    private String propertyName ;
    public String getPropertyName() {
        if (propertyName == null) {
            String className = this.getClass().getSimpleName();
            return className.substring(0, 1).toLowerCase() + className.substring(1);
        }else{
            return propertyName;
        }
    }
    public void setPropertyName(String propertyName){
        this.propertyName = propertyName;
    }

    public void specifBehaviour(){

    }
}
