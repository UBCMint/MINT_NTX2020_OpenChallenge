package com.example.mint_anroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech text_to_speech;
    private TextInputLayout one_voice;
    private TextInputLayout two_voice;
    private TextInputLayout three_voice;
    private TextInputLayout four_voice;


    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        one_voice = (TextInputLayout) findViewById(R.id.command_one_voice);
        two_voice = (TextInputLayout) findViewById(R.id.command_two_voice);
        three_voice = (TextInputLayout) findViewById(R.id.command_three_voice);
        four_voice = (TextInputLayout) findViewById(R.id.command_four_voice);

        myDialog = new Dialog(this);

        text_to_speech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    text_to_speech.setLanguage(Locale.CANADA);
                }
            }
        });
    }

    public void SpeakOne(View v) {
        String to_speak = one_voice.getEditText().getText().toString();
        text_to_speech.speak(to_speak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void SpeakTwo(View v) {
        String to_speak = two_voice.getEditText().getText().toString();
        text_to_speech.speak(to_speak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void SpeakThree(View v) {
        String to_speak = three_voice.getEditText().getText().toString();
        text_to_speech.speak(to_speak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void SpeakFour(View v) {
        String to_speak = four_voice.getEditText().getText().toString();
        text_to_speech.speak(to_speak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void goToBluetoothPage(View v) {
        Intent myIntent = new Intent(MainActivity.this, add_page.class);
//        myIntent.putExtra("key", value); //Optional parameters
        MainActivity.this.startActivity(myIntent);
    }

    public void OneShowPopup(View v) {
        TextView text_close;
        Button btnFollow = (Button) myDialog.findViewById(R.id.one_save_button);
        myDialog.setContentView(R.layout.command_one);
        text_close =(TextView) myDialog.findViewById(R.id.text_close);
        text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        one_voice = (TextInputLayout) myDialog.findViewById(R.id.command_one_voice);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void TwoShowPopup(View v) {
        TextView text_close;
        Button button_save = (Button) myDialog.findViewById(R.id.two_save_button);
        myDialog.setContentView(R.layout.command_two);
        text_close =(TextView) myDialog.findViewById(R.id.text_close);
        text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        two_voice = (TextInputLayout) myDialog.findViewById(R.id.command_two_voice);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void ThreeShowPopup(View v) {
        TextView text_close;
        Button button_save = (Button) myDialog.findViewById(R.id.three_save_button);
        myDialog.setContentView(R.layout.command_three);
        text_close =(TextView) myDialog.findViewById(R.id.text_close);
        text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        three_voice = (TextInputLayout) myDialog.findViewById(R.id.command_three_voice);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void FourShowPopup(View v) {
        TextView text_close;
        Button button_save = (Button) myDialog.findViewById(R.id.four_save_button);
        myDialog.setContentView(R.layout.command_four);
        text_close =(TextView) myDialog.findViewById(R.id.text_close);
        text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        four_voice = (TextInputLayout) myDialog.findViewById(R.id.command_four_voice);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    @Override
    protected void onDestroy() {
        if (text_to_speech != null) {
            text_to_speech.stop();
            text_to_speech.shutdown();
        }
        super.onDestroy();
    }

}

