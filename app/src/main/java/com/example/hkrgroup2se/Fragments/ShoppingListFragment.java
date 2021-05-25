package com.example.hkrgroup2se.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.hkrgroup2se.R;
import com.example.hkrgroup2se.Skeleton.DBConnect;
import com.example.hkrgroup2se.Skeleton.ListDate;
import com.example.hkrgroup2se.Skeleton.ShoppingListItem;
import com.example.hkrgroup2se.Skeleton.ShoppinglistAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class ShoppingListFragment extends Fragment {

    DBConnect dbConnect = DBConnect.getInstance();
    ListView shoppingListView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    ShoppinglistAdapter arrayAdapter;
    ArrayList<ListDate> list = new ArrayList<>();
    FloatingActionButton newListButton;
    int removePosition;
    FloatingActionButton backButtonShoppinglist;

    ArrayList<String> keys = new ArrayList<>();

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AlertDialog.Builder removeAlert = new AlertDialog.Builder(getContext());
        removeAlert.setTitle("Remove shopping list?");
        removeAlert.setPositiveButton("Remove List", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbConnect.removeShoppingList(keys.get(removePosition));
                reloadData();

            }
        });
        removeAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing. this just adds a cancel button in case the user wants to cancel or not click outside the box to close it down
            }
        });

        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        backButtonShoppinglist = view.findViewById(R.id.backButtonShoppinglist);
        backButtonShoppinglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_shoppingListFragment_to_appFragment_pop);
            }
        });

        newListButton = view.findViewById(R.id.newListButton);
        newListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbConnect.setShoppingListTitle();
                reloadData();
            }
        });

        shoppingListView = view.findViewById(R.id.shoppingListView);
        arrayAdapter = new ShoppinglistAdapter(getActivity(), list);
        shoppingListView.setAdapter(arrayAdapter);
        shoppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("key", keys.get(position));
                Navigation.findNavController(view).navigate(R.id.action_shoppingListFragment_to_manageShoppingListFragment,bundle);
            }
        });
        shoppingListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removePosition=position;
                removeAlert.show();
                return true;
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
                        databaseReference = database.getReference("User/" + ds.getKey() + "/ShoppingLists");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<ListDate> shoppingLists = new ArrayList<>();
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    ListDate listTitle = snap.getValue(ListDate.class);
                                    shoppingLists.add(listTitle);
                                    keys.add(snap.getKey());

                                }
                                Collections.reverse(shoppingLists);
                                Collections.reverse(keys);
                                for (ListDate g : shoppingLists) {
                                    arrayAdapter.insert(g, arrayAdapter.getCount());
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
    void reloadData(){
        arrayAdapter.clear();
        String hashedEmail = dbConnect.getCurrentHash();
        databaseReference = database.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = ds.child("Email").getValue().toString();
                    if (email.equals(hashedEmail)) {
                        databaseReference = database.getReference("User/" + ds.getKey() + "/ShoppingLists");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<ListDate> shoppingLists = new ArrayList<>();
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    ListDate listTitle = snap.getValue(ListDate.class);
                                    shoppingLists.add(listTitle);
                                    keys.add(snap.getKey());

                                }
                                Collections.reverse(shoppingLists);
                                Collections.reverse(keys);
                                for (ListDate g : shoppingLists) {
                                    arrayAdapter.insert(g, arrayAdapter.getCount());
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