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

public class changePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //Sets action bar with title "Change password" and an option to go back to previous page.
        getSupportActionBar().setTitle("Change password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Sets button that resets password
        Button resetBtn = findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Retrieves user inputs
                EditText checkPass = findViewById(R.id.checkPass);
                EditText newPass1 = findViewById(R.id.newPass1);
                EditText newPass2 = findViewById(R.id.newPass2);

                //Changes user inputs to strings
                String pass = checkPass.getText().toString();
                String newPassword = newPass1.getText().toString();
                String newPassword2 = newPass2.getText().toString();

                //Opens a file called userInfo and retrieves current set password
                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String currentPass = sharedPref.getString("password", "");

                //Open a file called Admin to check if the admin is logged on
                SharedPreferences sharedAdmin = getSharedPreferences("Admin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editAdmin = sharedAdmin.edit();
                Boolean isAdmin = sharedAdmin.getBoolean("Admin", false);

                //Checks if the current password is equal to what the user put for verification or if the admin is logged on
                if (pass.equals(currentPass) || isAdmin == true){
                    Toast.makeText(changePassword.this, "Password match!", Toast.LENGTH_LONG).show();
                    //Checks if new password contains at least 6 characters
                    if (newPassword.length() >= 6) {
                        //Checks if the new password matches the second entry of the new password and then saves it to "password" under "userinfo" and logs out
                        if (newPassword.equals(newPassword2)) {
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("password", newPassword2);
                            editor.apply();
                            editAdmin.putBoolean("Admin", false);
                            startActivity(new Intent(changePassword.this, MainActivity.class));
                            Toast.makeText(changePassword.this, "Password changed!", Toast.LENGTH_LONG).show();
                        }
                        //If the new password entered does not match the second entry of the new password then the user is alerted
                        else {
                            Toast.makeText(changePassword.this, "New passwords no NOT match!", Toast.LENGTH_LONG).show();
                        }
                    }
                    //If new password contains less than 6 characters the user is alerted
                    else{
                        Toast.makeText(changePassword.this, "Password must contain more than 5 characters", Toast.LENGTH_SHORT).show();
                    }
                }
                //If the current password does not equal what the user input for verification then they are alerted and can't update the password
                else {
                    Toast.makeText(changePassword.this, "Incorrect password!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
