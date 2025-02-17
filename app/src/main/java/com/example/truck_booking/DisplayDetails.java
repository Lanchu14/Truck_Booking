package com.example.truck_booking;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayDetails extends AppCompatActivity {

    TextView detailsTextView;
    DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details);

        detailsTextView = findViewById(R.id.detailsTextView);
        dbHelper = new DatabaseHelper(this);

        // Retrieve source and destination from intent
        String source = getIntent().getStringExtra("source");
        String destination = getIntent().getStringExtra("destination");

        displayBookingsForLocation(source, destination);
    }

    private void displayBookingsForLocation(String source, String destination) {
        Cursor cursor = dbHelper.getBookingsByLocation(source, destination);

        if (cursor != null && cursor.moveToFirst()) {
            StringBuilder details = new StringBuilder();
            do {
                String date = cursor.getString(cursor.getColumnIndexOrThrow("booking_date"));
                String vehicleType = cursor.getString(cursor.getColumnIndexOrThrow("vehicle_type"));
                String goodsType = cursor.getString(cursor.getColumnIndexOrThrow("goods_type"));
                int weight = cursor.getInt(cursor.getColumnIndexOrThrow("weight"));
                double fare = cursor.getDouble(cursor.getColumnIndexOrThrow("fare"));

                details.append("Date: ").append(date).append("\n")
                        .append("Vehicle Type: ").append(vehicleType).append("\n")
                        .append("Goods Type: ").append(goodsType).append("\n")
                        .append("Weight: ").append(weight).append(" kg\n")
                        .append("Fare: ₹").append(String.format("%.2f", fare)).append("\n\n");
            } while (cursor.moveToNext());

            detailsTextView.setText(details.toString());
        } else {
            detailsTextView.setText("No bookings found for the specified location.");
        }

        if (cursor != null) {
            cursor.close();
        }
    }
}
