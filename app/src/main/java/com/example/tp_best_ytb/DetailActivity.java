    package com.example.tp_best_ytb;


    import android.content.ActivityNotFoundException;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.TextView;
    import androidx.appcompat.widget.Toolbar;

    import androidx.appcompat.app.AppCompatActivity;

    import com.example.tp_best_ytb.db.YoutubeVideoDatabase;
    import com.example.tp_best_ytb.model.YoutubeVideo;

    public class DetailActivity extends AppCompatActivity {
        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvUrl;
        private TextView tvCategory;
        private Button buttonView;
        private YoutubeVideo video;
        private ImageButton buttonFavorite;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            tvTitle = findViewById(R.id.tvTitle);
            tvDescription = findViewById(R.id.tvDescription);
            tvUrl = findViewById(R.id.tvUrl);
            tvCategory = findViewById(R.id.tvCategory);
            buttonView = findViewById(R.id.buttonView);
            buttonFavorite = findViewById(R.id.buttonFavorite);

            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("video")) {
                video = (YoutubeVideo) intent.getSerializableExtra("video");
                if (video != null) {
                    tvTitle.setText(video.getTitre());
                    tvDescription.setText(video.getDescription());
                    tvUrl.setText(video.getUrl());
                    tvCategory.setText(video.getCategorie());
                    updateFavoriteButton();

                    buttonView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(tvUrl.getText().toString()));
                            try {
                                DetailActivity.this.startActivity(webIntent);
                            } catch (ActivityNotFoundException ex) {
                            }
                        }
                    });
                    buttonFavorite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (video.getFavori() == 1) {
                                video.setFavori(0);
                            } else {
                                video.setFavori(1);
                            }
                            YoutubeVideoDatabase.getdb(DetailActivity.this).youtubeVideoDao().update(video);
                            updateFavoriteButton();
                        }
                    });
                }
            }
        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.detail_menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_delete) {
                if (video != null) {
                    // Supprimer la vidéo de la base de données
                    YoutubeVideoDatabase.getdb(this).youtubeVideoDao().delete(video);
                    // Retour à MainActivity
                    finish(); // Appelle `onBackPressed()`, donc revient à la dernière activité
                }
                return true;
            } else if (id == R.id.action_update) {
                if (video != null) {
                    // Lancer l'activité de mise à jour
                    Intent intent = new Intent(this, UpdateVideoActivity.class);
                    intent.putExtra("video", video);
                    startActivity(intent);
                }
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public boolean onSupportNavigateUp() {
            finish(); // Retour à l'activité précédente
            return true;
        }
        private void updateFavoriteButton() {
            if (video.getFavori() == 1) {
                buttonFavorite.setImageResource(R.drawable.ic_fav);
            } else {
                buttonFavorite.setImageResource(R.drawable.ic_not_fav);
            }
        }
    }
