package com.example.mangareader;

public class comicModel {

    private String image;
    private String name, category, manga_id, favourite;

    public comicModel(String image, String name, String category, String manga_id, String favourite) {
        this.image = image;
        this.name = name;
        this.category = category;
        this.manga_id = manga_id;
        this.favourite = favourite;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getManga_id() {
        return manga_id;
    }

    public void setManga_id(String manga_id) {
        this.manga_id = manga_id;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }
}
