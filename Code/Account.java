//##############################################
//# Name: Joe Walker                           #
//# Project: Graded Unit 2                     #
//# Project Title: Budget Tracker              #
//# Class: QBDD-F182B                          #
//##############################################
package com.example.graded_unit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //Sets action bar with title "Account" and an option to go back to previous page.
        getSupportActionBar().setTitle("Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Loads and displays the username at bottom of page
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String name = sharedPref.getString("username", "");
        TextView nameFooter;
        nameFooter = findViewById(R.id.nameFooter);
        nameFooter.setText(name);

        //Sets a button to access the wage activity
        Button wageBtn = findViewById(R.id.wageBtn);
        wageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, Wage.class));
            }
        });

        //Sets a button to access the expenses activity
        Button expensesBtn = findViewById(R.id.expensesBtn);
        expensesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, Expenses.class));
            }
        });

        //Sets a button to access the earnings activity
        Button earningsBtn = findViewById(R.id.earningsBtn);
        earningsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, Earnings.class));
            }
        });

        //Sets a button to access the summary activity
        Button summaryBtn = findViewById(R.id.summaryBtn);
        summaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, Summary.class));
            }
        });

        //Sets a button to access the savings activity
        Button savingsBtn = findViewById(R.id.savingsBtn);
        savingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, Savings.class));
            }
        });
    }
}
