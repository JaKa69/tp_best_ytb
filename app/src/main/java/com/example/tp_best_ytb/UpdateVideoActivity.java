package com.example.tp_best_ytb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tp_best_ytb.db.YoutubeVideoDatabase;
import com.example.tp_best_ytb.model.YoutubeVideo;

public class UpdateVideoActivity extends AppCompatActivity {
    private EditText etTitle;
    private EditText etDescription;
    private EditText etUrl;
    private Spinner spinnerCategory;
    private Button btnSave;
    private YoutubeVideo video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_video);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etUrl = findViewById(R.id.etUrl);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSave = findViewById(R.id.btnSave);

        // Initialiser le Spinner avec les catégories
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("video")) {
            video = (YoutubeVideo) intent.getSerializableExtra("video");
            if (video != null) {
                etTitle.setText(video.getTitre());
                etDescription.setText(video.getDescription());
                etUrl.setText(video.getUrl());
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video != null) {
                    video.setTitre(etTitle.getText().toString());
                    video.setDescription(etDescription.getText().toString());
                    video.setUrl(etUrl.getText().toString());
                    video.setCategorie(spinnerCategory.getSelectedItem().toString());

                    // Mettre à jour la vidéo dans la base de données
                    YoutubeVideoDatabase.getdb(UpdateVideoActivity.this).youtubeVideoDao().update(video);

                    // Retour à MainActivity
                    Intent mainIntent = new Intent(UpdateVideoActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish(); // Optionnel: pour fermer l'activité actuelle
                }
            }
        });
    }
}
