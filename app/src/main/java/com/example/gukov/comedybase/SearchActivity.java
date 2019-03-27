package com.example.gukov.comedybase;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.gukov.comedybase.AddComedyActivity.sqLiteHelper;

public class SearchActivity extends Activity {

    GridView gridView;
    ArrayList<Comedy> list;
    GridAdapter adapter = null;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // находим элементы
        etSearch = (EditText) findViewById(R.id.etSearch);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new GridAdapter(this, R.layout.item, list);
        gridView.setAdapter(adapter);

        // отлавливаем изменение текста в строке поиска
        etSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            // при изменении текста выполняем запрос
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                request("SELECT * FROM COMEDIES WHERE comedy LIKE '%" + s.toString() + "%' OR director LIKE '%" + s.toString() + "%' OR year LIKE '%" + s.toString() + "%'");
            }

        });

    }

    // выполнение запроса
    public void request(String sql) {
        Cursor cursor = sqLiteHelper.getData(sql);
        list.clear();

        if (cursor.getCount() == 0)
            Toast.makeText(this, "По запросу ничего не найдено", Toast.LENGTH_SHORT).show();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String comedy = cursor.getString(1);
            String director = cursor.getString(2);
            int year = cursor.getInt(3);
            int number = cursor.getInt(4);

            list.add(new Comedy(id, year, number, comedy, director));
        }
        adapter.notifyDataSetChanged();
    }

}