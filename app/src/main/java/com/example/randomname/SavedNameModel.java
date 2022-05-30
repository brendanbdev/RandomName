package com.example.randomname;

public class SavedNameModel {

    private int id;
    private String name;

    //constructors

    public SavedNameModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public SavedNameModel() {
    }

    //toString is necessary for printing the contents of a class object

    @Override
    public String toString() { return name; }


    //getters and setters

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
}
