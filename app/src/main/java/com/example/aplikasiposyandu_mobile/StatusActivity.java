package com.example.aplikasiposyandu_mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class StatusActivity extends AppCompatActivity {

    private CheckBox checkImunisasi;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        checkImunisasi = findViewById(R.id.check_imunisasi);
        deleteButton = findViewById(R.id.delete_button);

        checkImunisasi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                deleteButton.setVisibility(View.VISIBLE);
            } else {
                deleteButton.setVisibility(View.GONE);
            }
        });

        deleteButton.setOnClickListener(v -> {
            // Logika untuk menghapus kegiatan
            // Misalnya, menampilkan pesan toast atau menghapus item dari database
        });
    }
}
