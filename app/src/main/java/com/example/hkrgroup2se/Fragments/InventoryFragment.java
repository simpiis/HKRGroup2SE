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
import android.widget.ListView;

import com.example.hkrgroup2se.R;
import com.example.hkrgroup2se.Skeleton.DBConnect;
import com.example.hkrgroup2se.Skeleton.Grocery;
import com.example.hkrgroup2se.Skeleton.Inventory;
import com.example.hkrgroup2se.Skeleton.InventoryAdapter;
import com.example.hkrgroup2se.Skeleton.Security;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InventoryFragment extends Fragment {

    DBConnect dbConnect = DBConnect.getInstance();
    Inventory inventory = Inventory.getInstance();
    ArrayList<Grocery> groceryList = new ArrayList<>();
    InventoryAdapter arrayAdapter;
    ListView groceryListView;
    Security security = new Security();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FloatingActionButton inventoryBackButton;

    public InventoryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        inventoryBackButton = view.findViewById(R.id.inventoryBackButton);

        groceryListView = view.findViewById(R.id.grocerListView);
        arrayAdapter = new InventoryAdapter(getActivity(), groceryList);
        groceryListView.setAdapter(arrayAdapter);

        //If back button from another fragment, clear list
        if (groceryList.size() > 0) {
            groceryList.clear();
            arrayAdapter.clear();
        }

        String hashedEmail = dbConnect.getCurrentHash();
        databaseReference = database.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = ds.child("Email").getValue().toString();
                    if (email.equals(hashedEmail)) {
                        databaseReference = database.getReference("User/" + ds.getKey() + "/Inventory");
                        databaseReference.orderByChild("bestBefore")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    Grocery grocery = snap.getValue(Grocery.class);
                                    arrayAdapter.insert(grocery, arrayAdapter.getCount());
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
        groceryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                Navigation.findNavController(view).navigate(R.id.action_inventoryFragment_to_myGroceriesFragment, bundle);
            }
        });

        inventoryBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_inventoryFragment_pop);
            }
        });

        return view;
    }
}