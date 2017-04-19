package com.example.hoanghiep.mediaplayer;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Hoang Hiep on 3/15/2017.
 */

public class Song implements Serializable {
    private String title;
    private String author;
    private long duration;
    private Uri data;
    private long id;

    public long getId() {
        return id;
    }

    public Song(String title, String author, long duration, Uri data, long id) {
        this.title = title;
        this.author = author;
        this.duration = duration;
        this.data = data;
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDuration() {
        long min = (duration / 1000) / 60;
        long sec = ((duration / 1000) - min) / 10;
        return min + ":" + sec;
    }

    public Uri getData() {
        return data;
    }

}
