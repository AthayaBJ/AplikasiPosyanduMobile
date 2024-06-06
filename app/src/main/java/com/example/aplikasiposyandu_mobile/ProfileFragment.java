package com.example.aplikasiposyandu_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private TextView txtNama, txtEmail, txtTelephone, txtAlamat;
    private Button btnLogout, btnDashboard, btnGantiPass;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        txtNama = view.findViewById(R.id.nama_akun);
        txtEmail = view.findViewById(R.id.email_akun);
        txtTelephone = view.findViewById(R.id.telepon_akun);
        txtAlamat = view.findViewById(R.id.alamat_akun);
        btnLogout = view.findViewById(R.id.logout);
        btnDashboard = view.findViewById(R.id.dashboard);
        btnGantiPass = view.findViewById(R.id.ganti_pass);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://aplikasiposyandu-mobile-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("users").child(mAuth.getCurrentUser().getUid());

        sharedPreferences = getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);

        loadUserData();

        setupButtons();

        return view;
    }

    private void loadUserData() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);

                    if (user != null) {
                        txtNama.setText(user.getName());
                        txtEmail.setText(user.getEmail());
                        txtTelephone.setText(user.getTelephone());
                        txtAlamat.setText(user.getAddress());

                        // Save to SharedPreferences for later use
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("nama", user.getName());
                        editor.putString("email", user.getEmail());
                        editor.putString("telepon", user.getTelephone());
                        editor.putString("alamat", user.getAddress());
                        editor.apply();
                    }
                } else {
                    Toast.makeText(getContext(), "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupButtons() {
        String role = sharedPreferences.getString("role", "");

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            startActivity(intent);
            getActivity().finish();
        });

        btnGantiPass.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        });

        btnDashboard.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), StatusActivity.class);
            startActivity(intent);
        });

        // Retrieve and log data from SharedPreferences for debugging
        try {
            String nama = sharedPreferences.getString("nama", "");
            String email = sharedPreferences.getString("email", "");
            String telepon = sharedPreferences.getString("telepon", "");
            String alamat = sharedPreferences.getString("alamat", "");

            Log.d("ProfileFragment", "Nama: " + nama);
            Log.d("ProfileFragment", "Email: " + email);
            Log.d("ProfileFragment", "Telepon: " + telepon);
            Log.d("ProfileFragment", "Alamat: " + alamat);

            txtNama.setText(nama);
            txtEmail.setText(email);
            txtTelephone.setText(telepon);
            txtAlamat.setText(alamat);
        } catch (Exception e) {
            Log.e("Data error", String.valueOf(e));
        }
    }
}
