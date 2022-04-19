package com.example.taplalkozaskoveto;

public class Food {
    private String id;
    private String UID;
    private String name;
    private double calories;
    private double protein;
    private double fiber;
    private double fat;
    private String date;
    private String time;


    public Food(String UID, String name, double calories, double protein, double fiber, double fat, String date) {
        this.UID=UID;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fiber = fiber;
        this.fat = fat;
        this.date=date.split(" ")[0];
        this.time=date.split(" ")[1];
    }

    public String getTime() {
        return time;
    }

    public Food() {
    }

    public String getUID() {
        return UID;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public double getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getFiber() {
        return fiber;
    }

    public double getFat() {
        return fat;
    }

    public String _getId() {
        return id;
    }

    public void setId(String id){
        this.id=id;
    }
}
