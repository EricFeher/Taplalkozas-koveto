package com.example.taplalkozaskoveto;

public class Food {
    private String name;
    private int calories;
    private int protein;
    private int fiber;
    private int fat;

    public Food(String name, int calories, int protein, int fiber, int fat) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fiber = fiber;
        this.fat = fat;
    }

    public Food() {
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public int getProtein() {
        return protein;
    }

    public int getFiber() {
        return fiber;
    }

    public int getFat() {
        return fat;
    }
}
