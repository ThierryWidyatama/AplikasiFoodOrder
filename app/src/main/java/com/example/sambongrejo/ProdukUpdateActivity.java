package com.example.sambongrejo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class ProdukUpdateActivity extends AppCompatActivity {
    EditText nama, deskripsi, harga, berat, stok;
    ImageView iv_gambar;
    Button btn_gallery, btn_simpan, btn_reset;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_update);
        AndroidNetworking.initialize(getApplicationContext());

        int id = getIntent().getIntExtra("id", 0);

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
                AndroidNetworking.post(ServerAPI.PRODUK_UPDATE + "?id=" + id)
                        .addBodyParameter("nama", nama.getText().toString())
                        .addBodyParameter("deskripsi", deskripsi.getText().toString())
                        .addBodyParameter("harga", harga.getText().toString())
                        .addBodyParameter("berat", berat.getText().toString())
                        .addBodyParameter("stok", stok.getText().toString())
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    int value = response.getInt("value");
                                    if (value == 1){
                                        Toast.makeText(getApplicationContext(), "Berhasil!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Gagal!", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("anError", anError.getMessage());
                                Toast.makeText(getApplicationContext(), "Gagal!", Toast.LENGTH_LONG).show();
                            }
                        });
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

        AndroidNetworking.get(ServerAPI.PRODUK_GET_BY_ID + "?id=" + id)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            nama.setText(data.getString("nama"));
                            deskripsi.setText(data.getString("deskripsi"));
                            harga.setText(data.getString("harga"));
                            berat.setText(data.getString("berat"));
                            stok.setText(data.getString("stok"));
                            Glide.with(getApplicationContext())
                                    .load(ServerAPI.BASE_URL + "/gambar/" + data.getString("gambar"))
                                    .into(iv_gambar);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("get data", anError.getMessage());
                    }
                });
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