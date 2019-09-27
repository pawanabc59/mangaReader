package com.example.mangareader;

public class chapterModel {

    private String chapter_name;
    private String chapter_path;

    public chapterModel(String chapter_name, String chapter_path) {
        this.chapter_name = chapter_name;
        this.chapter_path = chapter_path;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public String getChapter_path() {
        return chapter_path;
    }

    public void setChapter_path(String chapter_path) {
        this.chapter_path = chapter_path;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }
}
