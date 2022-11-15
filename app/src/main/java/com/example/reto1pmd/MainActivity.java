package com.example.reto1pmd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final int CHOOSE_ACTIVITY = 1;
    public static final int QUESTION_ACTIVITY = 2;
    public static final int RESULT_ACTIVITY = 3;

    private MediaPlayer mp = null;
    private TextView nombre = null;
    private TextView apellido = null;
    private EditText setNombre = null;
    private EditText setApellido = null;
    private Button start = null;
    private DBManager dbManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();

        mp = MediaPlayer.create(this, R.raw.show_result);
        mp.setVolume(50, 50);

        nombre = (TextView) findViewById(R.id.nameView);
        apellido = (TextView) findViewById(R.id.apellidoView);
        setNombre = (EditText) findViewById(R.id.editNombre);
        setApellido = (EditText) findViewById(R.id.editApellido);
        start = (Button) findViewById(R.id.startButton);

        start = findViewById(R.id.startButton);
        start.setOnClickListener(
                (View v) -> {
                    mp.start();
                    String nombre = setNombre.getText().toString();
                    String apellido = setApellido.getText().toString();
                    if (nombre.isEmpty() || apellido.isEmpty()) {
                        Toast toast = Toast.makeText(this,R.string.error_text, Toast.LENGTH_SHORT);
                        toast.show();
                    }else{
                        dbManager = new DBManager(this);
                        dbManager.open();
                        dbManager.insertUser(setNombre.getText().toString(), setApellido.getText().toString());
                        Intent intent = new Intent(this, ActivityElegir.class);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.fadein, R.anim.fadeout);
                            startActivityForResult(intent, CHOOSE_ACTIVITY, options.toBundle());
                        } else {
                            startActivityForResult(intent, CHOOSE_ACTIVITY);
                        }
                    }
                });
        Intent soundService = new Intent(MainActivity.this, BackgroundSound.class);
        startService(soundService);
    }


}