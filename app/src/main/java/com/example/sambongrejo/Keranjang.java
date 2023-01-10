package com.example.sambongrejo;

public class Keranjang {
    public int id;
    public int produk_id;
    public String nama;
    public int harga;
    public int berat;
    public String gambar;

    public Keranjang(int id, int produk_id, String nama, int harga, int berat, String gambar) {
        this.id = id;
        this.produk_id = produk_id;
        this.nama = nama;
        this.harga = harga;
        this.berat = berat;
        this.gambar = gambar;
    }

    public String getGambar(){
        return ServerAPI.BASE_URL + "/gambar/" + gambar;
    }
}
