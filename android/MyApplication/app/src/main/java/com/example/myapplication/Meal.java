package com.example.myapplication;

import java.util.Date;

public class Meal {
    private Integer mealID;
    private Integer mealType;
    private Date mealDate;
    private Integer userID;

    Meal(){}

    public Meal(Integer mealID, Integer mealType, Date mealDate, Integer userID) {
        this.mealID = mealID;
        this.mealType = mealType;
        this.mealDate = mealDate;
        this.userID = userID;
    }

    public Integer getMealID() {
        return mealID;
    }

    public void setMealID(Integer mealID) {
        this.mealID = mealID;
    }

    public Integer getMealType() {
        return mealType;
    }

    public void setMealType(Integer mealType) {
        this.mealType = mealType;
    }

    public Date getMealDate() {
        return mealDate;
    }

    public void setMealDate(Date mealDate) {
        this.mealDate = mealDate;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
