package com.example.ubc_mint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton button;
    Button btnSpeak;
    EditText editText;

    android.speech.tts.TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTextToSpeech();
            }
        });


        // Initialize TextToSpeech
    }

    public void openTextToSpeech() {
        Intent intent = new Intent(this, TextToSpeech.class);
        startActivity(intent);
    }


}
