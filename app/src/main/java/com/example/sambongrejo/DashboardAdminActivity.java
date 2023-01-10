package com.example.sambongrejo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DashboardAdminActivity extends AppCompatActivity {
    TextView tv_konsumen, tv_produk, tv_penjualan, tv_akun, tv_logout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        tv_konsumen = findViewById(R.id.tv_konsumen);
        tv_produk = findViewById(R.id.tv_produk);
        tv_penjualan = findViewById(R.id.tv_penjualan);
        tv_akun = findViewById(R.id.tv_akun);
        tv_logout = findViewById(R.id.tv_logout);

        tv_konsumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardAdminActivity.this, KonsumenActivity.class);
                startActivity(intent);
            }
        });

        tv_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardAdminActivity.this, ProdukActivity.class);
                startActivity(intent);
            }
        });

        tv_penjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardAdminActivity.this, PenjualanCekActivity.class);
                startActivity(intent);
            }
        });

        tv_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardAdminActivity.this, AkunActivity.class);
                startActivity(intent);
            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(DashboardAdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}