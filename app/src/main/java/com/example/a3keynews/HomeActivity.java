package com.example.a3keynews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.a3keynews.parameter.Articles;
import com.example.a3keynews.parameter.Headlines;
import com.example.a3keynews.parameter.Source;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter1 adapter;
    final String API_KEY="84ae27d2946c49498b5f425dedf41ee5";
    Button refreshButton;
    List<Articles> articles=new ArrayList<>();
    List<String>url=new ArrayList<>();
    List<String>publised=new ArrayList<>();
    List<String>title=new ArrayList<>();
    List<String>urltoimage=new ArrayList<>();
    String _url;
    String _title;
    String _published;
    String _urlToImage;
    AdminDB db;
    String user;
    int cnt=1;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar=findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        db=new AdminDB(this);
        Intent intent=getIntent();
        recyclerView=findViewById(R.id.recycler);
        refreshButton=findViewById(R.id.refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        user=intent.getStringExtra("the_user");
        String key1=db.getKeyWord(user,1);
        String key2=db.getKeyWord(user,2);
        String key3=db.getKeyWord(user,3);
        fetchJSON(key1,API_KEY);
        fetchJSON(key2,API_KEY);
        fetchJSON(key3,API_KEY);
    }

    private void fetchJSON(String qInTitle, String api_key) {
        Call<Headlines> call=Client.getInstance().getApi().getHeadlines(qInTitle,API_KEY);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful()&&response.body().getArticles()!=null)
                {
                    articles.clear();
                    articles=response.body().getArticles();
                    if(articles.size()>=3)
                    {
                        for(int i=0;i<=2;i++)
                        {
                            _url=articles.get(i).getUrl();
                            _title=articles.get(i).getTitle();
                            _published=articles.get(i).getPublishedAt();
                            _urlToImage=articles.get(i).getUrlToImage();
                            db.insertDataTable(user,String.valueOf(cnt),_url,_title,_published,_urlToImage);
                            cnt++;
                            url.add(_url);
                            title.add(_title);
                            publised.add(_published);
                            urltoimage.add(_urlToImage);
                        }
                    }
                    else
                    {
                        if(articles.size()>0)
                        {
                            for(int i=0;i<articles.size();i++)
                            {
                                _url=articles.get(i).getUrl();
                                _title=articles.get(i).getTitle();
                                _published=articles.get(i).getPublishedAt();
                                _urlToImage=articles.get(i).getUrlToImage();
                                url.add(_url);
                                title.add(_title);
                                publised.add(_published);
                                urltoimage.add(_urlToImage);
                            }
                        }
                    }
                    adapter=new Adapter1(HomeActivity.this,url,title,publised,urltoimage);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                  recyclerView.setAdapter(adapter);
                  if(flag==0)
                  {
                       int row=db.rowNum(user);
                       for(int i=1;i<=row;i++)
                       {
                           url.add(db.getdata(user,"url",String.valueOf(i)));
                           title.add(db.getdata(user,"title",String.valueOf(i)));
                           publised.add(db.getdata(user,"published",String.valueOf(i)));
                           urltoimage.add(db.getdata(user,"urlToImage",String.valueOf(i)));
                       }
                      adapter=new Adapter1(HomeActivity.this,url,title,publised,urltoimage);
                      recyclerView.setAdapter(adapter);
                       flag=1;
                  }
                  Toast.makeText(HomeActivity.this,"Check yout internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.action_menu,menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.keychange:
                Intent intent1 = new Intent(HomeActivity.this, KeyWords.class);
                intent1.putExtra("cur_user",user);
                startActivity(intent1);
                return true;
            case R.id.logout:
                Intent intent2 = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}