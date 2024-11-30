package com.example.petpalooza;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class GameplayActivity extends AppCompatActivity {

    private Pet pet;
    private Handler handler;
    private Runnable progressUpdater;
    private final int UPDATE_INTERVAL = 60000; // Update every minute

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameplay_screen);  // This will load your gameplay_screen.xml

        // Retrieve pet data from SharedPreferences if available, otherwise use the Intent values
        pet = loadPetInfo();
        if (pet == null) {
            String petType = getIntent().getStringExtra("PET_TYPE");
            String petName = getIntent().getStringExtra("PET_NAME");

            pet = new Pet(petType, petName, 100, 100, 100);  // Default values for cleanliness and energy
            savePetInfo(pet);
        }

        adjustProgressBars();
        setupButtons();
        updateProgressBars();

        // Get references to the views
        ImageView petGifImageView = findViewById(R.id.petgif);
        TextView petNameTextView = findViewById(R.id.petname);

        // Set the pet name in the TextView
        petNameTextView.setText(pet.getName());

        // Set the correct GIF using Glide for animation
        int petGifResId = getPetGifResource(pet.getType());
        Glide.with(this).load(petGifResId).into(petGifImageView);

        // Start continuous depletion
        startProgressDepletion();

        savePetInfo(pet); // Save updated pet object
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopProgressDepletion(); // Stop updates when the activity is destroyed
    }

    private void startProgressDepletion() {
        handler = new Handler();
        progressUpdater = new Runnable() {
            @Override
            public void run() {
                depleteStats();
                updateProgressBars();
                savePetInfo(pet);

                // Schedule next update
                handler.postDelayed(this, UPDATE_INTERVAL);
            }
        };
        handler.post(progressUpdater); // Start the loop
    }

    private void stopProgressDepletion() {
        if (handler != null && progressUpdater != null) {
            handler.removeCallbacks(progressUpdater);
        }
    }

    private void depleteStats() {
        // Decrease stats gradually
        pet.setHunger(Math.max(0, pet.getHunger() - 1));       // Decrease hunger by 1
        pet.setCleanliness(Math.max(0, pet.getCleanliness() - 1)); // Decrease cleanliness by 1
        pet.setEnergy(Math.max(0, pet.getEnergy() - 1));       // Decrease energy by 1
        checkGameOver();
    }
    private void adjustProgressBars() {
        SharedPreferences sharedPreferences = getSharedPreferences("PetInfo", MODE_PRIVATE);
        long lastActiveTime = sharedPreferences.getLong("LAST_ACTIVE_TIME", System.currentTimeMillis());
        long currentTime = System.currentTimeMillis();
        long elapsedMinutes = (currentTime - lastActiveTime) / (1000 * 60);

        int decreaseValue = (int) (elapsedMinutes / 5) * 5;  // Decrease by 5 every 5 minutes

        pet.setHunger(Math.max(0, pet.getHunger() - decreaseValue));
        pet.setCleanliness(Math.max(0, pet.getCleanliness() - decreaseValue));
        pet.setEnergy(Math.max(0, pet.getEnergy() - decreaseValue));

        // Update the progress bars
        checkGameOver();
        updateProgressBars();
        savePetInfo(pet);
    }

    private void updateProgressBars() {
        ProgressBar hungerBar = findViewById(R.id.feedbar);
        ProgressBar cleanlinessBar = findViewById(R.id.cleanbar);
        ProgressBar energyBar = findViewById(R.id.sleepbar);

        hungerBar.setProgress(pet.getHunger());
        cleanlinessBar.setProgress(pet.getCleanliness());
        energyBar.setProgress(pet.getEnergy());
    }

    private void setupButtons() {
        findViewById(R.id.feedbutton).setOnClickListener(v -> {
            pet.setHunger(Math.min(100, pet.getHunger() + 5));  // Increase hunger, cap at 100
            updateProgressBars();
            updatePetGif("feed");
            savePetInfo(pet);
        });

        findViewById(R.id.cleanbutton).setOnClickListener(v -> {
            pet.setCleanliness(Math.min(100, pet.getCleanliness() + 5));  // Increase cleanliness, cap at 100
            updateProgressBars();
            updatePetGif("clean");
            savePetInfo(pet);
        });

        findViewById(R.id.sleepbutton).setOnClickListener(v -> {
            pet.setEnergy(Math.min(100, pet.getEnergy() + 5));  // Increase energy, cap at 100
            updateProgressBars();
            updatePetGif("sleep");
            savePetInfo(pet);
        });
    }

    private void updatePetGif(String action) {
        ImageView petGifImageView = findViewById(R.id.petgif);
        int gifResId = getActionGifResource(pet.getType(), action);
        Glide.with(this).load(gifResId).into(petGifImageView);

        // Revert to idle GIF after 2 seconds
        petGifImageView.postDelayed(() -> {
            int idleGifResId = getActionGifResource(pet.getType(), "idle");
            Glide.with(this).load(idleGifResId).into(petGifImageView);
        }, 2000);  // 2-second delay
    }
    private int getActionGifResource(String petType, String action) {
        // Define the GIF resources for each action and pet type
        switch (petType) {
            case "BrownDog":
                switch (action) {
                    case "feed": return R.drawable.brown_dog_eat;
                    case "sleep": return R.drawable.brown_dog_sleeppt1;
                    default: return R.drawable.brown_dog_idle;
                }
            case "TuxedoCat":
                switch (action) {
                    case "feed": return R.drawable.tuxedocat_eat;
                    case "sleep": return R.drawable.tuxedocat_sleeppt1;
                    default: return R.drawable.tuxedocat_idle;
                }
            case "TanDog":
                switch (action) {
                    case "feed": return R.drawable.tandog_eat;
                    case "sleep": return R.drawable.tandog_sleeppt1;
                    default: return R.drawable.tandog_idle;
                }
            case "FoxyCat":
                switch (action) {
                    case "feed": return R.drawable.foxycat_eat;
                    case "sleep": return R.drawable.foxycat_sleeppt1;
                    default: return R.drawable.foxycat_idle;
                }
            default:
                return 0;  // Fallback resource
        }
    }

    private int getPetGifResource(String petType) {
        switch (petType) {
            case "BrownDog":
                return R.drawable.brown_dog_idle;
            case "TuxedoCat":
                return R.drawable.tuxedocat_idle;
            case "TanDog":
                return R.drawable.tandog_idle;
            case "FoxyCat":
                return R.drawable.foxycat_idle;
            default:
                return 0;
        }
    }

    // Save the Pet object to SharedPreferences
    private void savePetInfo(Pet pet) {
        try {
            // Serialize the Pet object
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(pet);
            byte[] petBytes = byteArrayOutputStream.toByteArray();
            objectOutputStream.close();

            // Save byte array to SharedPreferences as a string (Base64 encoded)
            SharedPreferences sharedPreferences = getSharedPreferences("PetInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String encodedPetData = Base64.encodeToString(petBytes, Base64.DEFAULT);  // Encode byte array as Base64
            editor.putString("PET_DATA", encodedPetData);  // Store Base64 string
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Load the Pet object from SharedPreferences
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

    private void checkGameOver() {
        // Check if all the pet values are 0
        if (pet.getHunger() == 0 && pet.getCleanliness() == 0 && pet.getEnergy() == 0) {
            // Navigate to game over screen and delete the pet data from SharedPreferences
            goToGameOverScreen();
        }
    }

    private void goToGameOverScreen() {
        Intent gameOverIntent = new Intent(GameplayActivity.this, GameOverActivity.class);
        startActivity(gameOverIntent);
        finish();  // Close the current activity
    }


}
