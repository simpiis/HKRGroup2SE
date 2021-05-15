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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hkrgroup2se.R;
import com.example.hkrgroup2se.Skeleton.DBConnect;
import com.example.hkrgroup2se.Skeleton.Grocery;
import com.example.hkrgroup2se.Skeleton.Waste;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MyGroceriesFragment extends Fragment {

    int pos;
    DBConnect dbConnect = DBConnect.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    TextView nameOfGrocery, priceOfGrocery, amountValue;
    Button consumeButton, wasteButton, freezeButton;
    ArrayList<Grocery> arrayList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_groceries, container, false);



        //TEXTVIEW
        nameOfGrocery = view.findViewById(R.id.nameOfGrocery);
        priceOfGrocery = view.findViewById(R.id.priceOfGrocery);
        amountValue = view.findViewById(R.id.amountValue);


        //BUTTONS
        consumeButton = view.findViewById(R.id.consumedButton);
        wasteButton = view.findViewById(R.id.trashButton);
        freezeButton = view.findViewById(R.id.freezeButton);

        //GETS VALUE OF POS
        getPos();

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
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    Grocery grocery = snap.getValue(Grocery.class);
                                    arrayList.add(grocery);
                                }

                                nameOfGrocery.setText(arrayList.get(pos).getName());
                                priceOfGrocery.setText(String.valueOf(arrayList.get(pos).getPrize()));
                                amountValue.setText(arrayList.get(pos).getAmount() + " " + arrayList.get(pos).getTypeOfAmount() );
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


        consumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Grocery grocery = new Grocery(arrayList.get(pos).getName(), arrayList.get(pos).getBrand(), arrayList.get(pos).getPrize(),
                        arrayList.get(pos).getTypeOfAmount(), arrayList.get(pos).getAmount(), arrayList.get(pos).getTypeOfGrocery(), arrayList.get(pos).getBestBefore());

                databaseReference = database.getReference("User");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                            String email = ds.child("Email").getValue().toString();
                            if (email.equals(hashedEmail)){
                                databaseReference = database.getReference("User/" + ds.getKey() + "/Inventory");
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            Grocery grocery1 = ds.getValue(Grocery.class);
                                            if (grocery.getName().equals(grocery1.getName()) && grocery.getBrand().equals(grocery1.getBrand()) && grocery.getPrize() == grocery1.getPrize()
                                                    && grocery.getTypeOfAmount().equals(grocery1.getTypeOfAmount()) && grocery.getAmount().equals(grocery1.getAmount()) && grocery.getBestBefore().equals(grocery1.getBestBefore())) {
                                                String key = snapshot.getKey();
                                                databaseReference.child(key).removeValue();
                                                break;

                                            }
                                        }
                                        Toast.makeText(getActivity(), nameOfGrocery.getText() + " " + priceOfGrocery.getText() + "kr " + amountValue.getText() +  " consumed", Toast.LENGTH_LONG).show();
                                        Navigation.findNavController(view).navigate(R.id.action_myGroceriesFragment_to_inventoryFragment);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull  DatabaseError error) {

                                    }
                                });
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        wasteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

                Waste waste = new Waste(currentDate, arrayList.get(pos).getPrize(),arrayList.get(pos).getAmount(),
                        arrayList.get(pos).getTypeOfAmount());
                Grocery grocery = new Grocery(arrayList.get(pos).getName(), arrayList.get(pos).getBrand(), arrayList.get(pos).getPrize(),
                        arrayList.get(pos).getTypeOfAmount(), arrayList.get(pos).getAmount(), arrayList.get(pos).getTypeOfGrocery(), arrayList.get(pos).getBestBefore());

                databaseReference = database.getReference("User");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                            String email = ds.child("Email").getValue().toString();
                            if (email.equals(hashedEmail)){
                                databaseReference = database.getReference("User/" + ds.getKey() + "/Inventory");
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            Grocery grocery1 = ds.getValue(Grocery.class);
                                            if (grocery.getName().equals(grocery1.getName()) && grocery.getBrand().equals(grocery1.getBrand()) && grocery.getPrize() == grocery1.getPrize()
                                                    && grocery.getTypeOfAmount().equals(grocery1.getTypeOfAmount()) && grocery.getAmount().equals(grocery1.getAmount()) && grocery.getBestBefore().equals(grocery1.getBestBefore())) {
                                                String key = snapshot.getKey();
                                                databaseReference.child(key).removeValue();
                                                break;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull  DatabaseError error) {

                                    }
                                });
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                dbConnect.addInformation(waste);
                Toast.makeText(getActivity(), nameOfGrocery.getText() + " " + priceOfGrocery.getText() + "kr " + amountValue.getText() + " wasted", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_myGroceriesFragment_to_inventoryFragment);
            }

        });

        freezeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Grocery grocery = new Grocery(arrayList.get(pos).getName(), arrayList.get(pos).getBrand(), arrayList.get(pos).getPrize(),
                        arrayList.get(pos).getTypeOfAmount(), arrayList.get(pos).getAmount(), arrayList.get(pos).getTypeOfGrocery(), arrayList.get(pos).getBestBefore());

                databaseReference = database.getReference("User");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                            String email = ds.child("Email").getValue().toString();
                            if (email.equals(hashedEmail)){
                                databaseReference = database.getReference("User/" + ds.getKey() + "/Inventory");
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            Grocery grocery1 = ds.getValue(Grocery.class);
                                            if (grocery.getName().equals(grocery1.getName()) && grocery.getBrand().equals(grocery1.getBrand()) && grocery.getPrize() == grocery1.getPrize()
                                                    && grocery.getTypeOfAmount().equals(grocery1.getTypeOfAmount()) && grocery.getAmount().equals(grocery1.getAmount()) && grocery.getBestBefore().equals(grocery1.getBestBefore())) {
                                                ds.getRef().child("bestBefore").setValue("FROZEN");
                                                break;
                                            }
                                        }
                                        Toast.makeText(getActivity(), nameOfGrocery.getText() + " " + amountValue.getText() + " frozen", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull  DatabaseError error) {

                                    }
                                });
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });

        return view;
    }

    public void getPos(){
        if (getArguments() != null) {
            pos = getArguments().getInt("pos");
        }
    }
}