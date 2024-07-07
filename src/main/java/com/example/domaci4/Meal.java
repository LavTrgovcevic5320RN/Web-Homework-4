package com.example.domaci4;

public class Meal {
    private String day;
    private String food;
    private int orderNumber;

    public Meal(String day, String food) {
        this.day = day;
        this.food = food;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
