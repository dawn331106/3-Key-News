package com.example.a3keynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sign_up extends AppCompatActivity {
    EditText username,email,password,retpassword;
    Button signup;
    AdminDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username=(EditText) findViewById(R.id.textNewUsername);
        email=(EditText) findViewById(R.id.textNewEmail);
        password=(EditText) findViewById(R.id.textNewPassword);
        retpassword=(EditText)findViewById(R.id.textRetypePassword);
        signup=(Button) findViewById(R.id.newsignupButton);
        db=new AdminDB(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();
                String em=email.getText().toString();
                String pass=password.getText().toString();
                String retpass=retpassword.getText().toString();
                if(user.equals("")||pass.equals("")||retpass.equals(""))
                    Toast.makeText(Sign_up.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                else
                {
                    if(pass.equals(retpass))
                    {
                        Boolean check=db.checkUsername(user);
                        if(check==false)
                        {
                            Boolean insert=db.insertdata(user,em,pass);
                            if(insert==true) {
                                Toast.makeText(Sign_up.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                            else Toast.makeText(Sign_up.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(Sign_up.this, "User already exists!", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(Sign_up.this, "Passwords are not matching!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}