package com.leon.counter_reading.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.leon.counter_reading.R;
import com.leon.counter_reading.utils.MyDatabaseClient;

import java.util.ArrayList;

public class ListViewCustomAdapter extends BaseAdapter {

    ArrayList<String> titles;
    ArrayList<Boolean> selected;
    ArrayList<Integer> zoneIds;
    LayoutInflater inflater;
    Context context;

    public ListViewCustomAdapter(Context context, ArrayList<String> titles,
                                 ArrayList<Boolean> selected, ArrayList<Integer> zoneIds) {
        this.zoneIds = zoneIds;
        this.titles = titles;
        this.selected = selected;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckBoxViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_public, null);
        }
        holder = new CheckBoxViewHolder(view);
        holder.checkBox.setText(titles.get(position));
        holder.checkBox.setOnClickListener(view1 -> {
            holder.checkBox.setChecked(!holder.checkBox.isChecked());
            selected.set(position, holder.checkBox.isChecked());
            MyDatabaseClient.getInstance(context).getMyDatabase().
                    readingConfigDefaultDao().updateReadingConfigDefaultByStatus(
                    selected.get(position), zoneIds.get(position));
        });
        holder.checkBox.setChecked(selected.get(position));
        return view;
    }

    class CheckBoxViewHolder {
        public CheckedTextView checkBox;

        public CheckBoxViewHolder(View view) {
            this.checkBox = view.findViewById(android.R.id.text1);
        }
    }
}
