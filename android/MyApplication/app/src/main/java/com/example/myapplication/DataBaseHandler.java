package com.example.myapplication;

import java.util.ArrayList;
import java.util.Date;

public class DataBaseHandler {
    private ArrayList<User>userArrayList;

    public DataBaseHandler(){
        userArrayList = new ArrayList<>();
        userArrayList.add(new User(1,"a","Alicja","Gratkowska","grat.ala@gmail.com",1,57,173,1800,56,"agrat",new Date()));
        userArrayList.add(new User(2,"123","Karolina","Bojarska","boj.kar@gmail.com",1,65,182,2400,60,"kboj",new Date()));

    }

    public boolean checkIfUserWithUserNameOrAdressExists(String userNameOrEmail){
        for(User user : userArrayList){
            if(user.getUser_name().equals(userNameOrEmail)||user.getEmail().equals(userNameOrEmail)){
                return true;
            }
        }
        return false;
    }

    public boolean checkIfPasswordIsCorrect(String userNameOfEmail, String userPassword){
        for(User user : userArrayList){
            if((user.getUser_name().equals(userNameOfEmail)&&user.getPassword().equals(userPassword))){
                return true;
            }
        }
        return false;
    }

    public User findUserByUserNameOrEmailAddress(String userNameOrEmail){
        for(User user : userArrayList){
            if(user.getUser_name().equals(userNameOrEmail)||user.getEmail().equals(userNameOrEmail)){
                return user;
            }
        }
        return null;
    }

    public User findUser(int ID){
        for(User user : userArrayList){
            if(user.getId()==ID){
                return user;
            }
        }
        return null;
    }

}
