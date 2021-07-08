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
import android.widget.TextView;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        //Sets action bar with title "Summary" and back button
        getSupportActionBar().setTitle("Summary");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Sets name at top of page
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String name = sharedPref.getString("username", "");
        TextView displayName = findViewById(R.id.nameText);
        displayName.setText(name);

        //Displays savings
        SharedPreferences sharedSavings = getSharedPreferences("savings", Context.MODE_PRIVATE);
        Integer savingsInt = sharedSavings.getInt("percentage", 20);
        Float savingsVal = sharedSavings.getFloat("value", 0);
        Boolean isPercent = sharedSavings.getBoolean("is percent", true);
        TextView displaySavings = findViewById(R.id.savingsText);
        if (isPercent == true){
            displaySavings.setText("Saves: " + savingsInt.toString() + "%");
        }

        else if (isPercent == false){
            displaySavings.setText("Saves: £" + savingsVal.toString());
        }

        //Displays wage
        SharedPreferences sharedWage = getSharedPreferences("wage", Context.MODE_PRIVATE);
        Float wage = sharedWage.getFloat("wage", 0);
        TextView displayWage = findViewById(R.id.wageText);
        displayWage.setText("Wage: £" + wage.toString());

        //Displays expenses
        SharedPreferences sharedExpenses = getSharedPreferences("Expense Value", Context.MODE_PRIVATE);
        Float expenses = sharedExpenses.getFloat("Expense Value", 0);
        TextView displayExpense = findViewById(R.id.expensesText);
        displayExpense.setText("Expenses: £" + expenses.toString());

        //Displays earnings
        SharedPreferences sharedEarnings = getSharedPreferences("earnings", Context.MODE_PRIVATE);
        Float totalEarnings = sharedEarnings.getFloat("total", 0);
        Float overtimeHours = sharedEarnings.getFloat("Overtime Hours", 0);
        Float contractedHours = sharedEarnings.getFloat("Contracted Hours", 0);
        Float overtimeMoney = sharedEarnings.getFloat("Overtime Money", 0);
        Float totalHours = contractedHours+overtimeHours;

        TextView displayEarnings = findViewById(R.id.earningsText);
        displayEarnings.setText("Earnings: You have made £" + String.format("%.2f",totalEarnings) + " for working " + totalHours.toString() + " hours total so far.\n£" + overtimeMoney.toString() + " of that was made from working " + overtimeHours.toString() + " hours overtime.");

    }
}
