package com.example.hkrgroup2se.Skeleton;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.hkrgroup2se.Fragments.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DBConnect<T> {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private static DBConnect single_instance = null;
    private static Security security = new Security();


    public static DBConnect getInstance() {
        if (single_instance == null) {
            single_instance = new DBConnect();
        }
        return single_instance;
    }

    private DBConnect() {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getCurrentHash() {
        FirebaseUser getCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        return security.hashString(getCurrentUser.getEmail());
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public <T> void addInformation(T info) {
        if (info instanceof User) {
            databaseReference = database.getReference("User");
            String id = databaseReference.push().getKey();
            databaseReference = database.getReference("User/" + id + "/Email");
            String hash = security.hashString(((User) info).getEmail());
            databaseReference.setValue(hash);
        } else if (info instanceof Grocery) {
            FirebaseUser getCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
            String hashedEmail = security.hashString(getCurrentUser.getEmail());
            databaseReference = database.getReference("User");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String email = ds.child("Email").getValue().toString();
                        if (email.equals(hashedEmail)) {
                            databaseReference = database.getReference("User/" + ds.getKey() + "/Inventory");
                            databaseReference.push().setValue(info);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        } else if (info instanceof Waste) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String hashedEmail = security.hashString(currentUser.getEmail());
            databaseReference = database.getReference("User");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String email = ds.child("Email").getValue().toString();
                        if (email.equals(hashedEmail)) {
                            databaseReference = database.getReference("User/" + ds.getKey() + "/Waste");
                            databaseReference.push().setValue(info);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setShoppingListTitle() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        ListDate ld = new ListDate(currentDate);
        Map info = new HashMap();
        info.put("Title", ld);
        FirebaseUser getCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String hashedEmail = security.hashString(getCurrentUser.getEmail());
        databaseReference = database.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = ds.child("Email").getValue().toString();
                    if (email.equals(hashedEmail)) {
                        databaseReference = database.getReference("User/" + ds.getKey() + "/ShoppingLists/");
                        databaseReference.push().setValue(ld);

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void addToShoppingList(String key, String itemName, String itemAmount, String itemComment) {
        ShoppingListItem item = new ShoppingListItem(itemName, itemAmount, itemComment);
        Map info = new HashMap();
        info.put("Item", item);
        FirebaseUser getCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String hashedEmail = security.hashString(getCurrentUser.getEmail());
        databaseReference = database.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = ds.child("Email").getValue().toString();
                    if (email.equals(hashedEmail)) {
                        databaseReference = database.getReference("User/" + ds.getKey() + "/ShoppingLists/" + key + "/Items");
                        databaseReference.push().setValue(item);

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void modifyShoppingListItem(String key1, String key2, String itemName, String itemAmount, String itemComment) {
        ShoppingListItem item = new ShoppingListItem(itemName, itemAmount, itemComment);
        Map info = new HashMap();
        info.put("Item", item);
        FirebaseUser getCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String hashedEmail = security.hashString(getCurrentUser.getEmail());
        databaseReference = database.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = ds.child("Email").getValue().toString();
                    if (email.equals(hashedEmail)) {
                        databaseReference = database.getReference("User/" + ds.getKey() + "/ShoppingLists/" + key1 + "/Items/" + key2);
                        databaseReference.setValue(item);

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //for removing an item inside of a shoppinglist ie. k??ttf??rs
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void removeShoppingListItem(String key1,String key2) {
        FirebaseUser getCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String hashedEmail = security.hashString(getCurrentUser.getEmail());
        databaseReference = database.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = ds.child("Email").getValue().toString();
                    if (email.equals(hashedEmail)) {                                                                    //key1                          //key2
                        databaseReference = database.getReference("User/" + ds.getKey() + "/ShoppingLists/" + key1+"/Items/"+key2);
                        databaseReference.removeValue();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    //for removing an entire shoppinglist
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void removeShoppingList(String key) {
        FirebaseUser getCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String hashedEmail = security.hashString(getCurrentUser.getEmail());
        databaseReference = database.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = ds.child("Email").getValue().toString();
                    if (email.equals(hashedEmail)) {
                        databaseReference = database.getReference("User/" + ds.getKey() + "/ShoppingLists/"+key);
                        databaseReference.removeValue();

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
