package com.example.final_main;

import android.location.Location;

public interface GPSCallback {
    public abstract void onGPSUpdate(Location location);
}
