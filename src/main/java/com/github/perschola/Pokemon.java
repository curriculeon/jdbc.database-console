package com.github.perschola;

public class Pokemon {
    Integer id;
    String name;
    Integer primaryType;
    Integer secondaryType;

    public Pokemon(String name, Integer primaryType, Integer secondaryType) {
        this.name = name;
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
    }

    public Pokemon(Integer id, String name, Integer primaryType, Integer secondaryType) {
        this.id = id;
        this.name = name;
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(Integer primaryType) {
        this.primaryType = primaryType;
    }

    public Integer getSecondaryType() {
        return secondaryType;
    }

    public void setSecondaryType(Integer secondaryType) {
        this.secondaryType = secondaryType;
    }
}
