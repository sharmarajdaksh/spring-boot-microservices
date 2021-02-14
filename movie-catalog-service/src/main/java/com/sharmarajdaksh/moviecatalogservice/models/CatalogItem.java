package com.sharmarajdaksh.moviecatalogservice.models;

public class CatalogItem {
    private final String name;
    private final String desc;
    private final int rating;

    public CatalogItem(String name, String desc, int rating) {
        this.name = name;
        this.desc = desc;
        this.rating = rating;
    }

    public int getRating() {
        return this.rating;
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return desc;
    }
}
