package com.example.a3keynews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.a3keynews.parameter.Articles;

// This class handles the database for storing and retrieving user data and news data of a user.
public class AdminDB extends SQLiteOpenHelper {
    public static final String dbname="Login.db";
    public AdminDB(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table users(username TEXT primary key,email TEXT,password TEXT)");
        db.execSQL("create Table keywords(username TEXT primary key,keyword1 TEXT,keyword2 TEXT,keyword3 TEXT)");
        db.execSQL("create table articleData(username TEXT,id TEXT,url TEXT,title TEXT,published TEXT,urlToImage TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
        db.execSQL("drop Table if exists keywords");
        db.execSQL("drop Table if exists articleData");
    }

    // For storing user information -> username,email and password
    public boolean insertData(String username,String email,String password){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("username",username);
        content.put("email",email);
        content.put("password",password);
        long result=db.insert("users",null,content);
        if(result==-1) return false;
        else
            return true;
    }
    // For storing the keywords of a user
    public boolean insertTable2(String username,String keyword1,String keyword2,String keyword3)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.delete("keywords","username=?",new String[]{username});
        ContentValues content=new ContentValues();
        content.put("username",username);
        content.put("keyword1",keyword1);
        content.put("keyword2",keyword2);
        content.put("keyword3",keyword3);
        long result=db.insert("keywords",null,content);
        if(result==-1) return false;
        else
            return true;
    }
    // For storing news information for a particular user
    public void insertDataTable(String username,String id,String url,String title,String published,String urlToImage)
    {
        SQLiteDatabase db=getWritableDatabase();
        if(id.equals("1"))
        {
           int delNum= db.delete("articleData","username=?",new String[]{username});
        }
        ContentValues content=new ContentValues();
        content.put("username",username);
        content.put("id",id);
        content.put("url",url);
        content.put("title",title);
        content.put("published",published);
        content.put("urlToImage",urlToImage);
        db.insert("articleData",null,content);

    }
    // To find the number of news articles of a user stored in database
    public int rowNum(String username)
    {
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("select count(*) from articleData where username =?",new String[]{username});
        String res="";
        if(cursor.moveToFirst())
        {
            res=cursor.getString(0);
        }
        cursor.close();
        return Integer.valueOf(res);
    }
    // To verify if a user is registered or not
    public boolean checkUsername(String username){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from users where username =?",new String []{username});
        if(cursor.getCount()>0) return true;
        else return false;
    }
    // To verify both username and password
    public boolean checkUsernameAndPassword(String username,String password){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from users where username =? and password=?",new String []{username,password});
        if(cursor.getCount()>0) return true;
        else return false;
    }
    // To check if a user has already entered the keywords
    public boolean checkUserInTable(String username)
    {
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from keywords where username =?",new String []{username});
        if(cursor.getCount()>0) return true;
        else return false;
    }
    // To get the stored news data of a user
    public String getData(String username, String text,String id)
    {
        SQLiteDatabase db=getWritableDatabase();
        String result="";
        Cursor cursor=db.rawQuery("select "+text+" from articleData where username =? and id =?",new String[]{username,id});
        if(cursor.moveToFirst())
        {
            result=cursor.getString(0);
        }
        cursor.close();
        return result;
    }
    // To retrieve the keywords of a user
    public String getKeyWord(String username,int ind)
    {
        SQLiteDatabase db=getWritableDatabase();
        String result="";
        Cursor cursor=db.rawQuery("select * from keywords where username =?",new String[]{username});
        if(cursor.moveToFirst())
        {
            result=cursor.getString(ind);
        }
        cursor.close();
        return result;
    }
}
