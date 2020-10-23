package com.example.mint_anroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public ImageView buttonToAddPage;

    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDialog = new Dialog(this);

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
        TextView text_close;
        Button btnFollow = (Button) myDialog.findViewById(R.id.one_save_button);
        myDialog.setContentView(R.layout.command_one);
        text_close =(TextView) myDialog.findViewById(R.id.txtclose);
        text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void TwoShowPopup(View v) {
        TextView text_close;
        Button button_save = (Button) myDialog.findViewById(R.id.two_save_button);
        myDialog.setContentView(R.layout.command_two);
        text_close =(TextView) myDialog.findViewById(R.id.txtclose);
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
        Button button_save = (Button) myDialog.findViewById(R.id.two_save_button);
        myDialog.setContentView(R.layout.command_two);
        text_close =(TextView) myDialog.findViewById(R.id.txtclose);
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
        Button button_save = (Button) myDialog.findViewById(R.id.two_save_button);
        myDialog.setContentView(R.layout.command_two);
        text_close =(TextView) myDialog.findViewById(R.id.txtclose);
        text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}

