package com.example.tp_best_ytb.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tp_best_ytb.model.YoutubeVideo;

import java.util.List;

@Dao
public interface YoutubeVideoDao {
    @Insert
    void insert(YoutubeVideo video);

    @Update
    void update(YoutubeVideo video);

    @Delete
    void delete(YoutubeVideo video);

    @Query("SELECT * FROM youtube_video WHERE id = :id")
    YoutubeVideo getVideoById(long id);

    @Query("SELECT * FROM youtube_video")
    List<YoutubeVideo> getAllVideos();

    @Query("SELECT * FROM youtube_video WHERE is_favorite = 1")
    List<YoutubeVideo> getFavoriteVideos();
}
