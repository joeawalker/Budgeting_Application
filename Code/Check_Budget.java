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

public class Check_Budget extends AppCompatActivity {

    //Creates a method for displaying the text summary of the budgeting
    static String summary(String fullMoney, String money, String savings, String remains, String budget){
        String summary = "You have made £" + fullMoney + " this month. \n\nThat leaves you with £" + money + " after expenses. \n\nYou should keep £" + savings + " into your bank to save. \n\nThis leaves you with £" + remains + " to spend on yourself this month. \n\nThat is £" + budget + " per week.";
        return summary;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check__budget);

        //Creates action bar with title and option to go back to previous page
        getSupportActionBar().setTitle("Check Budget");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Loads and displays the summary of last budget
        SharedPreferences sharedSummary = getSharedPreferences("summary", Context.MODE_PRIVATE);
        String textSummary = sharedSummary.getString("text", "");
        TextView summaryText = findViewById(R.id.summaryText);
        summaryText.setText(textSummary);

        //Creates the button function for the budgetBtn
        Button budgetBtn = findViewById(R.id.budgetBtn);
        budgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Assigns EditText's to ID's
                EditText mny = findViewById(R.id.budgetMoney);
                EditText wks = findViewById(R.id.budgetWeeks);
                EditText exp = findViewById(R.id.budgetExpenses);

                //Assigns text from EditText's to strings
                String extra = exp.getText().toString();
                String money = mny.getText().toString();
                String time = wks.getText().toString();

                //Checks for empty boxes and alerts player if found
                if (extra.matches("") || money.matches("") || time.matches("")){
                    Toast.makeText(Check_Budget.this, "You must enter values in every section!", Toast.LENGTH_LONG).show();
                    return;
                }

                //Converts string values to floats
                Float weeks = Float.valueOf(wks.getText().toString());
                Float ext = Float.parseFloat(extra);
                Float moneyNum = Float.parseFloat(money);

                //Loads savings values
                SharedPreferences sharedPref = getSharedPreferences("savings", Context.MODE_PRIVATE);
                int per = sharedPref.getInt("percentage", 20);
                Float val = sharedPref.getFloat("value", 0);
                Boolean isPercent = sharedPref.getBoolean("is percent", false);

                //Loads expenses values
                SharedPreferences sharedExpense = getSharedPreferences("Expense Value", Context.MODE_PRIVATE);
                Float expenseList = sharedExpense.getFloat("Expense Value", 0);

                //Takes the expenses from the list and adds the extra expenses the user has entered
                Float expenses = ext + expenseList;

                //Creates the budget file to save to later
                SharedPreferences sharedBudget = getSharedPreferences("budget", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedBudget.edit();

                //If the savings is a percentage then works out the budgeting with % and displays it using the summary method
                if (isPercent == true){
                    //Money after expenses
                    moneyNum = moneyNum - expenses;

                    //Percentage user wants to save from savings activity
                    Float percent = (float) per;
                    Float percentage = percent/100;

                    //How much the user should save
                    Float savings = moneyNum * percentage;

                    //How much the user has to spend per week
                    Float budget = (moneyNum - savings) / weeks;
                    editor.putFloat("budget", budget);
                    editor.apply();

                    //How much money is left after expenses and savings have been taking away
                    Float remains = moneyNum - savings;

                    //Displays the text with budget information
                    String text = summary(money, String.format("%.2f", moneyNum), String.format("%.2f", savings), String.format("%.2f", remains), String.format("%.2f", budget));
                    TextView summaryText = findViewById(R.id.summaryText);
                    summaryText.setText(text);

                    //Saves information to be displayed again later
                    SharedPreferences sharedSummary = getSharedPreferences("summary", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editSummary = sharedSummary.edit();
                    editSummary.putString("text", text);
                    editSummary.apply();
                }

                //If the savings is an exact value then works out the budgeting using that number and displays it using the summary method
                else if (isPercent == false){
                    //Retrieves savings value
                    Float savings = val;

                    //Money after expenses
                    moneyNum = moneyNum - expenses;

                    //Money to spend per week
                    Float budget = (moneyNum - savings) / weeks;
                    editor.putFloat("budget", budget);
                    editor.apply();

                    //How much money is left after expenses and savings have been taking away
                    Float remains = moneyNum - savings;

                    //Displays the text with budget information
                    String text = summary(money, String.format("%.2f", moneyNum), String.format("%.2f", savings), String.format("%.2f", remains), String.format("%.2f", budget));
                    TextView summaryText = findViewById(R.id.summaryText);
                    summaryText.setText(text);

                    //Saves information to be displayed again later
                    SharedPreferences sharedSummary = getSharedPreferences("summary", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editSummary = sharedSummary.edit();
                    editSummary.putString("text", text);
                    editSummary.apply();
                }
            }
        });
    }
}
