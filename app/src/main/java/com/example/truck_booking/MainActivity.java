package com.example.truck_booking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView sourceText, destinationText;
    Button checkFareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceText = findViewById(R.id.autoCompleteTextView);
        destinationText = findViewById(R.id.autoCompleteTextView2);
        checkFareButton = findViewById(R.id.button);

        // Array of capitals of India
        String[] indiaCapitals = {
                "New Delhi", "Bengaluru", "Chennai", "Kolkata", "Mumbai",
                "Hyderabad", "Pune", "Ahmedabad", "Lucknow", "Jaipur",
                "Chandigarh", "Thiruvananthapuram", "Guwahati", "Patna", "Ranchi"
        };

        // Create an ArrayAdapter using the capitals array
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, indiaCapitals);

        // Set the adapter for both source and destination AutoCompleteTextViews
        sourceText.setAdapter(adapter);
        destinationText.setAdapter(adapter);

        // Set threshold to start showing suggestions when the user types at least 1 character
        sourceText.setThreshold(1);
        destinationText.setThreshold(1);

        // Ensure there is no overlapping or improper data handling
        checkFareButton.setOnClickListener(v -> {
            String source = sourceText.getText().toString();
            String destination = destinationText.getText().toString();

            if (source.isEmpty() || destination.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter both source and destination!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ensure source and destination are not the same
            if (source.equals(destination)) {
                Toast.makeText(MainActivity.this, "Source and destination cannot be the same!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Proceed with booking activity
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("SOURCE", source);
            intent.putExtra("DESTINATION", destination);
            startActivity(intent);
        });
    }
}
