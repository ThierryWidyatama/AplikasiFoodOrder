package com.example.sambongrejo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LaporanPenjualanAdapter extends RecyclerView.Adapter<LaporanPenjualanAdapter.MyViewHolder>{
    private List<LaporanPenjualan> list;
    private Activity activity;

    public LaporanPenjualanAdapter(List<LaporanPenjualan> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_laporan_penjualan, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LaporanPenjualan data = list.get(position);
        holder.tv_username.setText(data.username);
        holder.tv_nama.setText(data.nama);
        holder.tv_harga.setText("Rp. " + String.valueOf(data.harga));
        holder.tv_tanggal.setText(data.tanggal);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_username, tv_nama, tv_harga, tv_tanggal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_username = itemView.findViewById(R.id.tv_username);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_harga = itemView.findViewById(R.id.tv_harga);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
        }
    }
}
