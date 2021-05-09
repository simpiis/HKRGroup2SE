package com.example.hkrgroup2se.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hkrgroup2se.R;
import com.example.hkrgroup2se.Skeleton.DBConnect;
import com.example.hkrgroup2se.Skeleton.Grocery;
import com.example.hkrgroup2se.Skeleton.Inventory;
import com.example.hkrgroup2se.Skeleton.Security;

import java.time.LocalDate;

public class AddGroceriesFragment extends Fragment {
    private static final String[] BRANDS = new String[]{
            "OLW", "Skånemejerier", "Pågen", "Felix", "Apetit", "Findus", "ICA", "COOP", "ELDORADO"
    };
    private static final String[] TYPEOFGROCERY = new String[]{
            "Snacks",
    };
    private static final String[] TYPEOFAMOUNT = new String[]{
            "gram",
    };

    AutoCompleteTextView brand, typeOfAmount, typeOfGrocery;
    EditText name, price, amount;
    Button addGroceryButton;
    Inventory inventory = Inventory.getInstance();
    DatePicker picker;
    DBConnect dbConnect = DBConnect.getInstance();
    Security security = new Security();

    public AddGroceriesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_groceries, container, false);

        brand = view.findViewById(R.id.brand);
        typeOfAmount = view.findViewById(R.id.typeOfAmount);
        typeOfGrocery = view.findViewById(R.id.typeOfGrocery);
        name = view.findViewById(R.id.name);
        price = view.findViewById(R.id.price);
        amount = view.findViewById(R.id.amount);
        addGroceryButton = view.findViewById(R.id.AddGroceryButton);
        picker = view.findViewById(R.id.datePicker1);

        addGroceryButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {

                    boolean error = false;

                    if (TextUtils.isEmpty(name.getText().toString())) {
                        name.setError("Name is required");
                        error = true;
                    }
                    if (TextUtils.isEmpty(brand.getText().toString())) {
                        brand.setError("Brand is required");
                        error = true;
                    }
                    if (TextUtils.isEmpty(typeOfAmount.getText().toString())) {
                        typeOfAmount.setError("Type of amount is required");
                        error = true;
                    }
                    if (TextUtils.isEmpty(typeOfGrocery.getText().toString())) {
                        typeOfGrocery.setError("Type of grocery is required");
                        error = true;
                    }
                    if (TextUtils.isEmpty(price.getText().toString())) {
                        price.setError("Price is required");
                        error = true;
                    }
                    if (TextUtils.isEmpty(amount.getText().toString())) {
                        amount.setError("Amount is required");
                        error = true;
                    }
                    try{
                        Float.parseFloat(price.getText().toString());
                    }catch (Exception exception){
                        price.setError("Price must be a number");
                        error = true;
                    }
                    if (error) {
                        Toast.makeText(getActivity(), "Check following errors", Toast.LENGTH_LONG).show();
                        return;
                    }
                    LocalDate date = security.checkDate(Integer.toString(picker.getDayOfMonth()), Integer.toString(picker.getMonth() + 1), Integer.toString(picker.getYear())); //datepicker no likey zeroes
                    Grocery grocery = new Grocery(name.getText().toString(), brand.getText().toString(),
                            Float.parseFloat(price.getText().toString()),
                            typeOfAmount.getText().toString(), amount.getText().toString(),
                            typeOfGrocery.getText().toString(), date.toString());
                    inventory.addGrocery(grocery);
                    dbConnect.addInformation(grocery);
                    Toast.makeText(getActivity(), "Successfully added Grocery", Toast.LENGTH_LONG).show();
                } catch (Exception exception) {
                    Toast.makeText(getActivity(), "Failed to add Grocery", Toast.LENGTH_LONG).show();
                }


               /* FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                Map<String, Object> userData = new HashMap<>();
                userData.put(grocery.getName(),grocery);
                db.collection("users").document(firebaseAuth.getUid()).collection("Groceries").document(grocery.getName()).set(userData);

                */
            }
        });

        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, TYPEOFGROCERY);
        typeOfGrocery.setAdapter(adapter);
        typeOfGrocery.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                typeOfGrocery.showDropDown();
            }
        });

        ArrayAdapter adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, TYPEOFAMOUNT);
        typeOfAmount.setAdapter(adapter2);
        typeOfAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                typeOfAmount.showDropDown();
            }
        });


        ArrayAdapter adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, BRANDS);
        brand.setAdapter(adapter3);
        brand.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    brand.showDropDown();
                }
            }
        });


        return view;
    }
}