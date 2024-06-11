package com.example.aplikasiposyandu_mobile;

import android.content.Intent;
import android.content.res.ColorStateList;
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
import com.google.firebase.database.DatabaseReference;

import java.util.concurrent.ExecutorService;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edt_name, edt_email, edt_telephone, edt_address, edt_password;
    private Button btn_register;
    private TextView MoveToLLogin;
    private ProgressBar progressBar;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        edt_name = findViewById(R.id.Redt_name);
        edt_email = findViewById(R.id.Redt_email);
        edt_telephone = findViewById(R.id.Redt_telp);
        edt_address = findViewById(R.id.Redt_alamat);
        edt_password = findViewById(R.id.Redt_password);
        btn_register = findViewById(R.id.btn_register);
        MoveToLLogin= findViewById(R.id.MoveToLLogin);
        progressBar = findViewById(R.id.progressBar);


        btn_register.setOnClickListener(v -> {
            String inName = edt_name.getText().toString();
            String inEmail = edt_email.getText().toString();
            String inTelp = edt_telephone.getText().toString();
            String inAddres = edt_address.getText().toString();
            String inPass = edt_password.getText().toString();


            btn_register.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);

            if (inName.isEmpty() || inEmail.isEmpty() || inTelp.isEmpty() || inAddres.isEmpty() || inPass.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Maaf, Anda belum mengisi semua data.", Toast.LENGTH_SHORT).show();
                return; // Menghentikan proses registrasi jika ada data yang kosong
            } else{
                try{
                    mAuth.createUserWithEmailAndPassword(inEmail, inPass).
                            addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseAuth user = mAuth;
                                        String userId = user.getCurrentUser().getUid();
                                        String userEmail = user.getCurrentUser().getEmail();
                                        DBFirebase db = new DBFirebase();

                                        db.addUser(inName, userEmail,inTelp, inAddres, inPass, userId);

                                        Log.d("Register Success", "Registration Successfully");
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        Toast.makeText(RegisterActivity.this, "Registrasi Berhasil !", Toast.LENGTH_SHORT).show();


                                        btn_register.setEnabled(true);
                                        progressBar.setVisibility(View.GONE);
                                        progressBar.setIndeterminate(false);

                                        startActivity(intent);
                                        finish();
                                    }else{
                                        // If sign in fails, display a message to the user.
                                        Log.w("Failed Auth", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, task.getException().getMessage().toString(),
                                                Toast.LENGTH_LONG).show();

                                        btn_register.setEnabled(true);
                                        progressBar.setVisibility(View.GONE);
                                        progressBar.setIndeterminate(false);
                                    }
                                }
                            });
                }catch(Exception e){
                    Log.e("Register Fail", String.valueOf(e));
                    Toast.makeText(RegisterActivity.this, "Registrasi Gagal !", Toast.LENGTH_SHORT).show();

                    btn_register.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    progressBar.setIndeterminate(false);
                }
            }
        });

        MoveToLLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }

}