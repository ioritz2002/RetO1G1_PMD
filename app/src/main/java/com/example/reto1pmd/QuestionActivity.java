package com.example.reto1pmd;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class QuestionActivity extends AppCompatActivity {

    // Variables
    private Map<String, List<String>> questionOpc = null;
    private Integer result = 0;

    // Android items
    private Resources res = null;
    private LinearLayout testLinear = null;
    private TextView resultTest = null;
    private Button btnFinish = null;
    private MediaPlayer mp = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        res = getResources();
        questionOpc = new TreeMap<>();
        resultTest = new TextView(this);
        mp = MediaPlayer.create(this, R.raw.show_result);
        mp.setVolume(50, 50);

        // Get linear layout
        testLinear = findViewById(R.id.testLinearLayout);

        // Retrieve what series the user selected
        Bundle extra = getIntent().getExtras();
        String selectedSerie = extra.getString("serie");
        createMap(selectedSerie);
        createButtons();
        showResult();
    }

    private void createMap(@NonNull String serie) {
        // Get series name
        String starWars = Arrays.asList(res.getStringArray(R.array.series)).get(0);
        String rickMorty = Arrays.asList(res.getStringArray(R.array.series)).get(1);

        // Check what series is selected to store the questions and the options
        if (serie.equalsIgnoreCase(starWars)) {
            List<String> questionList = Arrays.asList(res.getStringArray(R.array.starwars_question));
            questionOpc.put(questionList.get(0), Arrays.asList(res.getStringArray(R.array.starwars_opc_1)));
            questionOpc.put(questionList.get(1), Arrays.asList(res.getStringArray(R.array.starwars_opc_2)));
            questionOpc.put(questionList.get(2), Arrays.asList(res.getStringArray(R.array.starwars_opc_3)));
            questionOpc.put(questionList.get(3), Arrays.asList(res.getStringArray(R.array.starwars_opc_4)));
            questionOpc.put(questionList.get(4), Arrays.asList(res.getStringArray(R.array.starwars_opc_5)));
        } else if (serie.equalsIgnoreCase(rickMorty)) {
            List<String> questionList = Arrays.asList(res.getStringArray(R.array.rickmorty_question));
            questionOpc.put(questionList.get(0), Arrays.asList(res.getStringArray(R.array.rickmorty_opc_1)));
            questionOpc.put(questionList.get(1), Arrays.asList(res.getStringArray(R.array.rickmorty_opc_2)));
            questionOpc.put(questionList.get(2), Arrays.asList(res.getStringArray(R.array.rickmorty_opc_3)));
            questionOpc.put(questionList.get(3), Arrays.asList(res.getStringArray(R.array.rickmorty_opc_4)));
            questionOpc.put(questionList.get(4), Arrays.asList(res.getStringArray(R.array.rickmorty_opc_5)));
        }

    }

    private void createButtons() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(100, 0, 100, 0);
        TextView[] textViews = new TextView[5];
        RadioGroup[] rg = new RadioGroup[5];
        RadioButton[] rb = new RadioButton[5];


        int i = 0;
        for (Map.Entry<String, List<String>> set : questionOpc.entrySet()) {
            // Create text views with the question
            textViews[i] = new TextView(this);
            textViews[i].setText(set.getKey());
            textViews[i].setGravity(Gravity.CENTER_HORIZONTAL);
            textViews[i].setLayoutParams(params);
            textViews[i].setTextSize(26);
            textViews[i].setId(i + 201);

            // Add text view to the liner layout
            testLinear.addView(textViews[i]);

            // Create radio group
            rg[i] = new RadioGroup(this);
            rg[i].setGravity(Gravity.CENTER_HORIZONTAL);
            rg[i].setOrientation(RadioGroup.VERTICAL);

            // Create radio buttons
            for (int j = 0; j < set.getValue().size(); j++) {
                rb[j] = new RadioButton(this);
                rb[j].setText(set.getValue().get(j));
                rb[j].setId(j + 1001);
                // Add button to the
                rg[i].addView(rb[j]);
            }
            rg[i].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                Integer previousId = 0;

                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // This will get the radiobutton that has changed in its check state
                    RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                    // This puts the value (true/false) into the variable
                    boolean isChecked = checkedRadioButton.isChecked();
                    if (isChecked) {
                        if (previousId != 0) {
                            result -= previousId;
                        }
                        previousId = checkedId - 1000;
                        result += checkedId - 1000;
                    }
                }
            });
            testLinear.addView(rg[i]);
            i++;
        }

    }

    private void showResult() {
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        btnParams.setMargins(100, 100, 100, 125);
        btnFinish = new Button(this);
        btnFinish.setText(getString(R.string.text_btnFinish));
        btnFinish.setTextSize(30);
        btnFinish.setLayoutParams(btnParams);
        btnFinish.setBackgroundColor(Color.CYAN);
        btnFinish.setOnClickListener(OnClickListener -> {
            mp.start();
            Intent intent = new Intent(this, ResultadoActivity.class);
            intent.putExtra("puntuacion", result);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.fadein, R.anim.fadeout);
            startActivityForResult(intent, MainActivity.RESULT_ACTIVITY, options.toBundle());
        });
        testLinear.addView(btnFinish);

    }
}