package com.example.reto1pmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultadoActivity extends AppCompatActivity {
    private Integer valoracionTotalObtenida;
    private Integer idUsuario;
    private String nombreUsuario;
    private TextView textResultado;
    private ImageView imgPersonaje;
    private Button btnAbrirWiki;
    private Button btnCompartir;
    private SQLiteDatabase database;
    private Integer puntuacionPersonaje;
    private String nombrePersonaje;
    private String urlWiki;
    private Cursor cursor;

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

        database = SQLiteDatabase.openDatabase("reto1_g1_pmd_database", null, 0);

        //Obtener el id de usuario y el nombre al que le vamos a introducir el personaje que le a salido
        cursor = database.rawQuery("SELECT ID_USUARIO, NOMBRE" +
                " FROM usuarios WHERE HORA_REGISTRO IN (SELECT MAX(HORA_REGISTRO) FROM usuarios)", null);
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                idUsuario = cursor.getInt(0);
                nombreUsuario = cursor.getString(1);
            }
        }

        //Paramentros: ambos son la valoracion obtenida
        cursor = database.rawQuery("SELECT * " +
                "FROM personajes WHERE valoracion > (? - 3)" +
                "AND valoracion < (? + 3)", new String[] {String.valueOf(valoracionTotalObtenida), String.valueOf(valoracionTotalObtenida)});

        //Si hay algun resultado
        if (cursor.getCount() > 0){

            while (cursor.moveToNext()){
                puntuacionPersonaje = cursor.getInt(0);
                nombrePersonaje = cursor.getString(1);
                //imgPersonaje =
                urlWiki = cursor.getString(3);
            }
            //Parametros primero puntuacion personaje luego nombre usuario
            database.execSQL("UPDATE usuarios SET puntuacion_personaje = ? WHERE usuarios.ID_USUARIO = ?" ,new String[] {String.valueOf(puntuacionPersonaje), String.valueOf(idUsuario)});

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
                        compartir.setType("text/plain");
                        compartir.setData(Uri.parse(nombreUsuario + "El personaje correspondiente al personaje es: " + nombrePersonaje));

                        Intent chooser = Intent.createChooser(compartir, getString(R.string.text_chooserMessage));
                        startActivity(chooser);
                    }
                }
        );

        btnAbrirWiki.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent wiki = new Intent(Intent.ACTION_WEB_SEARCH);
                        wiki.setData(Uri.parse(urlWiki));

                        Intent chooser = Intent.createChooser(wiki, getString(R.string.text_chooserMessage));
                        startActivity(chooser);
                    }
                }
        );
    }
}