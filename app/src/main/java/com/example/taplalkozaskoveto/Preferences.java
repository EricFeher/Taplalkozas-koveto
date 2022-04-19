package com.example.taplalkozaskoveto;

public class Preferences {
    private String id;
    private double calories;
    private double fat;
    private double protein;
    private double fiber;
    private String uid;

    public Preferences() {
    }

    public Preferences( double calories, double fat, double protein, double fiber,String uid) {
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.fiber = fiber;
        this.uid = uid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String _getId() {
        return id;
    }

    public double getCalories() {
        return calories;
    }

    public double getFat() {
        return fat;
    }

    public double getProtein() {
        return protein;
    }

    public double getFiber() {
        return fiber;
    }

    public String getUid() {
        return uid;
    }
}
