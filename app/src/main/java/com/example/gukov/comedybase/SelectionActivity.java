package com.example.gukov.comedybase;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.gukov.comedybase.AddComedyActivity.sqLiteHelper;

public class SelectionActivity extends Activity {

    GridView gridView;
    ArrayList<Comedy> list;
    GridAdapter adapter = null;
    int taskKey; // передаваемый ключ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        // получаем посланный ключ
        taskKey = getIntent().getExtras().getInt("key");

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new GridAdapter(this, R.layout.item, list);
        gridView.setAdapter(adapter);

        // формируем запросы в зависимости от задания
        switch (taskKey) {
            case 1:
                request("SELECT * FROM COMEDIES ORDER BY year");
                break;
            case 2:
                request("SELECT * FROM COMEDIES WHERE director = 'Данелия' OR director = 'Гайдай' OR director = 'Рязанов' ORDER BY director");
                break;
            case 3:
                request("SELECT * FROM COMEDIES WHERE number = 2 AND year < 1978");
                break;
            case 4:
                request("SELECT * FROM COMEDIES ORDER BY comedy");
                break;
            default:
                break;
        }

    }

    // выполнение запроса
    public void request(String sql) {
        Cursor cursor = sqLiteHelper.getData(sql);
        list.clear();

        if (cursor.getCount() == 0)
            Toast.makeText(this, "По запросу ничего не найдено", Toast.LENGTH_SHORT).show();

        // ищем и выводим нужные записи
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String comedy = cursor.getString(1);
            String director = cursor.getString(2);
            int year = cursor.getInt(3);
            int number = cursor.getInt(4);

            list.add(new Comedy(id, year, number, comedy, director));
        }
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Запрос успешно выполнен",Toast.LENGTH_SHORT).show();
    }

}
