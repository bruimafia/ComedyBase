package com.example.gukov.comedybase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    // вставка записи в таблицу БД
    public void insertData(String comedy, String director, int year, int number){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO COMEDIES VALUES (NULL, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, comedy);
        statement.bindString(2, director);
        statement.bindDouble(3, (double)year);
        statement.bindDouble(4, (double)number);

        statement.executeInsert();
    }

    // обновление записи в БД
    public void updateData(String comedy, String director, int year, int number, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE COMEDIES SET comedy = ?, director = ?, year = ?, number = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, comedy);
        statement.bindString(2, director);
        statement.bindDouble(3, (double)year);
        statement.bindDouble(4, (double)number);
        statement.bindDouble(5, (double)id);

        statement.execute();
        database.close();
    }

    // удаление записи из БД
    public  void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM COMEDIES WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

}