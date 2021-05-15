package com.example.hkrgroup2se.Fragments;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class ShoppingListFragment extends Fragment {

    DBConnect dbConnect = DBConnect.getInstance();
    ListView shoppingListView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    ArrayAdapter arrayAdapter;
    ArrayList<ShoppingListItem> list = new ArrayList<>();
    Button newListButton;

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
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        newListButton = view.findViewById(R.id.newListButton);
        newListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbConnect.setShoppingListTitle();
            }
        });

        shoppingListView = view.findViewById(R.id.shoppingListView);
        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        shoppingListView.setAdapter(arrayAdapter);
        shoppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("key", keys.get(position));
                Navigation.findNavController(view).navigate(R.id.action_shoppingListFragment_to_manageShoppingListFragment,bundle);
            }
        });


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
                                    System.out.println(listTitle.getDate()+" swag");
                                    shoppingLists.add(listTitle);
                                    keys.add(snap.getKey());

                                }

                                for (ListDate g : shoppingLists) {
                                    arrayAdapter.insert(g.getDate(), arrayAdapter.getCount());
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
}