package com.example.petpalooza;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class GameOverActivity extends AppCompatActivity {

    private Pet pet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover_screen);  // Set your game over layout

        pet = loadPetInfo();
        String petName = pet.getName();

        // Find the TextView and update its text dynamically
        TextView runawayText = findViewById(R.id.ranawaytext);
        String updatedText = "Uh oh! " + petName + " ran away since they weren't cared for.";
        runawayText.setText(updatedText);

        // Set up the adopt button
        Button adoptButton = findViewById(R.id.adoptbutton);
        adoptButton.setOnClickListener(v -> {
            clearPetData();
            // Start a new adoption process or go back to main menu
            Intent mainMenuIntent = new Intent(GameOverActivity.this, MainActivity.class);
            startActivity(mainMenuIntent);
            finish();
        });
    }

    private Pet loadPetInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("PetInfo", MODE_PRIVATE);
        String petData = sharedPreferences.getString("PET_DATA", null);

        if (petData == null) {
            return null;  // No saved pet data
        }

        try {
            // Decode the Base64 string back to byte array
            byte[] petBytes = Base64.decode(petData, Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(petBytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Pet pet = (Pet) objectInputStream.readObject();
            objectInputStream.close();
            return pet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Failed to load pet data
        }
    }
    private void clearPetData() {
        SharedPreferences sharedPreferences = getSharedPreferences("PetInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("PET_DATA");  // Remove the pet data from SharedPreferences
        editor.apply();
    }
}
