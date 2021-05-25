package com.example.hkrgroup2se.Skeleton;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hkrgroup2se.R;


import java.util.ArrayList;

public class ShoppinglistAdapter extends ArrayAdapter<ListDate> {
    private ArrayList<ListDate> arrayList;
    private Activity context;

    public ShoppinglistAdapter(Activity context, ArrayList<ListDate> arrayList) {
        super(context, R.layout.shoppinglist_adapter, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.shoppinglist_adapter, null, true);
        TextView nameTextView = listViewItem.findViewById(R.id.shoppinglistListName);
        ImageView iconImageView = listViewItem.findViewById(R.id.shoppinglistImageView);

        nameTextView.setText(arrayList.get(position).getDate());
        iconImageView.setImageResource(R.drawable.ic_baseline_format_list_bulleted_24);

        return listViewItem;
    }
}