package com.example.gukov.comedybase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FiltersActivity extends Activity implements View.OnClickListener {

    Button btnTask1, btnTask2, btnTask3, btnTask4; // кнопки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        // находим элементы
        btnTask1 = (Button) findViewById(R.id.btnTask1);
        btnTask2 = (Button) findViewById(R.id.btnTask2);
        btnTask3 = (Button) findViewById(R.id.btnTask3);
        btnTask4 = (Button) findViewById(R.id.btnTask4);

        // устанавливаем слушатели
        btnTask1.setOnClickListener(this);
        btnTask2.setOnClickListener(this);
        btnTask3.setOnClickListener(this);
        btnTask4.setOnClickListener(this);
    }

    // действия по нажатию кнопок
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnTask1: // первое задание
                intent = new Intent(this, SelectionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("key", 1);
                startActivity(intent);
                break;
            case R.id.btnTask2: // второе задание
                intent = new Intent(this, SelectionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("key", 2);
                startActivity(intent);
                break;
            case R.id.btnTask3: // третье задание
                intent = new Intent(this, SelectionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("key", 3);
                startActivity(intent);
                break;
            case R.id.btnTask4: // четвертое задание
                intent = new Intent(this, SelectionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("key", 4);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}