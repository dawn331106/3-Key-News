package com.example.a3keynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KeyWords extends AppCompatActivity {
    EditText keyword1,keyword2,keyword3;
    Button Done;
    AdminDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_words);
        keyword1=findViewById(R.id.keyword1);
        keyword2=findViewById(R.id.keyword2);
        keyword3=findViewById(R.id.keyword3);
        Done=findViewById(R.id.doneButton);
        db=new AdminDB(this);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key1=keyword1.getText().toString();
                String key2=keyword2.getText().toString();
                String key3=keyword3.getText().toString();
                Intent intent=getIntent();
                String user=intent.getStringExtra("cur_user");
                Boolean insert=db.inserttable2(user,key1,key2,key3);
                if(insert==true) {
                    Toast.makeText(KeyWords.this, "Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(getApplicationContext(),HomeActivity.class);
                    intent1.putExtra("the_user",user);
                    startActivity(intent1);
                }
                else Toast.makeText(KeyWords.this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}