package com.example.reto1pmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultadoActivity extends AppCompatActivity {
    private Integer numeroPersonaje;
    private String nombre;
    private String apellido;
    private TextView textResultado;
    private ImageView imgPersonaje;
    private Button btnAbrirWiki;
    private Button btnCompartir;
    private SQLiteDatabase database;
    private static final String QUERY_RESULTADO_PERSONAJE = "SELECT * FROM personajes WHERE valoracion_correspondiente = round(?,0) ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        //Obtener los componentes
        textResultado = findViewById(R.id.txtResultado);
        imgPersonaje = findViewById(R.id.imgPersonaje);
        btnAbrirWiki = findViewById(R.id.btnWiki);
        btnCompartir = findViewById(R.id.btnCompartir);

        //Recibe los parametros de la ventana anterior
        Bundle extras  = getIntent().getExtras();
        /*numeroPersonaje = extras.getInt("NUM_PERSONAJE");
        nombre = extras.getString("NOMBRE_USUARIO");
        apellido = extras.getString("APELLIDO_USUARIO");*/

        database = SQLiteDatabase.openDatabase("reto1_g1_pmd_database", null, 0);

        //btnAbrirWiki.setOnClickListener();
        btnCompartir.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent compartir = new Intent(Intent.ACTION_S)
                    }
                }
        );
    }
}