package com.example.reto1pmd;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.View;
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

    private Resources res = null;
    private Map<String, List<String>> questionOpc = null;
    private LinearLayout testLinear = null;
    private Integer result = 0;
    private TextView resultTest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        res = getResources();
        questionOpc = new TreeMap<>();
        resultTest = new TextView(this);

        // Get linear layout
        testLinear = findViewById(R.id.testLinearLayout);

        // Retrieve what series the user selected
        Bundle extra = getIntent().getExtras();
        String selectedSerie = extra.getString("serie");
        createMap(selectedSerie);
        createButtons();

    }

    private void createMap(String serie) {
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
        params.setMargins(100, 100, 100, 0);
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
                System.out.println(rb[j].getId());
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

}