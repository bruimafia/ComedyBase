package com.example.gukov.comedybase;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.gukov.comedybase.AddComedyActivity.sqLiteHelper;

public class MainActivity extends Activity implements View.OnClickListener {

    GridView gridView; // гридвью
    ArrayList<Comedy> list; // массив
    GridAdapter adapter = null; // объявляем адаптер
    ImageButton btnAdd, btnSearch, btnFilters; // кнопки

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // создание/обновление БД и таблицы
        sqLiteHelper = new SQLiteHelper(this, "ComediesDB.sqlite", null, 3);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS COMEDIES(id INTEGER PRIMARY KEY AUTOINCREMENT, comedy VARCHAR, director VARCHAR, year INTEGER, number INTEGER)");

        // находим элементы
        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnFilters = (ImageButton) findViewById(R.id.btnFilters);

        // устанавливаем слушатели
        btnAdd.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnFilters.setOnClickListener(this);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new GridAdapter(this, R.layout.item, list);
        gridView.setAdapter(adapter);

        // ищем и выводим все записи
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM COMEDIES");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String comedy = cursor.getString(1);
            String director = cursor.getString(2);
            int year = cursor.getInt(3);
            int number = cursor.getInt(4);

            list.add(new Comedy(id, year, number, comedy, director));
        }
        adapter.notifyDataSetChanged();

        // выводим id записи выбранного элемента gridview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Cursor c = sqLiteHelper.getData("SELECT id FROM COMEDIES");
                ArrayList<Integer> arrID = new ArrayList<Integer>();
                while (c.moveToNext()){
                    arrID.add(c.getInt(0));
                }
                Toast.makeText(MainActivity.this, "id записи: " + arrID.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        // диалог по долгому нажатию на элемент gridview
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Обновить запись", "Удалить запись"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                dialog.setTitle("Выберите действие:");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // обновление записи
                            Cursor c = sqLiteHelper.getData("SELECT * FROM COMEDIES");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogUpdate(MainActivity.this, arrID.get(position));

                        } else {
                            // удаление записи
                            Cursor c = sqLiteHelper.getData("SELECT id FROM COMEDIES");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });

    }

    // действия по нажатию кнопок
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnAdd: // кнопка "добавить запись"
                intent = new Intent(this, AddComedyActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSearch: // кнопка "динамический поиск"
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.btnFilters: // кнопка "индивидуальные фильтры"
                intent = new Intent(this, FiltersActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    // обновление записи
    private void showDialogUpdate(Activity activity, final int position) {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_comedy_activity);

        // находим элементы
        final EditText etComedy = (EditText) dialog.findViewById(R.id.etComedy);
        final EditText etDirector = (EditText) dialog.findViewById(R.id.etDirector);
        final EditText etYear = (EditText) dialog.findViewById(R.id.etYear);
        final EditText etNumber = (EditText) dialog.findViewById(R.id.etNumber);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);

        // ширина диалогового окна обновления
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // высота диалогового окна обновления
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        // выводим актуальную информацию о записи
        Cursor cc = sqLiteHelper.getData("SELECT * FROM COMEDIES WHERE id = " + position);
        while (cc.moveToNext()) {
            etComedy.setText(cc.getString(1));
            etDirector.setText(cc.getString(2));
            etYear.setText(cc.getInt(3) + "");
            etNumber.setText(cc.getInt(4) + "");
        }

        // нажатие кнопки "обновить"
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sqLiteHelper.updateData(
                            etComedy.getText().toString().trim(),
                            etDirector.getText().toString().trim(),
                            Integer.parseInt(etYear.getText().toString().trim()),
                            Integer.parseInt(etNumber.getText().toString().trim()),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Запись успешна обновлена",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Ошибка обновления", error.getMessage());
                }
                updateFoodList();
            }
        });
    }

    // удаление записи
    private void showDialogDelete(final int idComedy) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(MainActivity.this);

        // диалоговое окно удаления
        dialogDelete.setTitle("Внимание!");
        dialogDelete.setMessage("Вы уверены, что хотите удалить запись?");
        dialogDelete.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    sqLiteHelper.deleteData(idComedy);
                    Toast.makeText(MainActivity.this, "Запись успешно удалена",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("Ошибка удаления", e.getMessage());
                }
                updateFoodList();
            }
        });

        dialogDelete.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    // обновляем все записи на главном экране
    private void updateFoodList() {
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM COMEDIES");
        list.clear();
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

    // Переназначение кнопки "назад"
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

}