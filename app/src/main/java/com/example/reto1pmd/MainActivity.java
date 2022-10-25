package com.example.reto1pmd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView nombre = null;
    private TextView apellido = null;
    private EditText setNombre = null;
    private EditText setApellido = null;
    private Button start = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = (TextView)findViewById(R.id.nameView);
        apellido = (TextView)findViewById(R.id.apellidoView);
        setNombre = (EditText)findViewById(R.id.editNombre);
        setApellido = (EditText)findViewById(R.id.editApellido);
        start = (Button)findViewById(R.id.startButton);
    }
}