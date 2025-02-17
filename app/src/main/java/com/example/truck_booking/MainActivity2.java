package com.example.truck_booking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {

    Spinner vehicleTypeSpinner, goodsTypeSpinner;
    EditText dateEditText, weightEditText;
    Button bookNowButton;
    String source, destination;
    TextView fareTextView;
    double fare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        vehicleTypeSpinner = findViewById(R.id.spinner);
        goodsTypeSpinner = findViewById(R.id.spinner2);
        dateEditText = findViewById(R.id.editTextText2);
        weightEditText = findViewById(R.id.editTextText);
        bookNowButton = findViewById(R.id.button2);
        fareTextView = findViewById(R.id.textView9);

        // Get source and destination from the previous activity
        source = getIntent().getStringExtra("source");
        destination = getIntent().getStringExtra("destination");

        String[] vehicleTypes = {"Truck", "Mini Truck", "Pickup", "Trailer", "Van"};
        String[] goodsTypes = {"Furniture", "Electronics", "Clothing", "Food", "Construction Material"};

        ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, vehicleTypes);
        vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleTypeSpinner.setAdapter(vehicleAdapter);

        ArrayAdapter<String> goodsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, goodsTypes);
        goodsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goodsTypeSpinner.setAdapter(goodsAdapter);

        // Set date picker for dateEditText
        dateEditText.setOnClickListener(v -> showDatePicker());

        // Set listener for the "Book Now" button
        bookNowButton.setOnClickListener(v -> {
            if (dateEditText.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            } else if (weightEditText.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter the weight of the goods", Toast.LENGTH_SHORT).show();
            } else {
                calculateFare();  // Calculate the fare
                showReceiptDialog();  // Show the receipt dialog
                navigateToDetailsActivity();  // Navigate to DisplayDetailsActivity
            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int year1, int month1, int dayOfMonth) -> {
                    dateEditText.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
                },
                year, month, day);

        datePickerDialog.show();
    }

    private void calculateFare() {
        String vehicleType = vehicleTypeSpinner.getSelectedItem().toString();
        String goodsType = goodsTypeSpinner.getSelectedItem().toString();
        double weight = Double.parseDouble(weightEditText.getText().toString());

        int baseFare = 1000;
        double vehicleMultiplier = 1.0;
        double goodsMultiplier = 1.0;
        double weightMultiplier = weight;

        switch (vehicleType) {
            case "Truck":
                vehicleMultiplier = 2.0;
                break;
            case "Mini Truck":
                vehicleMultiplier = 1.5;
                break;
            case "Pickup":
                vehicleMultiplier = 1.2;
                break;
            case "Trailer":
                vehicleMultiplier = 3.0;
                break;
            case "Van":
                vehicleMultiplier = 1.0;
                break;
        }

        switch (goodsType) {
            case "Furniture":
            case "Electronics":
                goodsMultiplier = 1.5;
                break;
            case "Clothing":
            case "Food":
                goodsMultiplier = 1.2;
                break;
            case "Construction Material":
                goodsMultiplier = 2.0;
                break;
        }

        fare = baseFare * vehicleMultiplier * goodsMultiplier * weightMultiplier;
        fareTextView.setText("Estimated Fare: ₹" + String.format("%.2f", fare)); // Display fare in rupees
    }

    private void navigateToDetailsActivity() {
        // Create an intent to move to DisplayDetailsActivity
        Intent intent = new Intent(MainActivity2.this, DisplayDetails.class);

        // Pass the details to the next activity
        intent.putExtra("source", source);
        intent.putExtra("destination", destination);
        intent.putExtra("date", dateEditText.getText().toString());
        intent.putExtra("vehicleType", vehicleTypeSpinner.getSelectedItem().toString());
        intent.putExtra("goodsType", goodsTypeSpinner.getSelectedItem().toString());
        intent.putExtra("weight", weightEditText.getText().toString());
        intent.putExtra("fare", fare);

        // Start the next activity
        startActivity(intent);
    }

    // Show a receipt dialog with the booking details
    private void showReceiptDialog() {
        String date = dateEditText.getText().toString();
        double weight = Double.parseDouble(weightEditText.getText().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Booking Confirmation");
        builder.setMessage(
                "Source: " + source + "\n" +
                        "Destination: " + destination + "\n" +
                        "Date: " + date + "\n" +
                        "Vehicle Type: " + vehicleTypeSpinner.getSelectedItem().toString() + "\n" +
                        "Goods Type: " + goodsTypeSpinner.getSelectedItem().toString() + "\n" +
                        "Weight: " + weight + " kg\n" +
                        "Fare: ₹" + String.format("%.2f", fare) + "\n\n" +
                        "Thank you for booking with us!"
        );
        builder.setPositiveButton("OK", (dialog, which) -> Toast.makeText(this, "Your truck has been booked!", Toast.LENGTH_LONG).show());
        builder.show();
    }
}
