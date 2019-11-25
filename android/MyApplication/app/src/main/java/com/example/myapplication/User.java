package com.example.myapplication;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private Integer userID;
    private String userPassword;
    private String userFirsName;
    private String userLastName;
    private String userEmailAdress;
    private int userSex;
    private Integer userWeight;
    private Integer userHeight;
    //private ArrayList<String>userAlergies;
    private int userCaloriesIntakeDaily;
    private int userWeightGoal;
    private String userName;
    private Date userBirthDate;

    public User(){}

    public User(Integer userID, String userPassword, String userFirsName, String userLastName,
                String userEmailAdress, int userSex, int userWeight, int userHeight
            /*,ArrayList<String> userAlergies*/, int userCaloriesIntakeDaily, int userWeightGoal,
                String userName, Date userBirthDate) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userFirsName = userFirsName;
        this.userLastName = userLastName;
        this.userEmailAdress = userEmailAdress;
        this.userSex = userSex;
        this.userWeight = userWeight;
        this.userHeight = userHeight;
        //this.userAlergies = userAlergies;
        this.userCaloriesIntakeDaily = userCaloriesIntakeDaily;
        this.userWeightGoal = userWeightGoal;
        this.userName = userName;
        this.userBirthDate = userBirthDate;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserFirsName() {
        return userFirsName;
    }

    public void setUserFirsName(String userFirsName) {
        this.userFirsName = userFirsName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmailAdress() {
        return userEmailAdress;
    }

    public void setUserEmailAdress(String userEmailAdress) {
        this.userEmailAdress = userEmailAdress;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public Integer getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }

    public Integer getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(int userHeight) {
        this.userHeight = userHeight;
    }

    /*public ArrayList<String> getUserAlergies() {
        return userAlergies;
    }

    public void setUserAlergies(ArrayList<String> userAlergies) {
        this.userAlergies = userAlergies;
    }*/

    public Integer getUserCaloriesIntakeDaily() {
        return userCaloriesIntakeDaily;
    }

    public void setUserCaloriesIntakeDaily(int userCaloriesIntakeDaily) {
        this.userCaloriesIntakeDaily = userCaloriesIntakeDaily;
    }

    public Integer getUserWeightGoal() {
        return userWeightGoal;
    }

    public void setUserWeightGoal(int userWeightGoal) {
        this.userWeightGoal = userWeightGoal;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getUserBirthDate() {
        return userBirthDate;
    }

    public void setUserBirthDate(Date userBirthDate) {
        this.userBirthDate = userBirthDate;
    }
}
