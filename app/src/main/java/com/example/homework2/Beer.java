package com.example.homework2;

import android.graphics.drawable.Drawable;

public class Beer {
    private String name;
    private String description;
    private String imageUrl;
    private Drawable fav;
    private Drawable unFav;
    private boolean clicked;

    public Beer(String name, String description, String imageUrl, Drawable fav, Drawable unFav) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.fav = fav;
        this.unFav = unFav;

        clicked = false;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Drawable getFav() {
        return fav;
    }

    public void setFav(Drawable fav) {
        this.fav = fav;
    }

    public Drawable getUnFav() {
        return unFav;
    }

    public void setUnFav(Drawable unFav) {
        this.unFav = unFav;
    }
}

