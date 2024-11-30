package com.example.petpalooza;

import java.io.Serializable;

public class Pet implements Serializable {
    private String type;
    private String name; // Store the name of the pet
    private int cleanliness;
    private int hunger;
    private int energy;

    public Pet(String type) {
        this.type = type;
        this.cleanliness = 100;
        this.hunger = 100;
        this.energy = 100;
        this.name = ""; // Default to an empty string
    }

    public Pet(String type, String name, int cleanliness, int hunger, int energy) {
        this.type = type;
        this.name = name;
        this.cleanliness = cleanliness;
        this.hunger = hunger;
        this.energy = energy;
    }

    public String getType() { return type; }
    public String getName() { return name; }
    public int getCleanliness() { return cleanliness; }
    public int getHunger() { return hunger; }
    public int getEnergy() { return energy; }

    public void setName(String name) {
        this.name = name;
    }

    public void setHunger(int hunger){
        this.hunger = hunger;
    }

    public void setCleanliness(int cleanliness){
        this.cleanliness = cleanliness;
    }

    public void setEnergy(int energy){
        this.energy = energy;
    }
}
