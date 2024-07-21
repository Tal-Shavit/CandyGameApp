package com.example.hw1.Models;

import java.util.ArrayList;

public class DataBase {
    private ArrayList<UserItems> userItems = new ArrayList<>();

    public DataBase() {

    }

    public ArrayList<UserItems> getUserItems() {
        userItems.sort(((userItems1, userItems2) -> userItems2.getScore() - userItems1.getScore()));
        if (userItems.size() == 11)
            userItems.remove(10);
        return userItems;
    }

    public DataBase setUserItems(ArrayList<UserItems> userItems) {
        this.userItems = userItems;
        return this;
    }
}
