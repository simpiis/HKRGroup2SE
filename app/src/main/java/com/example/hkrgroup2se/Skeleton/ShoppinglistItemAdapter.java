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

public class ShoppinglistItemAdapter extends ArrayAdapter<ShoppingListItem> {
    private ArrayList<ShoppingListItem> arrayList;
    private Activity context;

    public ShoppinglistItemAdapter(Activity context, ArrayList<ShoppingListItem> arrayList) {
        super(context, R.layout.shoppinglist_item_adapter, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.shoppinglist_item_adapter, null, true);
        TextView nameTextView = listViewItem.findViewById(R.id.shoppinglistItemName);
        TextView itemComment = listViewItem.findViewById(R.id.shoppinglistItemComment);
        ImageView iconImageView = listViewItem.findViewById(R.id.manageShoppinglistImageView);

        nameTextView.setText(arrayList.get(position).getItemName() + "    " + arrayList.get(position).getItemAmount());
        itemComment.setText(arrayList.get(position).getItemComment());
        iconImageView.setImageResource(R.drawable.ic_baseline_format_list_bulleted_24);

        return listViewItem;
    }
}