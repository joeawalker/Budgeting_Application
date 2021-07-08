//##############################################
//# Name: Joe Walker                           #
//# Project: Graded Unit 2                     #
//# Project Title: Budget Tracker              #
//# Class: QBDD-F182B                          #
//##############################################

package com.example.graded_unit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Expenses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        //Creates bar at the top with title of page and back button
        getSupportActionBar().setTitle("Expenses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Opens list file and loads values
        final SharedPreferences sharedList = getSharedPreferences("List", Context.MODE_PRIVATE);
        Set<String> set = sharedList.getStringSet("key", null);
        Set<String> numSet = sharedList.getStringSet("values", null);

        //Creates editor and opens reset boolean
        final SharedPreferences.Editor ed = sharedList.edit();
        Boolean reset = sharedList.getBoolean("reset", false);

        //Opens Expense Value file, loads and displays the expense to the textView
        final SharedPreferences sharedPref = getSharedPreferences("Expense Value", Context.MODE_PRIVATE);
        final Float number = sharedPref.getFloat("Expense Value",0);
        TextView costView = findViewById(R.id.costView);
        String cost = Float.toString(number);
        costView.setText("Total: £" + cost);

        //Creates and assigns listView to the list on the design page
        final ListView listView;
        listView = findViewById(R.id.listview);

        //Creates an array list for strings
        final ArrayList<String> arrayList=new ArrayList<>();
        //If the values have not been reset then adds the set containing list items to list
        if (reset == false) {
            arrayList.addAll(set);
        }

        //Creates an array adapter for the arrayList
        ArrayAdapter arrayAdapter=new ArrayAdapter(Expenses.this, android.R.layout.simple_list_item_1, arrayList);

        //Sets the adapter to the listView so you can view the list on the page
        listView.setAdapter(arrayAdapter);

        //Creates an array list
        final ArrayList valList = new ArrayList();
        //If the values have not been reset then add the set containing list values to list
        if (reset == false) {
            valList.addAll(numSet);
        }

        //Sets on click listener for when an item in the list is selected
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                //Tries to run code and if an error is caught then the user us alerted
                try {
                    //Alerts the player which item they have selected
                    Toast.makeText(Expenses.this, "Selected: " + arrayList.get(position), Toast.LENGTH_SHORT).show();

                    //Creates an alert dialog asking if user wants to delete item
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Expenses.this);
                    builder.setTitle("Delete Item");
                    builder.setMessage("Are you sure?");

                    //If "Yes" is clicked then the item is removed from the list
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //Assigns the items value to a float
                            String sVal = valList.get(position).toString();
                            Float val = Float.parseFloat(sVal);

                            //Opens the Expense Value file and takes away the item value from that and updates the new value
                            SharedPreferences sharedExpense = getSharedPreferences("Expense Value", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedExpense.edit();
                            Float expenseVal = sharedExpense.getFloat("Expense Value", 0);
                            expenseVal = expenseVal - val;
                            edit.putFloat("Expense Value", expenseVal);
                            edit.apply();

                            //Displays the new total after removing the item
                            TextView total = findViewById(R.id.costView);
                            total.setText("Total: £" + expenseVal.toString());

                            //Sets the adapter to the listView so you can view the updated list on the page
                            ListView listView;
                            listView = findViewById(R.id.listview);
                            ArrayAdapter arrayAdapter = new ArrayAdapter(Expenses.this, android.R.layout.simple_list_item_1, arrayList);
                            listView.setAdapter(arrayAdapter);

                            //Opens List file, loads values and creates editor
                            SharedPreferences sharedList = getSharedPreferences("List", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editList = sharedList.edit();
                            Set<String> set = sharedList.getStringSet("key", null);
                            Set<String> numSet = sharedList.getStringSet("values", null);

                            //Assigns sets to lists
                            List list1 = new ArrayList(set);
                            List list2 = new ArrayList(numSet);

                            //Removes item selected and items value from the lists
                            list1.remove(position);
                            list2.remove(position);

                            //Clears the item set and then adds the list, updating it
                            set.clear();
                            set.addAll(list1);

                            //Clears the value set and then adds the list, updating it
                            numSet.clear();
                            numSet.addAll(list2);

                            //Saves the new sets to the List file
                            editList.putStringSet("key", set);
                            editList.putStringSet("values", numSet);
                            editList.apply();

                            //Removes them from the list been displayed
                            arrayList.remove(position);
                            valList.remove(position);

                            dialog.dismiss();
                        }
                    });

                    //If "No" is selected then the item is not deleted
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Expenses.this, "Canceled!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

                    //Runs the alert dialog
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                //If an error is found the user us alerted
                catch(Exception e){
                    Toast.makeText(Expenses.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Sets the button for adding a new item to list
        Button button = findViewById(R.id.buttonBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ed.putBoolean("reset", false);
                ed.apply();

                //Assigns user inputs to strings
                EditText editCost = findViewById(R.id.editCost);
                EditText itemName = findViewById(R.id.itemName);
                String cost = editCost.getText().toString();
                String expense = itemName.getText().toString();

                //If either of the editTexts are empty the user is alerted and cannot add item
                if (cost.matches("") || expense.matches("")){
                    Toast.makeText(Expenses.this, "You must enter an expense and value!", Toast.LENGTH_LONG).show();
                    return;
                }

                //Sets the adapter to the listView so you can view the updated list on the page
                ListView listView;
                listView = findViewById(R.id.listview);
                ArrayAdapter arrayAdapter=new ArrayAdapter(Expenses.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(arrayAdapter);

                //Adds expense to the list and the cost to another list with a toast to confirm
                arrayList.add(expense + ": £" + cost);
                valList.add(cost);
                Toast.makeText(Expenses.this, "Added " + expense + ": £" + cost + " to the list!", Toast.LENGTH_SHORT).show();


                //Saves the new total expenses value
                SharedPreferences sharedPref = getSharedPreferences("Expense Value", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                Float previousCost = sharedPref.getFloat("Expense Value", 0);
                Float newCost = Float.parseFloat(cost);
                Float totalCost = newCost + previousCost;
                editor.putFloat("Expense Value", totalCost);
                editor.apply();

                //Displays the total expenses value at the top
                TextView costView = findViewById(R.id.costView);
                String expenseValue = totalCost.toString();
                costView.setText("Total: £" + expenseValue);
                SharedPreferences sharedList = getSharedPreferences("List", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedList.edit();

                //Converts arrayList to set to be saved
                Set<String> set = new HashSet();
                set.addAll(arrayList);
                edit.putStringSet("key", set);
                edit.commit();

                //Converts valList to set to be saved
                Set<String> numSet = new HashSet<>();
                numSet.addAll(valList);
                edit.putStringSet("values", numSet);
                edit.commit();
            }
        });
    }
}