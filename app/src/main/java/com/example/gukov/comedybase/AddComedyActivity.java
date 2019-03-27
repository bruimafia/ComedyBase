package com.example.gukov.comedybase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddComedyActivity extends Activity implements View.OnClickListener {

    EditText etComedy, etDirector, etYear, etNumber; // вводимый текст
    Button btnAdd, btnClear; // кнопки

    public static SQLiteHelper sqLiteHelper; // объявляем sqlitehelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comedy);

        // находим элементы
        etComedy = (EditText) findViewById(R.id.etComedy);
        etDirector = (EditText) findViewById(R.id.etDirector);
        etYear = (EditText) findViewById(R.id.etYear);
        etNumber = (EditText) findViewById(R.id.etNumber);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnClear = (Button) findViewById(R.id.btnClear);

        // устанавливаем слушатели
        btnAdd.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    // действия по нажатию кнопок
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd: // кнопка "добавить запись"
                try{
                    sqLiteHelper.insertData(
                            etComedy.getText().toString().trim(),
                            etDirector.getText().toString().trim(),
                            Integer.parseInt(etYear.getText().toString().trim()),
                            Integer.parseInt(etNumber.getText().toString().trim())
                    );
                    Toast.makeText(getApplicationContext(), "Запись успешно добавлена", Toast.LENGTH_SHORT).show();
                    // переходим на главный экран
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Ошибка добавления записи", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnClear: // кнопка "очистить"
                etComedy.setText("");
                etDirector.setText("");
                etYear.setText("");
                etNumber.setText("");
                Toast.makeText(getApplicationContext(), "Поля очищены", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

}