package com.example.sambongrejo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements AyamAdapter.ItemClickListener {
    SharedPreferences mPreferences;
    AyamAdapter ayamAdapter;
    List<Ayam> list;
    RecyclerView recyclerView;
    TextView tv_total;
    Button btn_cart, btn_home;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        AndroidNetworking.initialize(getApplicationContext());
        mPreferences = getSharedPreferences("user", MODE_PRIVATE);

        recyclerView = findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        tv_total = findViewById(R.id.tv_total);
        btn_cart = findViewById(R.id.btn_cart);
        btn_home = findViewById(R.id.btn_home);

        tv_total.setText("Rp. " + this.total);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ayamAdapter = new AyamAdapter(list, DashboardActivity.this);
        recyclerView.setAdapter(ayamAdapter);

        AndroidNetworking.get(ServerAPI.GET_BARANG_ALL)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject produk = data.getJSONObject(i);
                                list.add(new Ayam(
                                        produk.getInt("id"),
                                        produk.getString("nama"),
                                        produk.getString("deskripsi"),
                                        produk.getInt("harga"),
                                        produk.getInt("berat"),
                                        produk.getInt("stok"),
                                        produk.getString("gambar")
                                ));
                            }

                            ayamAdapter = new AyamAdapter(list, DashboardActivity.this);
                            ayamAdapter.setClickListener(DashboardActivity.this);
                            recyclerView.setAdapter(ayamAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("get data", anError.getMessage());
                    }
                });

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, KeranjangActivity.class);
                startActivity(intent);
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.user).setTitle(mPreferences.getString("nama", "User"));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.call_center: {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Call Center");
                alert.setMessage("(123) 567 894");
                alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
                break;
            }
            case R.id.sms_center: {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("SMS Center");
                alert.setMessage("+62 8512348675");
                alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
                break;
            }
            case R.id.maps: {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String data = "geo:-6.90705532547378, 109.52662909450777?z=14";
                intent.setData(Uri.parse(data));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            }
            case R.id.user: {
                Intent intent = new Intent(DashboardActivity.this, AkunActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.logout: {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view, int position) {
        Ayam ayam = list.get(position);

        switch (view.getId()) {
            case R.id.tv_nama: {
                Intent intent = new Intent(DashboardActivity.this, DetailActivity.class);
                intent.putExtra("id", ayam.id);
                intent.putExtra("nama", ayam.nama);
                intent.putExtra("deskripsi", ayam.deskripsi);
                intent.putExtra("harga", ayam.harga);
                intent.putExtra("berat", ayam.berat);
                intent.putExtra("stok", ayam.stok);
                intent.putExtra("gambar", ayam.getImage());
                startActivity(intent);
                break;
            }
            case R.id.gambar: {
                AndroidNetworking.post(ServerAPI.KERANJANG_STORE)
                        .addBodyParameter("user_id", String.valueOf(mPreferences.getInt("id", 0)))
                        .addBodyParameter("produk_id", String.valueOf(ayam.id))
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    int value = response.getInt("value");
                                    if (value == 1){
                                        total += ayam.harga;
                                        tv_total.setText("Rp. " + total);
                                        Toast.makeText(getApplicationContext(), "Tambah ke keranjang!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Gagal tambah ke keranjang!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getApplicationContext(), "Gagal tambah ke keranjang!", Toast.LENGTH_SHORT).show();
                                Log.d("anError", anError.getMessage());
                            }
                        });
                break;
            }
        }
    }
}