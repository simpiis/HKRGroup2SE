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
import android.widget.ListView;

import com.example.hkrgroup2se.R;
import com.example.hkrgroup2se.Skeleton.DBConnect;
import com.example.hkrgroup2se.Skeleton.Grocery;
import com.example.hkrgroup2se.Skeleton.Inventory;
import com.example.hkrgroup2se.Skeleton.Security;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class InventoryFragment extends Fragment {

    DBConnect dbConnect = DBConnect.getInstance();
    Inventory inventory = Inventory.getInstance();
    ArrayList<String> groceryList = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ListView groceryListView;
    Security security = new Security();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

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

        groceryListView = view.findViewById(R.id.grocerListView);
        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, groceryList);
        groceryListView.setAdapter(arrayAdapter);

        String hashedEmail = dbConnect.getCurrentHash();
        databaseReference = database.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = ds.child("Email").getValue().toString();
                    if (email.equals(hashedEmail)) {
                        databaseReference = database.getReference("User/" + ds.getKey() + "/Inventory");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<Grocery> groceries = new ArrayList<>();
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    Grocery grocery = snap.getValue(Grocery.class);
                                    groceries.add(grocery);
                                }
                                for (Grocery g : groceries) {
                                    String show = g.getName() + ", " + g.getBrand() + ", " + g.getBestBefore();
                                    arrayAdapter.insert(show, arrayAdapter.getCount());
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
        return view;
    }
}