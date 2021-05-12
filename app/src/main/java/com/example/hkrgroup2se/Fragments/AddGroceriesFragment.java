package com.example.hkrgroup2se.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
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
import com.example.hkrgroup2se.Skeleton.ScannerViewModel;
import com.example.hkrgroup2se.Skeleton.Security;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;

public class AddGroceriesFragment extends Fragment {
    private static final String TAG = "AddGroceriesFragment";

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
    FloatingActionButton scannerButton;
    Inventory inventory = Inventory.getInstance();
    DatePicker picker;
    DBConnect dbConnect = DBConnect.getInstance();
    Security security = new Security();
    View view;

    //Camera permission
    private static final int CAMERA_PERMISSION_CODE = 1;

    //ViewModel for scanner
    ScannerViewModel scannerViewModel;

    //Scanner get database info from barcode
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    public AddGroceriesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_groceries, container, false);

        brand = view.findViewById(R.id.brand);
        typeOfAmount = view.findViewById(R.id.typeOfAmount);
        typeOfGrocery = view.findViewById(R.id.typeOfGrocery);
        name = view.findViewById(R.id.name);
        price = view.findViewById(R.id.price);
        amount = view.findViewById(R.id.amount);
        addGroceryButton = view.findViewById(R.id.AddGroceryButton);
        picker = view.findViewById(R.id.datePicker1);
        scannerButton = view.findViewById(R.id.floatingActionButtonScanner);

        //If search result from barcode scan exists in ViewModel, fill in textViews
        scannerViewModel = new ViewModelProvider(getActivity()).get(ScannerViewModel.class);
        getScannerResult();

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
                    clearFields();
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

        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScanner();
            }
        });

        return view;
    }

    //Open Barcode scanner
    private void openScanner() {
        //Open scanner if permission is granted
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Navigation.findNavController(view).navigate(R.id.action_addGroceriesFragment_to_scannerFragment);
        } else {
            //Request permission if not granted
            requestCameraPermission();
        }
    }

    //Request camera permission
    private void requestCameraPermission() {
        requestPermissions(new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }

    //Response to camera permission decision
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Camera permission granted", Toast.LENGTH_SHORT).show();
                openScanner();
            } else {
                Toast.makeText(getActivity(), "Camera permission required for barcode scanner", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Fills out textViews on barcode scan
    public void getGrocery(String barcode) {
        databaseReference = database.getReference("Grocery/" + barcode);
        databaseReference
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getChildrenCount() == 0) {
                            Toast.makeText(getActivity(), "Scanned barcode not found", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Grocery grocery = snapshot.getValue(Grocery.class);
                        name.setText(grocery.getName());
                        brand.setText(grocery.getBrand());
                        amount.setText(grocery.getAmount());
                        typeOfAmount.setText(grocery.getTypeOfAmount());
                        typeOfGrocery.setText(grocery.getTypeOfGrocery());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.i(TAG, "onCancelled");
                    }
                });
    }

    public void getScannerResult() {
        if(!(scannerViewModel.getScannedBarcode().matches(""))) {
            //Run search if Scanned Barcode string is not empty
            getGrocery(scannerViewModel.getScannedBarcode());
            scannerViewModel.resetScannedBarcode();
        }
    }

    public void clearFields() {
        name.setText("");
        brand.setText("");
        typeOfGrocery.setText("");
        typeOfAmount.setText("");
        amount.setText("");
        price.setText("");
    }
}