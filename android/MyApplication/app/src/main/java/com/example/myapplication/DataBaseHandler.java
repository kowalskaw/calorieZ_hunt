package com.example.myapplication;

import java.util.ArrayList;
import java.util.Date;

public class DataBaseHandler {
    private ArrayList<User>userArrayList;

    public DataBaseHandler(){
        userArrayList = new ArrayList<>();
        userArrayList.add(new User(1,"ala123","Alicja","Gratkowska","grat.ala@gmail.com",1,57,173,1800,56,"agrat",new Date()));
        userArrayList.add(new User(2,"123","Karolina","Bojarska","boj.kar@gmail.com",1,65,182,2400,60,"kboj",new Date()));

    }

    public boolean checkIfUserWithUserNameOrAdressExists(String userNameOrEmail){
        for(User user : userArrayList){
            if(user.getUserName().equals(userNameOrEmail)||user.getUserEmailAdress().equals(userNameOrEmail)){
                return true;
            }
        }
        return false;
    }

    public boolean checkIfPasswordIsCorrect(String userNameOfEmail, String userPassword){
        for(User user : userArrayList){
            if((user.getUserName().equals(userNameOfEmail)&&user.getUserPassword().equals(userPassword))){
                return true;
            }
        }
        return false;
    }

    public User findUserByUserNameOrEmailAddress(String userNameOrEmail){
        for(User user : userArrayList){
            if(user.getUserName().equals(userNameOrEmail)||user.getUserEmailAdress().equals(userNameOrEmail)){
                return user;
            }
        }
        return null;
    }

    public User findUser(int ID){
        for(User user : userArrayList){
            if(user.getUserID()==ID){
                return user;
            }
        }
        return null;
    }

}
