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
    // User
    private String nombreUsuario = "";
    private Integer idUsuario = 0;
    private Integer valoracionTotalObtenida = 0;

    //Personaje
    private List<String> datosPersonaje = null;
    private Integer puntuacionPersonaje = 0;
    private String urlWiki = "";
    private String nombrePersonaje = "";
    private String serie;

    // Android elements
    private TextView textResultado = null;
    private Button btnAbrirWiki = null;
    private Button btnCompartir = null;
    private ImageView imgPersonaje = null;
    private VideoView videoPersonaje = null;

    // Other
    private Cursor cursor;
    private DBManager dbManager;
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
        videoPersonaje = findViewById(R.id.videoView);

        //Recibe los parametros de la ventana anterior
        Bundle extras = getIntent().getExtras();
        valoracionTotalObtenida = extras.getInt("puntuacion");
        serie = extras.getString("serie");

        dbManager = new DBManager(this);
        dbManager.open();

        //Obtener el id de usuario y el nombre al que le vamos a introducir el personaje que le a salido
        cursor = dbManager.select_UltimoUsuario();
        if (cursor.getCount() > 0) {
            do {
                idUsuario = cursor.getInt(0);
                nombreUsuario = cursor.getString(1);
            } while (cursor.moveToNext());
        }
        cargarPersonaje(serie);
        nombrePersonaje = datosPersonaje.get(0);
        urlWiki = datosPersonaje.get(0);
        puntuacionPersonaje = Integer.parseInt(datosPersonaje.get(2));

        textResultado.setText(textResultado.getText() +" "+ datosPersonaje.get(0));
        //dbManager.update_PuntuacionUsuario(idUsuario, puntuacionPersonaje);


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

    private void cargarPersonaje(String serie) {
        String starWars = Arrays.asList(res.getStringArray(R.array.series)).get(0);
        String rickMorty = Arrays.asList(res.getStringArray(R.array.series)).get(1);

        String path = "android.resource://com.example.reto1pmd/";
        Uri u;

        if (serie.equalsIgnoreCase(starWars)) {
            Integer valoracionAnakin = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.Anakin)).get(2));
            Integer valoracionBabyYoda = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.BabyYoda)).get(2));
            Integer valoracionObiWan = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.ObiWan)).get(2));
            Integer valoracionHan = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.Han)).get(2));
            Integer valoracionLuke = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.Luke)).get(2));


            if ((valoracionAnakin > (valoracionTotalObtenida - 3)) && (valoracionAnakin < (valoracionTotalObtenida + 3))) {
                datosPersonaje = Arrays.asList(res.getStringArray(R.array.Anakin));
                imgPersonaje.setImageResource(R.drawable.anakin_skywalker);
                path += R.raw.anakin;
            } else if ((valoracionBabyYoda > (valoracionTotalObtenida - 3)) && (valoracionBabyYoda < (valoracionTotalObtenida + 3))) {
                datosPersonaje = Arrays.asList(res.getStringArray(R.array.BabyYoda));
                imgPersonaje.setImageResource(R.drawable.babyyoda);
                path += R.raw.luke;
            } else if ((valoracionObiWan > (valoracionTotalObtenida - 3)) && (valoracionObiWan < (valoracionTotalObtenida + 3))) {
                datosPersonaje = Arrays.asList(res.getStringArray(R.array.ObiWan));
                imgPersonaje.setImageResource(R.drawable.obiwan);
                path += R.raw.obiwan;
            } else if ((valoracionHan > (valoracionTotalObtenida - 3)) && (valoracionHan < (valoracionTotalObtenida + 3))) {
                datosPersonaje = Arrays.asList(res.getStringArray(R.array.Han));
                imgPersonaje.setImageResource(R.drawable.hansolo);
                path += R.raw.han;
            } else if ((valoracionLuke > (valoracionTotalObtenida - 3)) && (valoracionLuke < (valoracionTotalObtenida + 3))) {
                datosPersonaje = Arrays.asList(res.getStringArray(R.array.Luke));
                imgPersonaje.setImageResource(R.drawable.luke_skywalker);
                path += R.raw.luke;
            }
        } else if (serie.equalsIgnoreCase(rickMorty)) {
            Integer valoracionRick = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.Rick)).get(2));
            Integer valoracionMorty = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.Morty)).get(2));
            Integer valoracionSummer = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.Summer)).get(2));
            Integer valoracionSrMeesseks = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.Sr_Meeseeks)).get(2));
            Integer valoracionJerry = Integer.parseInt(Arrays.asList(res.getStringArray(R.array.Jerry)).get(2));

            if ((valoracionRick > (valoracionTotalObtenida - 3)) && (valoracionRick < (valoracionTotalObtenida + 3))) {
                datosPersonaje = Arrays.asList(res.getStringArray(R.array.Rick));
                imgPersonaje.setImageResource(R.drawable.rick);
            } else if ((valoracionMorty > (valoracionTotalObtenida - 3)) && (valoracionMorty < (valoracionTotalObtenida + 3))) {
                datosPersonaje = Arrays.asList(res.getStringArray(R.array.Morty));
                imgPersonaje.setImageResource(R.drawable.morty);
            } else if ((valoracionSummer > (valoracionTotalObtenida - 3)) && (valoracionSummer < (valoracionTotalObtenida + 3))) {
                datosPersonaje = Arrays.asList(res.getStringArray(R.array.Summer));
                imgPersonaje.setImageResource(R.drawable.summer);
            } else if ((valoracionSrMeesseks > (valoracionTotalObtenida - 3)) && (valoracionSrMeesseks < (valoracionTotalObtenida + 3))) {
                datosPersonaje = Arrays.asList(res.getStringArray(R.array.Sr_Meeseeks));
                imgPersonaje.setImageResource(R.drawable.meeseeks);
            } else if ((valoracionJerry > (valoracionTotalObtenida - 3)) && (valoracionJerry < (valoracionTotalObtenida + 3))) {
                datosPersonaje = Arrays.asList(res.getStringArray(R.array.Jerry));
                imgPersonaje.setImageResource(R.drawable.jerry_smith);
            }
        }
        u = Uri.parse(path);
        videoPersonaje.setVideoURI(u);
        videoPersonaje.start();

    }
}