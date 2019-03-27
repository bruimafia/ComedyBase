package com.example.gukov.comedybase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Comedy> comediesList;

    public GridAdapter(Context context, int layout, ArrayList<Comedy> comediesList) {
        this.context = context;
        this.layout = layout;
        this.comediesList = comediesList;
    }

    @Override
    public int getCount() {
        return comediesList.size();
    }

    @Override
    public Object getItem(int position) {
        return comediesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView tvComedy, tvDirector, tvYear, tvNumber;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            // находим элементы
            holder.tvComedy = (TextView) row.findViewById(R.id.tvComedy);
            holder.tvDirector = (TextView) row.findViewById(R.id.tvDirector);
            holder.tvYear = (TextView) row.findViewById(R.id.tvYear);
            holder.tvNumber = (TextView) row.findViewById(R.id.tvNumber);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Comedy comedy = comediesList.get(position);

        // выводим информацию
        holder.tvComedy.setText(comedy.getComedy());
        holder.tvDirector.setText(comedy.getDirector());
        holder.tvYear.setText(comedy.getYear() + "");
        holder.tvNumber.setText(comedy.getNumber() + "");

        return row;
    }

}