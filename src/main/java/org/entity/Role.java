package org.entity;

import java.util.ArrayList;

public class Role {
    private int id;
    private String name;
    private ArrayList<Account> users = new ArrayList<>();
    private ArrayList<Feature> features = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Account> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Account> users) {
        this.users = users;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }

}
