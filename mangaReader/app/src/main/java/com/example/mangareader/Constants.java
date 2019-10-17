package com.example.mangareader;

public class Constants {

    public static final String main_path = "http://192.168.0.101:5656";
    public static final String url_login = main_path+"/user/login";
    public static final String url_get_chapters = main_path+"/manga/getChapters";
    public static final String url_add_favourites = main_path+"/user/favourite/add";
    public static final String url_remove_favourites = main_path+"/user/favourite/remove";
    public static final String url_get_manga = main_path+"/user/home/feed";
    public static final String url_get_favourite_manga = main_path+"/user/favourites";
    public static final String url_upload = main_path+"/user/setProfilePic";
    public static final String url_recent_mangas = main_path+"/user/recent";
    public static final String server_url = main_path+"/user/register";
    public static final String url_get_book = main_path+"/books/get_books";
    public static final String url_add_favourite_book = main_path+"/user/favourite_book/add";
    public static final String url_remove_favourite_book = main_path+"/user/favourite_book/remove";
    public static final String url_get_favourite_book = main_path+"/user/favourites_book";

}
