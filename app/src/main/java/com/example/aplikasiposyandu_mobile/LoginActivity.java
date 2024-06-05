package com.example.aplikasiposyandu_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edt_email, edt_pass;
    private Button btn_login;
    private TextView to_register;
    private ProgressBar progressBar;

    // Shared preferences
    public static final String SHARED_PREF_NAME = "myPref";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        edt_email = findViewById(R.id.Ledt_Email);
        edt_pass = findViewById(R.id.Ledt_Pass);
        btn_login = findViewById(R.id.Ledt_btn);
        to_register = findViewById(R.id.MoveToRegistert);
        progressBar = findViewById(R.id.progressBar);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        btn_login.setOnClickListener(v -> {
            String email = edt_email.getText().toString();
            String pass = edt_pass.getText().toString();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Email dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            btn_login.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        String id = user.getUid();

                        // Update database URL to the correct region
                        DatabaseReference reference = FirebaseDatabase.getInstance("https://aplikasiposyandu-mobile-default-rtdb.asia-southeast1.firebasedatabase.app")
                                .getReference("users").child(id);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    String nama = snapshot.child("name").getValue(String.class);
                                    String email = snapshot.child("email").getValue(String.class);

                                    editor.putString("nama", nama);
                                    editor.putString("email", email);
                                    editor.putBoolean("masuk", true);
                                    editor.apply();

                                    Intent main = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(main);
                                    finish();

                                    Toast.makeText(LoginActivity.this, "Berhasil masuk", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                                }
                                btn_login.setEnabled(true);
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                btn_login.setEnabled(true);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        btn_login.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        to_register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
