package com.example.reto1pmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

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
        valoracionTotalObtenida = extras.getInt("puntuacion");
        serie = extras.getString("serie");

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
        cargarPersonajes(serie);

        for (String set : personajes.keySet()){
            nombrePersonaje = set;
        }

        textResultado.setText(textResultado.getText() + nombrePersonaje);

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

    private void cargarPersonajes(String serie) {
        String starWars = Arrays.asList(res.getStringArray(R.array.series)).get(0);
        String rickMorty = Arrays.asList(res.getStringArray(R.array.series)).get(1);

        if (serie.equalsIgnoreCase(starWars)){
            Integer valoracionAnakin = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_starWars)).get(0));
            Integer valoracionBabyYoda = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_starWars)).get(1));
            Integer valoracionObiWan = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_starWars)).get(2));
            Integer valoracionHan =  Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_starWars)).get(3));
            Integer valoracionLuke = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_starWars)).get(4));


            if ((valoracionAnakin > (valoracionTotalObtenida - 3))&&(valoracionAnakin < (valoracionTotalObtenida + 3))){
                personajes.put(Arrays.asList(res.getStringArray(R.array.Anakin)).get(0),
                        Arrays.asList(res.getStringArray(R.array.Anakin)));
                puntuacionPersonaje = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_starWars)).get(0));

            } else if ((valoracionBabyYoda > (valoracionTotalObtenida - 3))&&(valoracionBabyYoda < (valoracionTotalObtenida + 3))){
                personajes.put(Arrays.asList(res.getStringArray(R.array.BabyYoda)).get(0),
                        Arrays.asList(res.getStringArray(R.array.BabyYoda)));
                puntuacionPersonaje = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_starWars)).get(1));
            }
            else if ((valoracionObiWan > (valoracionTotalObtenida - 3))&&(valoracionObiWan < (valoracionTotalObtenida + 3))){
                personajes.put(Arrays.asList(res.getStringArray(R.array.ObiWan)).get(0),
                        Arrays.asList(res.getStringArray(R.array.ObiWan)));
                puntuacionPersonaje = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_starWars)).get(2));
            }
            else if ((valoracionHan > (valoracionTotalObtenida - 3))&&(valoracionHan < (valoracionTotalObtenida + 3))){
                personajes.put(Arrays.asList(res.getStringArray(R.array.Han)).get(0),
                        Arrays.asList(res.getStringArray(R.array.Han)));
                puntuacionPersonaje = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_starWars)).get(3));
            }
            else if ((valoracionLuke > (valoracionTotalObtenida - 3))&&(valoracionLuke < (valoracionTotalObtenida + 3))){
                personajes.put(Arrays.asList(res.getStringArray(R.array.Luke)).get(0),
                        Arrays.asList(res.getStringArray(R.array.Luke)));
                puntuacionPersonaje = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_starWars)).get(4));
            }
        }
        else if(serie.equalsIgnoreCase(rickMorty)){
            Integer valoracionRick = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_rickMorty)).get(0));
            Integer valoracionMorty = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_rickMorty)).get(1));
            Integer valoracionSummer = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_rickMorty)).get(2));
            Integer valoracionSrMeesseks =  Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_rickMorty)).get(3));
            Integer valoracionJerry = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_rickMorty)).get(4));


            if ((valoracionRick > (valoracionTotalObtenida - 3))&&(valoracionRick < (valoracionTotalObtenida + 3))){
                personajes.put(Arrays.asList(res.getStringArray(R.array.Rick)).get(0),
                        Arrays.asList(res.getStringArray(R.array.Rick)));
                puntuacionPersonaje = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_rickMorty)).get(0));
            } else if ((valoracionMorty > (valoracionTotalObtenida - 3))&&(valoracionMorty < (valoracionTotalObtenida + 3))){
                personajes.put(Arrays.asList(res.getStringArray(R.array.Morty)).get(0),
                        Arrays.asList(res.getStringArray(R.array.Morty)));
                puntuacionPersonaje = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_rickMorty)).get(1));
            }
            else if ((valoracionSummer > (valoracionTotalObtenida - 3))&&(valoracionSummer < (valoracionTotalObtenida + 3))){
                personajes.put(Arrays.asList(res.getStringArray(R.array.Summer)).get(0),
                        Arrays.asList(res.getStringArray(R.array.Summer)));
                puntuacionPersonaje = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_rickMorty)).get(2));

            }
            else if ((valoracionSrMeesseks > (valoracionTotalObtenida - 3))&&(valoracionSrMeesseks < (valoracionTotalObtenida + 3))){
                personajes.put(Arrays.asList(res.getStringArray(R.array.Sr_Meeseeks)).get(0),
                        Arrays.asList(res.getStringArray(R.array.Sr_Meeseeks)));
                puntuacionPersonaje = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_rickMorty)).get(3));

            }
            else if ((valoracionJerry > (valoracionTotalObtenida - 3))&&(valoracionJerry < (valoracionTotalObtenida + 3))){
                personajes.put(Arrays.asList(res.getStringArray(R.array.Jerry)).get(0),
                        Arrays.asList(res.getStringArray(R.array.Jerry)));
                puntuacionPersonaje = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.puntuacion_personajes_rickMorty)).get(4));

            }
        }
    }
}