package com.example.preferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private EditText NotePad;
    private TextView tvDate;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  if(savedInstanceState!=null){
            NotePad.setText(savedInstanceState.getString("text"));
        }*/
        NotePad=findViewById(R.id.notePad);
        tvDate=findViewById(R.id.date);
        calendar=Calendar.getInstance();

        NotePad.setScroller(new Scroller(getApplicationContext()));
        NotePad.setVerticalScrollBarEnabled(true);
        NotePad.setScrollBarSize(20);

        loadFile();

    }

    private void loadFile() {
        int Status;
        int DateStatus;
        StringBuilder stringBuilder=new StringBuilder("");
        StringBuilder stringBuilder1=new StringBuilder("");
        try {
            FileInputStream fileInputStream=openFileInput("Notepad");
            FileInputStream fileInputStream1=openFileInput("DateAndTime");
            while ((Status=fileInputStream.read())!=-1 ){
                NotePad.setText(stringBuilder.append((char) Status));
            }
            while ((DateStatus=fileInputStream1.read())!=-1){
                tvDate.setText("Last Update: "+stringBuilder1.append((char)DateStatus));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*@Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("text",NotePad.getText().toString());
        super.onSaveInstanceState(outState);
    }*/

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
            saveData();
        super.onBackPressed();
    }

    public void saveData(){
        String Notes=NotePad.getText().toString();
        String DateTime=tvDate.getText().toString();
        try {
            FileOutputStream outputStream=openFileOutput("Notepad", Context.MODE_PRIVATE);
            FileOutputStream outputStream1=openFileOutput("DateAndTime",Context.MODE_PRIVATE);
            try {
                outputStream1.write(java.text.DateFormat.getDateTimeInstance().format(new Date()).getBytes());
                outputStream.write(Notes.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }
}