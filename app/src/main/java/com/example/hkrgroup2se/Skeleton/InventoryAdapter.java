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

public class InventoryAdapter extends ArrayAdapter<Grocery> {
    private ArrayList<Grocery> arrayList;
    private Activity context;

    public InventoryAdapter(Activity context, ArrayList<Grocery> arrayList) {
        super(context, R.layout.listitem_inventory_adapter, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.listitem_inventory_adapter, null, true);
        TextView nameTextView = listViewItem.findViewById(R.id.inventoryItemNameTextView);
        TextView bestBeforeTextView = listViewItem.findViewById(R.id.inventoryItemBestBeforeTextView);
        ImageView iconImageView = listViewItem.findViewById(R.id.inventoryItemImageView);

        nameTextView.setText(arrayList.get(position).getBrand() + " " + arrayList.get(position).getName());
        bestBeforeTextView.setText("Best before: " + arrayList.get(position).getBestBefore());
        iconImageView.setImageResource(R.drawable.ic_baseline_food_bank_48);

        return listViewItem;
    }
}
