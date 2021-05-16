package com.example.hkrgroup2se.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hkrgroup2se.R;
import com.example.hkrgroup2se.Skeleton.DBConnect;
import com.example.hkrgroup2se.Skeleton.ListDate;
import com.example.hkrgroup2se.Skeleton.ShoppingListItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ManageShoppingListFragment extends Fragment {
    String key;
    String key1;
    String key2;
    ArrayList<String> keylist = new ArrayList<>();
    ArrayList<ShoppingListItem> list = new ArrayList<>();
    ArrayList<ShoppingListItem> global = new ArrayList<>();
    int globalpos;

    DBConnect dbConnect = DBConnect.getInstance();
    ListView shoppingListView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    ArrayAdapter arrayAdapter;
    Button addItemButton;


    public ManageShoppingListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        // for add item popup
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Add item");
        EditText itemName = new EditText(getContext());
        itemName.setInputType(InputType.TYPE_CLASS_TEXT);
        itemName.setHint("Item name");
        EditText itemAmount = new EditText(getContext());
        itemAmount.setInputType(InputType.TYPE_CLASS_TEXT);
        itemAmount.setHint("Amount. Pieces gram etc.");
        EditText itemComment = new EditText(getContext());
        itemComment.setInputType(InputType.TYPE_CLASS_TEXT);
        itemComment.setHint("Item comment");

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(itemName);
        layout.addView(itemAmount);
        layout.addView(itemComment);
        alert.setView(layout);


        AlertDialog.Builder alert2 = new AlertDialog.Builder(getContext());
        alert2.setTitle("Modify item");
        EditText itemNameMod = new EditText(getContext());
        itemNameMod.setInputType(InputType.TYPE_CLASS_TEXT);
        itemNameMod.setHint("Item name");
        EditText itemAmountMod = new EditText(getContext());
        itemAmountMod.setInputType(InputType.TYPE_CLASS_TEXT);
        itemAmountMod.setHint("Amount. Pieces gram etc.");
        EditText itemCommentMod = new EditText(getContext());
        itemCommentMod.setInputType(InputType.TYPE_CLASS_TEXT);
        itemCommentMod.setHint("Item comment");
        LinearLayout layout2 = new LinearLayout(getContext());
        layout2.setOrientation(LinearLayout.VERTICAL);
        layout2.addView(itemNameMod);
        layout2.addView(itemAmountMod);
        layout2.addView(itemCommentMod);


        alert2.setPositiveButton("Save changes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dbConnect.modifyShoppingListItem(key,keylist.get(globalpos),itemNameMod.getText().toString(),itemAmountMod.getText().toString(),itemCommentMod.getText().toString());
                reloadItems();
                ((ViewGroup)layout2.getParent()).removeView(layout2);

            }
        });
        alert2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ((ViewGroup)layout2.getParent()).removeView(layout2);
            }
        });


        alert.setPositiveButton("Add item", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbConnect.addToShoppingList(key, itemName.getText().toString(), itemAmount.getText().toString(), itemComment.getText().toString());
                Toast.makeText(getContext(), "Added Item (hopefully)", Toast.LENGTH_LONG).show();
                itemName.getText().clear();
                itemAmount.getText().clear();
                itemComment.getText().clear();
                reloadItems();
                ((ViewGroup)layout.getParent()).removeView(layout);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ((ViewGroup)layout.getParent()).removeView(layout);
            }
        });
        // end of add item popup


        getKey();
        View view = inflater.inflate(R.layout.fragment_manage_shopping_list, container, false);

        addItemButton = view.findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.setView(layout);
                alert.show();


            }
        });

        shoppingListView = view.findViewById(R.id.manageListView);
        shoppingListView.setAdapter(arrayAdapter);
        shoppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                globalpos=position;
                ShoppingListItem i = global.get(position);
                itemNameMod.setText(i.getItemName());
                itemAmountMod.setText(i.getItemAmount());
                itemCommentMod.setText(i.getItemComment());
                alert2.setView(layout2);
                alert2.show();

            }
        });


        String hashedEmail = dbConnect.getCurrentHash();
        databaseReference = database.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(arrayAdapter!=null) {
                    arrayAdapter.clear();
                }
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = ds.child("Email").getValue().toString();
                    if (email.equals(hashedEmail)) {
                        databaseReference = database.getReference("User/" + ds.getKey() + "/ShoppingLists/" + key + "/Items/");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<ShoppingListItem> shoppingLists = new ArrayList<>();
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    ShoppingListItem listItem = snap.getValue(ShoppingListItem.class);
                                    shoppingLists.add(listItem);
                                    global.add(listItem);
                                    keylist.add(snap.getKey());

                                }

                                for (ShoppingListItem g : shoppingLists) {
                                    arrayAdapter.insert(g.getItemName() + ",  " + g.getItemAmount() + ", " + g.getItemComment(), arrayAdapter.getCount());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return view;
    }

    public void getKey() {
        if (getArguments() != null) {
            key = getArguments().getString("key");
        }
    }

    void reloadItems(){
        String hashedEmail = dbConnect.getCurrentHash();
        databaseReference = database.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(arrayAdapter!=null) {
                    arrayAdapter.clear();
                }
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = ds.child("Email").getValue().toString();
                    if (email.equals(hashedEmail)) {
                        databaseReference = database.getReference("User/" + ds.getKey() + "/ShoppingLists/" + key + "/Items/");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<ShoppingListItem> shoppingLists = new ArrayList<>();
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    ShoppingListItem listItem = snap.getValue(ShoppingListItem.class);
                                    shoppingLists.add(listItem);
                                    global.add(listItem);
                                    keylist.add(snap.getKey());

                                }

                                for (ShoppingListItem g : shoppingLists) {
                                    arrayAdapter.insert(g.getItemName() + ",  " + g.getItemAmount() + ", " + g.getItemComment(), arrayAdapter.getCount());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}