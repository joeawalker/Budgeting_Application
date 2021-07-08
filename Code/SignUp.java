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
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Creates action bar with title "Sign up" and option to go back
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Creates the button to check validation of entered data
        Button validation = findViewById(R.id.validation);
        validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Retrieves user inputs
                EditText signInID = findViewById(R.id.signInID);
                EditText userSignInPassword = findViewById(R.id.userSignInPassword);
                EditText signInPasswordCheck = findViewById(R.id.signInPasswordCheck);
                TextView userInfoCheck = findViewById(R.id.userInfoCheck);

                //Assigns password inputs to strings
                String password1 = userSignInPassword.getText().toString();
                String password2 = signInPasswordCheck.getText().toString();

                //Checks is user ID is at least 3 characters long
                if (signInID.length() >=3) {
                    //Checks if the password length is at least 6 characters long
                    if (password1.length() >= 6) {
                        //Checks that the first password the user entered is equal to the second password inputted
                        if (password1.equals(password2)) {
                            //Saves the username and password to userInfo file and brings the user back to the signIn activity
                            userInfoCheck.setText("Passwords match!");
                            SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("username", signInID.getText().toString());
                            editor.putString("password", password2);
                            editor.apply();
                            Toast.makeText(SignUp.this, "Signed up", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignUp.this, MainActivity.class));
                        }
                        //If the passwords do not match the user is alerted
                        else {
                            userInfoCheck.setText("Passwords do not match!");
                        }
                    }
                    //If the password doesn't meet the requirements the user is alerted
                    else {
                        Toast.makeText(SignUp.this, "Password must contain more than 5 characters", Toast.LENGTH_SHORT).show();
                    }
                }
                //Is the user ID doesn't meet requirements the user is alerted
                else{
                    Toast.makeText(SignUp.this, "User ID must be at least 3 characters long", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
