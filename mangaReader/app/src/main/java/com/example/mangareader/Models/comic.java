package com.example.mangareader.Models;

public class comic {

    private String Title;
    private String Category;
    private String Description;
    private String Thumbnail;
    private String Manga_id;
    private String Favourite;

//    public comic(String one_piece, String category_comic, String description_comic, int onePiece){
//    }

    public comic(String title, String category, String description, String thumbnail, String manga_id, String favourite) {
        Title = title;
        Category = category;
        Description = description;
        Thumbnail = thumbnail;
        Manga_id = manga_id;
        Favourite = favourite;
    }

    public String getTitle() {
        return Title;
    }

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getManga_id() {
        return Manga_id;
    }

    public void setManga_id(String manga_id) {
        Manga_id = manga_id;
    }

    public String getFavourite() {
        return Favourite;
    }

    public void setFavourite(String favourite) {
        Favourite = favourite;
    }
}
