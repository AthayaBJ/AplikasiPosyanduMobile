package com.example.aplikasiposyandu_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText oldPassword, newPassword, confirmPassword;
    private Button btnChangePass;
    private ProgressBar progressBar;
    private FirebaseUser userAuth;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ImageView back = findViewById(R.id.backline);
        oldPassword = findViewById(R.id.edt_current_pass);
        newPassword = findViewById(R.id.edt_new_pass);
        confirmPassword = findViewById(R.id.edt_confirm_pass);
        btnChangePass = findViewById(R.id.btn_change_pass);
        progressBar = findViewById(R.id.progressBar);

        back.setOnClickListener(v -> finish());

        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();

        btnChangePass.setOnClickListener(v -> {
            String currentPass = oldPassword.getText().toString().trim();
            String newPass = newPassword.getText().toString().trim();
            String confirmPass = confirmPassword.getText().toString().trim();

            if (currentPass.isEmpty()) {
                oldPassword.setError("Kata sandi saat ini diperlukan");
                return;
            } else if (newPass.isEmpty()) {
                newPassword.setError("Password baru diperlukan");
                return;
            } else if (!newPass.equals(confirmPass)) {
                newPassword.setError("Konfirmasi password tidak sama");
                confirmPassword.setError("Konfirmasi password tidak sama");
                return;
            } else {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null && user.getEmail() != null) {
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPass);
                    btnChangePass.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);

                    user.reauthenticate(credential).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPass).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    btnChangePass.setEnabled(true);
                                    progressBar.setVisibility(View.GONE);
                                    progressBar.setIndeterminate(false);

                                    Toast.makeText(ChangePasswordActivity.this, "Silahkan login kembali", Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();

                                    Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.clear();
                                    editor.apply();

                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(ChangePasswordActivity.this, "Ganti password gagal", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            btnChangePass.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            progressBar.setIndeterminate(false);
                            oldPassword.setError("Incorrect current password");
                        }
                    });
                }
            }
        });
    }
}
