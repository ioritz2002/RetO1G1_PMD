package com.example.reto1pmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Integer idPersonaje;
    private String nombrePersonaje;
    private VideoView videoPersonaje;
    private String urlWiki;
    private Cursor cursor;
    private DBManager dbManager;
    private String serie;
    private Map<String, List<String>> personajes;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        res = getResources();
        //Obtener los componentes
        textResultado = findViewById(R.id.txtResultado);
        imgPersonaje = findViewById(R.id.imgPersonaje);
        btnAbrirWiki = findViewById(R.id.btnWiki);
        btnCompartir = findViewById(R.id.btnCompartir);

        //Recibe los parametros de la ventana anterior
        Bundle extras  = getIntent().getExtras();
        valoracionTotalObtenida = extras.getInt("VALORACION_TOTAL_OBTENIDA");
        serie = extras.getString("SERIE");

        dbManager = new DBManager(this);
        dbManager.open();
       // database = SQLiteDatabase.openDatabase("reto1_g1_pmd_database", null, 0);

        //Obtener el id de usuario y el nombre al que le vamos a introducir el personaje que le a salido
        cursor = dbManager.select_UltimoUsuario();
        if (cursor.getCount() > 0){
            do {
                idUsuario = cursor.getInt(0);
                nombreUsuario = cursor.getString(1);
            } while (cursor.moveToNext());
        }

        //TODO Paramentros: ambos son la valoracion obtenida
        personajes = new HashMap<>();
        cargarPersonajes(serie, personajes);



        cursor = database.rawQuery("SELECT * " +
                "FROM t_character c WHERE c.puntuacion > (? - 3)" +
                "AND c.puntuacion < (? + 3)", new String[] {String.valueOf(valoracionTotalObtenida), String.valueOf(valoracionTotalObtenida)});

        // TODO Si hay algun resultado
        if (cursor.getCount() > 0){

            while (cursor.moveToNext()){
                idPersonaje = cursor.getInt(0);
                puntuacionPersonaje = cursor.getInt(1);
                nombrePersonaje = cursor.getString(2);

                urlWiki = cursor.getString(4);
            }
            //Parametros primero puntuacion personaje luego nombre usuario
            dbManager.update_PuntuacionUsuario(idUsuario, puntuacionPersonaje);
           // database.execSQL("UPDATE t_user SET t_user.puntuacion = ? WHERE t_user._id = ?" ,new String[] {String.valueOf(puntuacionPersonaje), String.valueOf(idUsuario)});

        } else {
            Toast.makeText(this, "Error al cargar", Toast.LENGTH_LONG).show();
        }


        btnCompartir.setOnClickListener(
                v -> {
                    Intent compartir = new Intent();
                    compartir.setAction(Intent.ACTION_SEND);
                    compartir.setType("text/plain");
                    compartir.setData(Uri.parse(nombreUsuario + "El personaje correspondiente al personaje es: " + nombrePersonaje));

                    Intent chooser = Intent.createChooser(compartir, getString(R.string.text_chooserMessage));
                    startActivity(chooser);
                }
        );

        btnAbrirWiki.setOnClickListener(
                v -> {
                    Intent wiki = new Intent(Intent.ACTION_WEB_SEARCH);
                    wiki.setData(Uri.parse(urlWiki));

                    Intent chooser = Intent.createChooser(wiki, getString(R.string.text_chooserMessage));
                    startActivity(chooser);
                }
        );

        dbManager.close();

    }

    private void cargarPersonajes(String serie, Map<String, List<String>> personajes) {
        String starWars = Arrays.asList(res.getStringArray(R.array.series)).get(0);
        String rickMorty = Arrays.asList(res.getStringArray(R.array.series)).get(1);

        if (serie.equalsIgnoreCase(starWars)){
            for (int i = 0; i < Arrays.asList(res.getStringArray(R.array.Personajes_starWars)).size(); i++){

            }
        }
        else if(serie.equalsIgnoreCase(rickMorty)){

        }
    }
}