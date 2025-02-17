package com.example.truck_booking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TruckBooking.db";
    private static final int DATABASE_VERSION = 1;

    // Table and Columns
    private static final String TABLE_NAME = "bookings";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SOURCE = "source";
    private static final String COLUMN_DESTINATION = "destination";
    private static final String COLUMN_VEHICLE = "vehicle_type";
    private static final String COLUMN_GOODS = "goods_type";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_PAYMENT = "payment_method";
    private static final String COLUMN_DATE = "booking_date";
    private static final String COLUMN_FARE = "fare";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SOURCE + " TEXT, "
                + COLUMN_DESTINATION + " TEXT, "
                + COLUMN_VEHICLE + " TEXT, "
                + COLUMN_GOODS + " TEXT, "
                + COLUMN_WEIGHT + " INTEGER, "
                + COLUMN_PAYMENT + " TEXT, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_FARE + " REAL)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertBooking(String source, String destination, String vehicleType, String goodsType, int weight, String paymentMethod, String date, double fare) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SOURCE, source);
        values.put(COLUMN_DESTINATION, destination);
        values.put(COLUMN_VEHICLE, vehicleType);
        values.put(COLUMN_GOODS, goodsType);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_PAYMENT, paymentMethod);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_FARE, fare);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public Cursor getBookingsByLocation(String source, String destination) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_SOURCE + " = ?" +
                " AND " + COLUMN_DESTINATION + " = ?", new String[]{source, destination});
    }
}
