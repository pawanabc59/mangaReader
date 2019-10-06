package com.example.mangareader;

public class BookModel {

    private String Title;
    private String Category;
    private String Description;
    private String Thumbnail;
    private String Book_id;
    private String Favourite;
    private String Book_path;

    public BookModel(String title, String category, String description, String thumbnail, String book_id, String favourite, String book_path) {
        Title = title;
        Category = category;
        Description = description;
        Thumbnail = thumbnail;
        Book_id = book_id;
        Favourite = favourite;
        Book_path = book_path;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getBook_id() {
        return Book_id;
    }

    public void setBook_id(String book_id) {
        Book_id = book_id;
    }

    public String getFavourite() {
        return Favourite;
    }

    public void setFavourite(String favourite) {
        Favourite = favourite;
    }

    public String getBook_path() {
        return Book_path;
    }

    public void setBook_path(String book_path) {
        Book_path = book_path;
    }
}
