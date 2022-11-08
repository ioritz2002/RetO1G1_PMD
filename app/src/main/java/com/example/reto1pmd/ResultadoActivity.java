package com.example.reto1pmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultadoActivity extends AppCompatActivity {
    private Integer valoracionTotalObtenida;
    private String nombreUsuario;
    private TextView textResultado;
    private ImageView imgPersonaje;
    private Button btnAbrirWiki;
    private Button btnCompartir;
    private SQLiteDatabase database;
    private Integer puntuacionPersonaje;
    private String nombrePersonaje;
    private String urlWiki;

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
        valoracionTotalObtenida = extras.getInt("VALORACION_TOTAL_OBTENIDA");
        nombreUsuario = extras.getString("NOMBRE_USUARIO");


        database = SQLiteDatabase.openDatabase("reto1_g1_pmd_database", null, 0);
        //Paramentros: ambos son la valoracion obtenida
        Cursor cursor = database.rawQuery("SELECT * " +
                "FROM personajes" +
                "WHERE valoracion > (? - 3)" +
                "AND valoracion < (? + 3)", new String[] {String.valueOf(valoracionTotalObtenida), String.valueOf(valoracionTotalObtenidaº)});

        if (cursor.getCount() > 0){

            while (cursor.moveToNext()){
                puntuacionPersonaje = cursor.getInt(0);
                nombrePersonaje = cursor.getString(1);
                //imgPersonaje =
                urlWiki = cursor.getString(3);
            }
            //Parametros primero puntuacion personaje luego nombre usuario
            database.execSQL("UPDATE usuarios" +
                    "SET puntuacion_personaje = ? WHERE usuarios.nombre LIKE ?");

        } else {
            Toast toast = Toast.makeText(this, "Error al cargar", Toast.LENGTH_LONG);
        }


        //btnAbrirWiki.setOnClickListener();
        btnCompartir.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent compartir = new Intent();
                        compartir.setAction(Intent.ACTION_SEND);
                        compartir.setType("text/html");


                    }
                }
        );
    }
}