package com.example.sambongrejo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sambongrejo.rajaongkir.PilihKurir;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KeranjangActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView tv_total_harga, tv_total_berat;
    RecyclerView rv_keranjang;
    KeranjangAdapter keranjangAdapter;
    List<Keranjang> list;
    Button btn_checkout;
    int total_harga, total_berat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        AndroidNetworking.initialize(getApplicationContext());

        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        rv_keranjang = findViewById(R.id.rv_keranjang);
        list = new ArrayList<>();
        btn_checkout = findViewById(R.id.btn_checkout);
        tv_total_harga = findViewById(R.id.tv_total_harga);
        tv_total_berat = findViewById(R.id.tv_total_berat);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        rv_keranjang.setLayoutManager(layoutManager);
        keranjangAdapter = new KeranjangAdapter(list, KeranjangActivity.this);
        rv_keranjang.setAdapter(keranjangAdapter);

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (total_harga == 0){
                    Toast.makeText(getApplicationContext(), "Keranjang Anda kosong!", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(KeranjangActivity.this, PilihKurir.class);
                    intent.putExtra("total_harga", total_harga);
                    intent.putExtra("total_berat", total_berat);
                    startActivity(intent);
                }
            }
        });

        AndroidNetworking.get(ServerAPI.KERANJANG_GET_ALL + "?user_id=" + sharedPreferences.getInt("id", 0))
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject produk = data.getJSONObject(i);
                                total_harga += produk.getInt("harga");
                                total_berat += produk.getInt("berat");
                                list.add(new Keranjang(
                                        produk.getInt("id"),
                                        produk.getInt("produk_id"),
                                        produk.getString("nama"),
                                        produk.getInt("harga"),
                                        produk.getInt("berat"),
                                        produk.getString("gambar")
                                ));
                            }

                            keranjangAdapter = new KeranjangAdapter(list, KeranjangActivity.this);
                            rv_keranjang.setAdapter(keranjangAdapter);
                            tv_total_harga.setText("Rp. " + String.valueOf(total_harga));
                            tv_total_berat.setText(String.valueOf(total_berat) + " gram");
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
}