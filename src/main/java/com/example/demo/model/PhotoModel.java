package com.example.demo.model;

public class PhotoModel {
    public PhotoModel() {}

    public PhotoModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id, name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
