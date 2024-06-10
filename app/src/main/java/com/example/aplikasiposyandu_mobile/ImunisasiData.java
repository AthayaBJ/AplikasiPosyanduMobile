package com.example.aplikasiposyandu_mobile;

public class ImunisasiData {
    private String id;
    private String nama;
    private String umur;
    private String nik;
    private String tanggalImunisasi;
    private String lokasi;

    public ImunisasiData(String nama, String umur, String nik, String tanggalImunisasi, String lokasi) {
        this.nama = nama;
        this.umur = umur;
        this.nik = nik;
        this.tanggalImunisasi = tanggalImunisasi;
        this.lokasi = lokasi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getTanggalImunisasi() {
        return tanggalImunisasi;
    }

    public void setTanggalImunisasi(String tanggalImunisasi) {
        this.tanggalImunisasi = tanggalImunisasi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
}
