package com.brendan.randomname;

/* This class is for making objects from the data in the SQLite Database.
That list will be used in the parameter of the NameAdapter constructor,
and then will be displayed as a Recycler View. */
public class SavedNameModel {
    private final int id;
    private String name;

    public SavedNameModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

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