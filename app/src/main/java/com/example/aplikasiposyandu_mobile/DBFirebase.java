package com.example.aplikasiposyandu_mobile;

import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBFirebase {
    private DatabaseReference mDatabase;

    public DBFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplikasiposyandu-mobile-default-rtdb.asia-southeast1.firebasedatabase.app");
        mDatabase = database.getReference();
    }

    public void addUser(String name, String email, String telephone, String address, String password, String id) {
        try {
            User user = new User(id, name, email, telephone, address, password);
            mDatabase.child("users").child(id).setValue(user);
            Log.d("Write Data Success", "successfully");
        } catch (Exception e) {
            Log.e("Write Data Error", String.valueOf(e));
        }
    }

    public void addImunisasiData(ImunisasiData imunisasiData) {
        try {
            String id = mDatabase.child("imunisasi").push().getKey();
            if (id != null) {
                imunisasiData.setId(id);
                mDatabase.child("imunisasi").child(id).setValue(imunisasiData);
                Log.d("Write Data Success", "successfully");
            }
        } catch (Exception e) {
            Log.e("Write Data Error", String.valueOf(e));
        }
    }
}
