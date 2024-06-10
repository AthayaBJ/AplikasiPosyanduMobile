package com.example.aplikasiposyandu_mobile;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
public class Imunisasi extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imunisasi);

        EditText namaAnak = findViewById(R.id.namaAnak);
        EditText umur = findViewById(R.id.umur);
        EditText nik = findViewById(R.id.nik);
        EditText tanggalImunisasi = findViewById(R.id.tanggalImunisasi);
        Spinner lokasiSpinner = findViewById(R.id.lokasiSpinner);
        Button submitButton = findViewById(R.id.submitButton);

        // setting spiner e ben iso luru lokasine
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.lokasi_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lokasiSpinner.setAdapter(adapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = namaAnak.getText().toString();
                String umurAnak = umur.getText().toString();
                String nikAnak = nik.getText().toString();
                String tanggal = tanggalImunisasi.getText().toString();
                String lokasi = lokasiSpinner.getSelectedItem().toString();

                // Aksi ketika tombol Submit ditekan
                Toast.makeText(Imunisasi.this, "Data: " + nama + ", " + umurAnak + ", " + nikAnak + ", " + tanggal + ", " + lokasi, Toast.LENGTH_LONG).show();
            }
        });
    }
}