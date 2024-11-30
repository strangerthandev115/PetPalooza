package com.example.petpalooza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PetSelectActivity extends AppCompatActivity {

    private ImageView petGif;
    private boolean isDogSelected = true; // Tracks if the dog is selected
    private Pet selectedPet; // Store the selected pet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_select);

        // Initialize views
        petGif = findViewById(R.id.petgif);
        Button dogButton = findViewById(R.id.dogbutton);
        Button catButton = findViewById(R.id.catbutton);
        Button option1 = findViewById(R.id.option1);
        Button option2 = findViewById(R.id.option2);
        Button checkButton = findViewById(R.id.checkbutton);

        // Set default pet type
        selectedPet = new Pet("BrownDog"); // Default pet type is "BrownDog"
        Glide.with(this).asGif().load(R.drawable.brown_dog_idle).into(petGif);

        // Dog button listener
        dogButton.setOnClickListener(view -> {
            isDogSelected = true;
            selectedPet = new Pet("BrownDog"); // Set pet type as "BrownDog"
            Glide.with(this).asGif().load(R.drawable.brown_dog_idle).into(petGif);
        });

        // Cat button listener
        catButton.setOnClickListener(view -> {
            isDogSelected = false;
            selectedPet = new Pet("TuxedoCat"); // Set pet type as "TuxedoCat"
            Glide.with(this).asGif().load(R.drawable.tuxedocat_idle).into(petGif);
        });

        // Option 1 button listener (Dog)
        option1.setOnClickListener(view -> {
            if (isDogSelected) {
                selectedPet = new Pet("BrownDog");
                Glide.with(this).asGif().load(R.drawable.brown_dog_idle).into(petGif);
            } else {
                selectedPet = new Pet("TuxedoCat");
                Glide.with(this).asGif().load(R.drawable.tuxedocat_idle).into(petGif);
            }
        });

        // Option 2 button listener (Dog)
        option2.setOnClickListener(view -> {
            if (isDogSelected) {
                selectedPet = new Pet("TanDog");
                Glide.with(this).asGif().load(R.drawable.tandog_idle).into(petGif);
            } else {
                selectedPet = new Pet("FoxyCat");
                Glide.with(this).asGif().load(R.drawable.foxycat_idle).into(petGif);
            }
        });

        // Check button listener
        checkButton.setOnClickListener(view -> {
            // Pass selected pet type to NameSelectActivity
            Intent intent = new Intent(PetSelectActivity.this, NameSelectActivity.class);
            intent.putExtra("petType", selectedPet.getType());  // Pass the selected pet type (e.g., "BrownDog")
            startActivity(intent);
        });
    }
}
