package com.guelph.engg1420finalprojectjavafx;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private ArrayList<User> userList;

    public UserController() {
        userList = new ArrayList<User>(); //To create an array of users
    }

    /*
    We want to add a new user to the lsit of users
     */
    public void addUser(String name, String email, String userType) {
        User u = new User(name, email, userType);
        userList.add(u); //Creating a list of users, that will take in a parameters of name, email, usertype
    }


    /*
    Using a for loop, we want to compare the strings.
    If the userList index equals to the userId
     */
    public void editUser(String userId, String name, String email, String userType) {
        for (int i = 0; i < userList.size(); i++) {
            // use .equals if you need to compare strings (== does not work on strings)
            if (userList.get(i).getUserId().equals(userId)) {
                userList.get(i).setName(name);
                userList.get(i).setEmail(email);
                userList.get(i).setUserType(userType);
            }
        }
    }

    public ArrayList<User> getUserList() {
        return userList;
    }
}


