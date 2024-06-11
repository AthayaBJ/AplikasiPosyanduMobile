package com.example.aplikasiposyandu_mobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class Imunisasi extends AppCompatActivity {

    private DBFirebase dbFirebase;
    private EditText tanggalImunisasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imunisasi);

        dbFirebase = new DBFirebase();

        EditText namaAnak = findViewById(R.id.namaAnak);
        EditText umur = findViewById(R.id.umur);
        EditText nik = findViewById(R.id.nik);
        tanggalImunisasi = findViewById(R.id.tanggalImunisasi);
        Spinner lokasiSpinner = findViewById(R.id.lokasiSpinner);
        Button submitButton = findViewById(R.id.submitButton);
        ImageView backline = findViewById(R.id.backline);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.lokasi_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lokasiSpinner.setAdapter(adapter);

        // Membuat EditText tidak bisa diedit secara langsung
        tanggalImunisasi.setFocusable(false);
        tanggalImunisasi.setClickable(true);

        // Menampilkan DatePickerDialog saat EditText diklik
        tanggalImunisasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = namaAnak.getText().toString();
                String umurAnak = umur.getText().toString();
                String nikAnak = nik.getText().toString();
                String tanggal = tanggalImunisasi.getText().toString();
                String lokasi = lokasiSpinner.getSelectedItem().toString();

                // Mendapatkan ID user dari Firebase Authentication
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userID = user.getUid();

                if (nama.isEmpty() || umurAnak.isEmpty() || nikAnak.isEmpty() || tanggal.isEmpty() || lokasi.isEmpty()) {
                    Toast.makeText(Imunisasi.this, "Harap isi semua field", Toast.LENGTH_SHORT).show();
                } else {
                    ImunisasiData imunisasiData = new ImunisasiData(nama, umurAnak, nikAnak, tanggal, lokasi, userID);
                    dbFirebase.addImunisasiData(imunisasiData);

                    Toast.makeText(Imunisasi.this, "Registrasi berhasil",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Imunisasi.this, HomeFragment.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // Tombol backline
        backline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengganti inisialisasi HomeFragment dengan MainActivity
                Intent intent = new Intent(Imunisasi.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        tanggalImunisasi.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }
}
