package com.example.reto1pmd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ActivityElegir extends AppCompatActivity {

    private TextView elegir = null;
    private Spinner box = null;
    private Button test = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir);
        elegir = (TextView)findViewById(R.id.textElegir);
        box = (Spinner)findViewById(R.id.comboBox);
    }
}