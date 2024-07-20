package com.example.tp_best_ytb.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tp_best_ytb.dao.YoutubeVideoDao;
import com.example.tp_best_ytb.model.YoutubeVideo;

@Database(entities = {YoutubeVideo.class}, version = 1)
public abstract class YoutubeVideoDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "youtube_video";
    public static YoutubeVideoDatabase getdb(Context context) {
        return Room.databaseBuilder(context, YoutubeVideoDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
    }
    public abstract YoutubeVideoDao youtubeVideoDao();
}
