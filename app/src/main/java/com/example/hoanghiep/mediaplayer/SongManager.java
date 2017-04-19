package com.example.hoanghiep.mediaplayer;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by Hoang Hiep on 3/3/2017.
 */

public class SongManager {
    public SongManager(){

    }
    public ArrayList<Song> getAllSong(Context context){
        Cursor cursor=context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.DATA,
                },
                null,
                null,
                MediaStore.Audio.Media.TITLE+" ASC"

        );
        if(cursor==null){
            return new ArrayList<>();
        }
        if (cursor.getCount()==0){
            cursor.close();
            return new ArrayList<>();
        }
        ArrayList<Song> songs=new ArrayList<>();
        cursor.moveToFirst();
        int indexTitle=cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int indexAuthor=cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int indexDuration=cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int indexData=cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        long id=cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        while (!cursor.isAfterLast()){
            String title=cursor.getString(indexTitle);
            String author=cursor.getString(indexAuthor);
            long duration=cursor.getLong(indexDuration);
            Uri data=Uri.parse(cursor.getString(indexData));
            Song song=new Song(title,author,duration,data,id);
            songs.add(song);
            cursor.moveToNext();
        }
        cursor.close();
        return songs;
    }

}
