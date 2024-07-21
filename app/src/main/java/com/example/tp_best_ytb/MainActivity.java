package com.example.tp_best_ytb;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_best_ytb.db.YoutubeVideoDatabase;
import com.example.tp_best_ytb.model.YoutubeVideo;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private YoutubeVideoAdapter adapter;
    private List<YoutubeVideo> videoList;
    private boolean isShowingFavorites = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rvYtbVideo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadVideos();
    }

    private void loadVideos() {
        // Récupérer la liste des vidéos depuis la base de données
        videoList = YoutubeVideoDatabase.getdb(this).youtubeVideoDao().getAllVideos();
        recyclerView.setAdapter(new YoutubeVideoAdapter(videoList, new YoutubeVideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(YoutubeVideo item) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("video", item);
                startActivity(intent);
            }
        }));
    }
    private void loadFavoriteVideos() {
        // Récupérer la liste des vidéos favorites depuis la base de données
        videoList = YoutubeVideoDatabase.getdb(this).youtubeVideoDao().getFavoriteVideos();
        adapter = new YoutubeVideoAdapter(videoList, new YoutubeVideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(YoutubeVideo item) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("video", item);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem filterItem = menu.findItem(R.id.action_filter_favorites);
        MenuItem showAllItem = menu.findItem(R.id.action_show_all_videos);

        if (isShowingFavorites) {
            filterItem.setVisible(false);
            showAllItem.setVisible(true);
        } else {
            filterItem.setVisible(true);
            showAllItem.setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_video) {
            Intent intent = new Intent(this, AddYouTubeActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.action_filter_favorites) {
            isShowingFavorites = true;
            loadFavoriteVideos();
            invalidateOptionsMenu(); // Recrée le menu pour mettre à jour les titres
            return true;
        } else if (item.getItemId() == R.id.action_show_all_videos) {
            isShowingFavorites = false;
            loadVideos();
            invalidateOptionsMenu(); // Recrée le menu pour mettre à jour les titres
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (isShowingFavorites) {
            loadFavoriteVideos();
        } else {
            loadVideos();
        }
    }
}