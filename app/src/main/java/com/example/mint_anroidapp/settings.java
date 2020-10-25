package com.example.mint_anroidapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class settings extends AppCompatActivity {

    private ImageView goback_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        goback_button = (ImageView) findViewById(R.id.go_back_from_settings);
        goback_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoBack();
            }
        });
    }

    public void GoBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}