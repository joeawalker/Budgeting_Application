//##############################################
//# Name: Joe Walker                           #
//# Project: Graded Unit 2                     #
//# Project Title: Budget Tracker              #
//# Class: QBDD-F182B                          #
//##############################################

package com.example.graded_unit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Wage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wage);

        //Sets action bar with title "Wage" and back button
        getSupportActionBar().setTitle("Wage");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Displays wage from wage file
        SharedPreferences sharedPref = getSharedPreferences("wage", Context.MODE_PRIVATE);
        Float numbr = sharedPref.getFloat("wage",0);
        TextView wageView = findViewById(R.id.wageView);
        wageView.setText("£" + String.format("%.2f", numbr));

        //Sets update wage button
        Button updateWage = findViewById(R.id.enterWage);
        updateWage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Takes user input and converts to string
                EditText wage = findViewById(R.id.wage);
                String wages = wage.getText().toString();

                //If nothing has been entered, alerts the user and does nothing
                if (wages.matches("")){
                    Toast.makeText(Wage.this, "You didn't enter anything!", Toast.LENGTH_LONG).show();
                    return;
                }

                //Converts to 2 decimal places and then to a float number
                DecimalFormat df = new DecimalFormat("0.00");
                df.setMaximumFractionDigits(2);
                wages = df.format(Float.parseFloat(wages));
                float wageNum = Float.parseFloat(wages);

                //Saves wage
                SharedPreferences sharedPref = getSharedPreferences("wage", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putFloat("wage", wageNum);
                editor.apply();

                //Alerts the player that wage has been saved
                Toast.makeText(Wage.this, "Saved!", Toast.LENGTH_LONG).show();

                //Updates wage displayed
                TextView wageView = findViewById(R.id.wageView);
                wageView.setText("£" + String.format("%.2f", wageNum));
            }
        });
    }
}
