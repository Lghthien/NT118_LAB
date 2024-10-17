package com.example.bt5;

public class Dish {

    private String name;
    private int thumbnailResource;
    private boolean isPromotion;

    public Dish(String name, int thumbnailResource, boolean isPromotion) {
        this.name = name;
        this.thumbnailResource = thumbnailResource;
        this.isPromotion = isPromotion;
    }

    public String getName() {
        return name;
    }

    public int getThumbnailResource() {
        return thumbnailResource;
    }

    public boolean isPromotion() {
        return isPromotion;
    }
}
