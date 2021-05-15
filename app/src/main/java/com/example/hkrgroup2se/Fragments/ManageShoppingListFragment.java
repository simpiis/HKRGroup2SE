package com.example.hkrgroup2se.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;


public class ManageShoppingListFragment extends Fragment {
    String key;

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
        getKey();
        View view = inflater.inflate(R.layout.fragment_manage_shopping_list, container, false);

        addItemButton = view.findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popup
                //Field name, amount, comment

                //read from fields, add item to list and db
            }
        });

        shoppingListView = view.findViewById(R.id.manageListView);
        shoppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //popup change fields update db
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
                        databaseReference = database.getReference("User/" + ds.getKey() + "/ShoppingLists/"+key);

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<ListDate> shoppingLists = new ArrayList<>();
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    ListDate listTitle = snap.getValue(ListDate.class);
                                    System.out.println(listTitle.getDate()+" swag");
                                    shoppingLists.add(listTitle);

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
    public void getKey(){
        if(getArguments()!=null){
            key = getArguments().getString("key");
        }
    }
}