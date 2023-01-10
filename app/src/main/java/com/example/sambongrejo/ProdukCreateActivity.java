package com.example.sambongrejo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;

public class ProdukCreateActivity extends AppCompatActivity {
    EditText nama, deskripsi, harga, berat, stok;
    ImageView iv_gambar;
    Button btn_gallery, btn_simpan, btn_reset;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_create);
        AndroidNetworking.initialize(getApplicationContext());

        nama = findViewById(R.id.nama);
        deskripsi = findViewById(R.id.deskripsi);
        harga = findViewById(R.id.harga);
        berat = findViewById(R.id.berat);
        stok = findViewById(R.id.stok);
        iv_gambar = findViewById(R.id.iv_gambar);
        btn_gallery = findViewById(R.id.btn_gallery);
        btn_simpan = findViewById(R.id.btn_simpan);
        btn_reset = findViewById(R.id.btn_reset);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                store();
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama.setText("");
                deskripsi.setText("");
                harga.setText("");
                berat.setText("");
                stok.setText("");
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
            }
        });
    }

    private void store(){

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                iv_gambar.setImageURI(selectedImageUri);
            }
        }
    }
}