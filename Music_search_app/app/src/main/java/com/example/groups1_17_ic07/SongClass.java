package com.example.groups1_17_ic07;

import android.icu.util.LocaleData;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class SongClass {

    String track;
    String artist;
    String album;
    String date;
    String url;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public SongClass() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public SongClass(String track, String artist, String album, String date,String url) {
        this.track = track;
        this.artist = artist;
        this.album = album;
        this.date = date;
        this.url=url;
    }


    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SongClass{" +
                "track='" + track + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", date=" + date +
                '}';
    }
}
