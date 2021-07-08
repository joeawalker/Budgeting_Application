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
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets action bar with title Graded Unit
        getSupportActionBar().setTitle("Graded Unit");

        //Opens the userInfo file and retrieves the username and password
        final SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String name = sharedPref.getString("username", null);
        final String pw = sharedPref.getString("password", null);

        //Sets button for signing in
        Button signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Opens the Admin file and sets an editor called editAdmin
                SharedPreferences sharedAdmin = getSharedPreferences("Admin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editAdmin = sharedAdmin.edit();

                //Retrieves users inputs
                EditText userID = findViewById(R.id.userID);
                EditText userPassword = findViewById(R.id.userPassword);

                //Assigns the inputted username and password values to strings
                String username = userID.getText().toString();
                String password = userPassword.getText().toString();

                //Checks if anything is saved to username and password in the userInfo file
                if (name != null && pw != null) {
                    //If the inputted username and the password matches what is saved within the "userInfo" file then loads the menu activity and sets Admin boolean to false
                    if (username.equals(name) && password.equals(pw)) {
                        editAdmin.putBoolean("Admin", false);
                        editAdmin.apply();
                        startActivity(new Intent(MainActivity.this, Menu.class));
                    }
                    //If the username or password does not match then the user is alerted
                    else {
                        Toast.makeText(MainActivity.this, "Incorrect Log In Details!", Toast.LENGTH_SHORT).show();
                    }
                }
                //If there is nothing saved for username or password you are asked to sign up
                else{
                    Toast.makeText(MainActivity.this, "You need to sign up", Toast.LENGTH_SHORT).show();
                }
                //If Admin details are entered then log in as admin
                if (username.equals("Admin") && password.equals("00000")){
                    editAdmin.putBoolean("Admin", true);
                    editAdmin.apply();
                    startActivity(new Intent(MainActivity.this, Settings.class));
                }
            }
        });

        //Sets button for singing up and loads the SignUp page
        Button signUpPage = findViewById(R.id.signUpPage);
        //First checks if someone is already signed up and if there is the sign up button is greyed out
        if (name != null){
            signUpPage.setEnabled(false);
        }
        //If sign Up button clicked then loads the SignUp activity
        signUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });
    }
}
