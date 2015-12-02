package com.fbartnitzek.gradle.displayjokeandroidlibrary;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.frank.displayjokeandroidlibrary.R;

public class DisplayJokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);
    }
}
