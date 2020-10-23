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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public ImageView buttonToAddPage;
    public TextToSpeech mTTS;
    private Button mButtonSpeak;

    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDialog = new Dialog(this);

//        go to Bluetooth page

        buttonToAddPage = findViewById(R.id.add0);
        buttonToAddPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, add_page.class);
                MainActivity.this.startActivity(i);
            }
        });
    }

    public void OneShowPopup(View v) {

    //    close

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
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    //        speak

//        mButtonSpeak = findViewById(R.id.command_one_voice);
//        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if (status == TextToSpeech.SUCCESS) {
//                    int result = mTTS.setLanguage(Locale.CANADA);
//                    if (result == TextToSpeech.LANG_MISSING_DATA
//                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                        Log.e("TTS", "Language not supported");
//                    } else {
//                        mButtonSpeak.setEnabled(true);
//                    }
//                } else {
//                    Log.e("TTS", "Initialization failed");
//                }
//            }
//        });
//
//        mEditText = findViewById(R.id.edit_text);
//        val inputText = outlinedTextField.editText?.text.toString()
//        text = command_one_voice.editText?.text.toString()
//        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mTTS.speak(, TextToSpeech.QUEUE_FLUSH, null);
//            }
//        });

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
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

}

