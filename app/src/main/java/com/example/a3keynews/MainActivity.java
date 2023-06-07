package com.example.a3keynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
// The initial page when the app is opened and requests the user to put username and password.
public class MainActivity extends AppCompatActivity {
    EditText username,password;
    Button button,login;
    AdminDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new AdminDB(this);
        username=(EditText) findViewById(R.id.textUsername);
        password=(EditText) findViewById(R.id.textPassword);
        login=(Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user = username.getText().toString();
                    String pass = password.getText().toString();
                    // If a user doesn't provide his username or password it will tell the user to enter the required fields.
                    if (user.equals("") || pass.equals(""))
                        Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    else {
                        Boolean check = db.checkUsernameAndPassword(user, pass);
                        if (check == true) {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            // It checks if a user has already entered the keywords, if not the keywords page will be opened asking for the keywords.
                            // If it finds the user in Table, the news article page will be opened
                            Boolean userCheck = db.checkUserInTable(user);
                            if (userCheck == true) {
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                intent.putExtra("the_user", user);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(MainActivity.this, KeyWords.class);
                                intent.putExtra("cur_user", user);
                                startActivity(intent);
                            }
                        } else
                            Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            button = (Button) findViewById(R.id.signupButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Sign_up.class);
                    startActivity(intent);
                }
            });
    }
}