//##############################################
//# Name: Joe Walker                           #
//# Project: Graded Unit 2                     #
//# Project Title: Budget Tracker              #
//# Class: QBDD-F182B                          #
//##############################################

package com.example.graded_unit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Set;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Creates action bar with title "Settings" and option to go back
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Creates dialog box prompting for confirmation of decision to clear expenses
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        //If "Yes" is selected then expenses is cleared
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //Opens Expense file and sets value to 0
                SharedPreferences sharedPref = getSharedPreferences("Expense Value", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putFloat("Expense Value", 0);
                editor.apply();

                //Opens file called List and retrieves the string sets that are saved
                SharedPreferences sharedList = getSharedPreferences("List", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedList.edit();
                Set<String> set = sharedList.getStringSet("key", null);
                Set<String> numSet = sharedList.getStringSet("values", null);

                //Tries to run code and if error is found a message will be displayed
                try {
                    //Clears the sets
                    numSet.clear();
                    set.clear();
                    edit.putStringSet("values", numSet);
                    edit.putStringSet("key", set);
                    edit.apply();
                }
                //If error is found in above code then the played is alerted
                catch (Exception e){
                    Toast.makeText(Settings.this, "Error", Toast.LENGTH_SHORT).show();
                }

                //Sets reset to true and saves
                Boolean reset = true;
                edit.putBoolean("reset", reset);
                edit.apply();

                //Alerts the player that expenses has been cleared and the dialog disappears
                Toast.makeText(Settings.this, "Cleared Expenses", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        //If "No" is selected then expenses is not cleared
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Settings.this, "Cancelled!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        //Creates dialog box for clearing earnings values
        final AlertDialog.Builder clear = new AlertDialog.Builder(this);
        clear.setTitle("Confirm");
        clear.setMessage("Are you sure?");

        //If "Yes" is clicked then earnings is cleared
        clear.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPref = getSharedPreferences("earnings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(Settings.this, "Cleared earnings!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        //If "No" is selected then earnings is not cleared
        clear.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Settings.this, "Cancelled!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        //Creates a dialog box for resetting everything to factory default
        final AlertDialog.Builder reset = new AlertDialog.Builder(this);
        reset.setTitle("Confirm");
        reset.setMessage("Are you sure?\nThis will delete and restore everything to default data values");

        //If "Yes" is clicked then everything is cleared
        reset.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences sharedEarnings = getSharedPreferences("earnings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editEarnings = sharedEarnings.edit();
                editEarnings.clear();
                editEarnings.apply();

                SharedPreferences sharedWage = getSharedPreferences("wage", Context.MODE_PRIVATE);
                SharedPreferences.Editor editWage = sharedWage.edit();
                editWage.clear();
                editWage.apply();

                SharedPreferences sharedSavings = getSharedPreferences("savings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editSavings = sharedSavings.edit();
                editSavings.clear();
                editSavings.apply();

                SharedPreferences sharedList = getSharedPreferences("List", Context.MODE_PRIVATE);
                SharedPreferences.Editor editList = sharedList.edit();
                Set<String> set = sharedList.getStringSet("key", null);
                Set<String> numSet = sharedList.getStringSet("values", null);
                numSet.clear();
                set.clear();
                editList.putStringSet("values", numSet);
                editList.putStringSet("key", set);
                editList.apply();
                Boolean reset = true;
                editList.putBoolean("reset", reset);
                editList.apply();

                SharedPreferences sharedExpenses = getSharedPreferences("Expense Value", Context.MODE_PRIVATE);
                SharedPreferences.Editor editExpenses = sharedExpenses.edit();
                editExpenses.clear();
                editExpenses.apply();

                SharedPreferences sharedSummary = getSharedPreferences("summary", Context.MODE_PRIVATE);
                SharedPreferences.Editor editSummary = sharedSummary.edit();
                editSummary.clear();
                editSummary.apply();

                SharedPreferences sharedBudget = getSharedPreferences("budget", Context.MODE_PRIVATE);
                SharedPreferences.Editor editBudget = sharedBudget.edit();
                editBudget.clear();
                editBudget.apply();

                SharedPreferences sharedUserInfo = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editUserInfo = sharedUserInfo.edit();
                editUserInfo.clear();
                editUserInfo.apply();

                //Logs user out after clearing everything
                startActivity(new Intent(Settings.this, MainActivity.class));

                //Alerts the user that the reset has completed
                Toast.makeText(Settings.this, "Reset Complete", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });

        //If "No" is selected then earnings is not cleared
        reset.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Settings.this, "Cancelled!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        //Assigns the reset expenses button to run the appropriate alert dialog
        Button expensesBtn = findViewById(R.id.expensesBtn);
        expensesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        //Assigns the reset password button to link the user to the changePassword activity
        Button passwordBtn = findViewById(R.id.passwordBtn);
        passwordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, changePassword.class));
            }
        });

        //Assigns the reset earnings button to run the appropriate alert dialog
        Button resetBtn = findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog show = clear.create();
                show.show();
            }
        });

        //Assigns the factory reset button to run the appropriate alert dialog
        Button factoryResetBtn = findViewById(R.id.factoryResetBtn);
        factoryResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog display = reset.create();
                display.show();
            }
        });
    }
}
