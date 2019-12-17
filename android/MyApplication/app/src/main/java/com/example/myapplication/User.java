package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable {

    private Integer id;

    private String password;

    private String first_name;

    private String last_name;

    private String email;

    private int sex;

    private Integer weight;

    private Integer height;

    private ArrayList<String>allergies;

    private int calories_intake_daily;

    private int weight_goal;

    private String user_name;

    private Date birthDate;

    public User(){}

    public User(Integer userID, String password, String first_name, String last_name,
                String userEmailAdress, int sex, int weight, int height
            /*,ArrayList<String> allergies*/, int calories_intake_daily, int weight_goal,
                String user_name, Date birthDate) {
        this.id = userID;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = userEmailAdress;
        this.sex = sex;
        this.weight = weight;
        this.height = height;
        //this.allergies = allergies;
        this.calories_intake_daily = calories_intake_daily;
        this.weight_goal = weight_goal;
        this.user_name = user_name;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /*public ArrayList<String> getUserAllergies() {
        return allergies;
    }

    public void setUserAllergies(ArrayList<String> allergies) {
        this.allergies = allergies;
    }*/

    public Integer getCalories_intake_daily() {
        return calories_intake_daily;
    }

    public void setCalories_intake_daily(int calories_intake_daily) {
        this.calories_intake_daily = calories_intake_daily;
    }

    public Integer getWeight_goal() {
        return weight_goal;
    }

    public void setWeight_goal(int weight_goal) {
        this.weight_goal = weight_goal;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
