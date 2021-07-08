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

public class Earnings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings);

        //Sets action bar with title "Earnings" and an option to go back to previous page.
        getSupportActionBar().setTitle("Earnings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Opens the file "earnings" and sets an editor for editing the files contents
        final SharedPreferences earnings = getSharedPreferences("earnings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = earnings.edit();

        //Loads the last week total and the overall total value of earnings to the activity
        Float weekTotal = earnings.getFloat("week", 0);
        Float totalNum = earnings.getFloat("total", 0);
        TextView week = findViewById(R.id.weeksEarnings);

            //Sets a button for entering the information the user has giving and updates the total values
            Button enterBtn = findViewById(R.id.enterBtn);
            enterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Tries the code and if an issue is found it catches it
                    try {
                        //Gets the ID for the user inputs
                        EditText contractedHours = findViewById(R.id.contractedHours);
                        EditText overtimeHours = findViewById(R.id.overtimeHours);

                        //Converts the users inputs to floats
                        Float contractedNum = Float.valueOf(contractedHours.getText().toString());
                        Float overtimeNum = Float.valueOf(overtimeHours.getText().toString());

                        //Loads the users wage and calculates the money made from the overtime hours and the contracted hours and then adds them together to make that weeks total
                        SharedPreferences wage = getSharedPreferences("wage", Context.MODE_PRIVATE);
                        Float wageNum = wage.getFloat("wage", 0);
                        Float overtimeMoney = overtimeNum * wageNum;
                        Float contractedMoney = contractedNum * wageNum;
                        Float weekTotal = (contractedNum + overtimeNum) * wageNum;

                        //Saves the week total and the money made from contracted hours
                        editor.putFloat("week", weekTotal);
                        editor.putFloat("Contracted Money", contractedMoney);

                        //Adds the users input for contracted hours to the currently saved hours and updates the value to the file
                        Float previousCont = earnings.getFloat("Contracted Hours", 0);
                        Float totalCont = previousCont + contractedNum;
                        editor.putFloat("Contracted Hours", totalCont);
                        editor.apply();

                        //Adds the users input for overtime money to the currently saved money value and updates the value to the file
                        Float prevOverMon = earnings.getFloat("Overtime Money", 0);
                        Float totalOverMon = prevOverMon + overtimeMoney;
                        editor.putFloat("Overtime Money", totalOverMon);
                        editor.apply();

                        //Adds the users input for overtime hours to the currently saved hours and updates the value to the file
                        Float previousOver = earnings.getFloat("Overtime Hours", 0);
                        Float totalOver = previousOver + overtimeNum;
                        editor.putFloat("Overtime Hours", totalOver);
                        editor.apply();

                        //Adds the users input for total money made to the currently saved total and updates the value to the file
                        Float previousTotal = earnings.getFloat("total", 0);
                        Float totalNum = previousTotal + weekTotal;
                        editor.putFloat("total", totalNum);
                        editor.apply();

                        //Updates the display for week total and the overall total to the text views
                        TextView week = findViewById(R.id.weeksEarnings);
                        TextView total = findViewById(R.id.totalEarnings);
                        week.setText("This week you have made: £" + String.format("%.2f", weekTotal));
                        total.setText("In total you have earned: £" + String.format("%.2f", totalNum));
                }
                    //Catches any errors and makes a toast alerting the user that an error was found
                    catch(Exception e){
                                Toast.makeText(Earnings.this, "Error on button press", Toast.LENGTH_SHORT).show();
                }
                }
            });

        //Displays the week total and the overall total to the activity
        TextView total = findViewById(R.id.totalEarnings);
        week.setText("This week you have made: £" + weekTotal.toString());
        total.setText("In total you have earned: £" + totalNum.toString());
    }
}
