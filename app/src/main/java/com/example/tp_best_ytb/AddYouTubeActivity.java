package com.example.tp_best_ytb;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tp_best_ytb.db.YoutubeVideoDatabase;
import com.example.tp_best_ytb.model.YoutubeVideo;

public class AddYouTubeActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextUrl;
    private Spinner spinnerCategory;
    private Button buttonAdd;
    private Button buttonCancel;
    private YoutubeVideoDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_you_tube);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextUrl = findViewById(R.id.editTextUrl);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonCancel = findViewById(R.id.buttonCancel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialiser le Spinner avec les catégories
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        db = YoutubeVideoDatabase.getdb(this);

        // Gérer le clic sur le bouton Ajouter
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logique pour ajouter la vidéo
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();
                String url = editTextUrl.getText().toString();
                String category = spinnerCategory.getSelectedItem().toString();

                if (title.isEmpty() || description.isEmpty() || url.isEmpty() || category.isEmpty()) {
                    Toast.makeText(AddYouTubeActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    YoutubeVideo video = new YoutubeVideo();
                    video.setTitre(title);
                    video.setDescription(description);
                    video.setUrl(url);
                    video.setCategorie(category);
                    video.setFavori(0); // Initialement, pas en favori

                    // Insérer la vidéo dans la base de données
                    db.youtubeVideoDao().insert(video);

                    // Retourner à MainActivity
                    Intent intent = new Intent(AddYouTubeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // Gérer le clic sur le bouton Annuler
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Fermer l'activité
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Retour à l'activité précédente
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }
}