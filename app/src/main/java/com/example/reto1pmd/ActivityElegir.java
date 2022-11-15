package com.example.reto1pmd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityElegir extends AppCompatActivity {

    private TextView hola = null;
    private Resources res = null;
    private TextView elegir = null;
    private Spinner box = null;
    private Button test = null;
    private DBManager dbManager = null;
    private String nombre = "";
    private String apellido = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getResources();
        setContentView(R.layout.activity_elegir);

        hola = (TextView) findViewById(R.id.nomApeView);
        elegir = (TextView) findViewById(R.id.textElegir);
        box = (Spinner) findViewById(R.id.comboBox);

        List<String> serie = Arrays.asList(res.getStringArray(R.array.series));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, serie);
        box.setAdapter(adapter);
        test = (Button) findViewById(R.id.empezar);
        Bundle extras = getIntent().getExtras();

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.select_UltimoUsuario();
        if (cursor.getCount() > 0) {
            do {
                nombre = cursor.getString(1);
                apellido = cursor.getString(2);
            } while (cursor.moveToNext());
        }
        hola.setText(getString(R.string.hello) + " " + nombre + " " + apellido);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityElegir.this, QuestionActivity.class);
                intent.putExtra("serie", Arrays.asList(res.getStringArray(R.array.series)).get(box.getSelectedItemPosition()));
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(ActivityElegir.this, R.anim.fadein, R.anim.fadeout);
                    startActivityForResult(intent, MainActivity.QUESTION_ACTIVITY, options.toBundle());
                } else {
                    startActivityForResult(intent, MainActivity.QUESTION_ACTIVITY);
                }
            }
        });
    }
}