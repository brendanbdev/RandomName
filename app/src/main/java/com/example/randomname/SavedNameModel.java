package com.example.randomname;

public class SavedNameModel {

    private int id;
    private String name;

    //constructors
    public SavedNameModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //getters and setters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}