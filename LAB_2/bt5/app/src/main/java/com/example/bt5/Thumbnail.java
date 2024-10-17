package com.example.bt5;

public enum Thumbnail {
    ThumbailL1("Thumbnail 1", R.drawable.image1),
    ThumbailL2("Thumbnail 2", R.drawable.image2),
    ThumbailL3("Thumbnail 3", R.drawable.image3),
    ThumbailL4("Thumbnail 4", R.drawable.image4);

    private final String name;
    private final int imageResource;

    Thumbnail(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }
}
