package com.example.sambongrejo;

public class Ayam {
    public int id;
    public String nama;
    public String deskripsi;
    public int harga;
    public int berat;
    public int stok;
    public String gambar;

    public Ayam(int id, String nama, String deskripsi, int harga, int berat, int stok, String gambar) {
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.berat = berat;
        this.stok = stok;
        this.gambar = gambar;
    }

    public String getImage(){
        return ServerAPI.BASE_URL + "/gambar/" + gambar;
    }
}
