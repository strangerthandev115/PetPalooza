package com.example.petpalooza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NameSelectActivity extends AppCompatActivity {

    private EditText petNameEditText;
    private Button checkButton;
    private Pet pet; // Store the Pet object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_select);

        // Initialize views
        petNameEditText = findViewById(R.id.nameedit);
        checkButton = findViewById(R.id.checkbutton);
        String petType = getIntent().getStringExtra("petType");

        // Initialize the pet object based on the petType
        pet = new Pet(petType);

        // Check button listener
        checkButton.setOnClickListener(view -> {
            String petName = petNameEditText.getText().toString().trim();

            // Ensure the name is not empty
            if (petName.isEmpty()) {
                Toast.makeText(NameSelectActivity.this, "Please enter a name!", Toast.LENGTH_SHORT).show();
            } else {
                pet.setName(petName); // Set the name entered by the user

                // Proceed to the next activity
                Intent intent = new Intent(NameSelectActivity.this, GameplayActivity.class);

                // Pass pet type and name
                intent.putExtra("PET_TYPE", pet.getType());
                intent.putExtra("PET_NAME", pet.getName());

                startActivity(intent);
            }
        });
    }
}
