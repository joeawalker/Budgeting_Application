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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Savings extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        //Creates actionbar with title "Savings" and option to access previous page
        getSupportActionBar().setTitle("Savings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Opens the savings file and it's assigns it's values
        SharedPreferences sharedSavings = getSharedPreferences("savings", Context.MODE_PRIVATE);
        int per = sharedSavings.getInt("percentage", 20);
        Float val = sharedSavings.getFloat("value", 0);
        Boolean checkPercent = sharedSavings.getBoolean("is percent", true);

        //If user savings is a percentage displays text with %
        if (checkPercent == true){
            TextView savingsView = findViewById(R.id.savingsView);
            String number = Integer.toString(per);
            savingsView.setText("Savings: " + number + "%");
        }

        //If user savings is not a percentage displays text with £
        if (checkPercent == false){
            TextView savingsView = findViewById(R.id.savingsView);
            String number = val.toString();
            savingsView.setText("Savings: £" + number);
        }

        //Assigns ID's to buttons and editText's
        final EditText percentage = findViewById(R.id.percentage);
        final Button updateVal = findViewById(R.id.updateBtn2);
        final EditText value = findViewById(R.id.exactValue);
        final Button updatePerc = findViewById(R.id.updateBtn);

        //Creates a TextWatcher to enable and disable buttons depending on user input
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Empty
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //If the editText box for percentage is empty then enables the updateVal button for exact value
                String perc = percentage.getText().toString().trim();
                updateVal.setEnabled(perc.isEmpty());

                //If the editText box for exact value is empty then enables the updatePerc button for percentage value
                String val = value.getText().toString().trim();
                updatePerc.setEnabled(val.isEmpty());
            }
            @Override
            public void afterTextChanged(Editable s) {
                //Empty
            }
        };

        //Assigns the textWatcher to the editTexts
        percentage.addTextChangedListener(textWatcher);
        value.addTextChangedListener(textWatcher);

        //Assigns on click listener to updatePerc button
        updatePerc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Tries to run code and if an error is caught then message is displayed
                try {
                    //Converts user input to integer
                    String convertPerc = percentage.getText().toString();
                    int percentageNum = Integer.parseInt(convertPerc);

                    //If the number the user inputs is greater than 100 they are told it has to be between 0 and 100
                    if (percentageNum > 100) {
                        Toast.makeText(Savings.this, "Percentage must be between 0 and 100", Toast.LENGTH_LONG).show();
                        return;
                    }
                    //Opens the file "savings" and saves the number the user gave to percentage and sets is percent to true
                    SharedPreferences sharedPref = getSharedPreferences("savings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("percentage", percentageNum);
                    editor.putBoolean("is percent", true);
                    editor.apply();

                    //Changes number back to string and displays on page the new savings amount
                    String number = Integer.toString(percentageNum);
                    TextView savingsView = findViewById(R.id.savingsView);
                    savingsView.setText("Savings: " + number + "%");

                    //Alerts the player that values were saved
                    Toast.makeText(Savings.this, "Saved to savings!", Toast.LENGTH_SHORT).show();
                }
                //If an error was found during the above code the player is alerted
                catch(Exception e){
                    Toast.makeText(Savings.this, "You must enter a value", Toast.LENGTH_SHORT).show();;
                }
            }
        });

        //Assigns on click listener to updateVal button
        updateVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Tries to run code and if error is found then a message is displayed
                try {
                    String convertVal = value.getText().toString();
                    Float valNum = Float.parseFloat(convertVal);

                    //Opens savings file and saves savings value and sets is percent to false
                    SharedPreferences sharedPref = getSharedPreferences("savings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putFloat("value", valNum);
                    editor.putBoolean("is percent", false);
                    editor.apply();

                    //Sets number to string and displays on page the new savings amount
                    TextView savingsView = findViewById(R.id.savingsView);
                    String number = valNum.toString();
                    savingsView.setText("Savings: £" + number);

                    //Alerts the player that values have been saved
                    Toast.makeText(Savings.this, "Saved to savings!", Toast.LENGTH_LONG).show();
                }
                //If any errors are found the player is alerted
                catch(Exception e){
                    Toast.makeText(Savings.this, "You must enter a value", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
