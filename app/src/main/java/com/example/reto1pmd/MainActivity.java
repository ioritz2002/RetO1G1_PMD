package com.example.reto1pmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final int CHOOSE_ACTIVITY = 1;
    public static final int QUESTION_ACTIVITY = 2;

    private Resources res = null;
    private TextView nombre = null;
    private TextView apellido = null;
    private EditText setNombre = null;
    private EditText setApellido = null;
    private Button start = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();

        nombre = (TextView) findViewById(R.id.nameView);
        apellido = (TextView) findViewById(R.id.apellidoView);
        setNombre = (EditText) findViewById(R.id.editNombre);
        setApellido = (EditText) findViewById(R.id.editApellido);
        start = (Button) findViewById(R.id.startButton);

        start = findViewById(R.id.startButton);
        start.setOnClickListener(
                (View v) -> {
            Intent intent = new Intent(this, QuestionActivity.class);
            intent.putExtra("serie", Arrays.asList(res.getStringArray(R.array.series)).get(0));
            startActivityForResult(intent, QUESTION_ACTIVITY);
        });
    }




}