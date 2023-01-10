package com.example.sambongrejo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class InvoiceProdukAdapter extends RecyclerView.Adapter<InvoiceProdukAdapter.MyViewHolder>{
    private List<Ayam> list;
    private Activity activity;

    public InvoiceProdukAdapter(List<Ayam> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_invoice_produk, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ayam ayam = list.get(position);
        holder.tv_nama.setText(ayam.nama);
        holder.tv_harga.setText("Rp. " + String.valueOf(ayam.harga));
        holder.tv_berat.setText(String.valueOf(ayam.berat) + " gram");
        Glide.with(holder.itemView)
                .load(list.get(position).getImage())
                .placeholder(R.drawable.sepatu)
                .error(R.drawable.sepatu)
                .centerCrop()
                .into(holder.gambar);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView gambar;
        TextView tv_nama, tv_harga, tv_berat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            gambar = itemView.findViewById(R.id.gambar);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_harga = itemView.findViewById(R.id.tv_harga);
            tv_berat = itemView.findViewById(R.id.tv_berat);
        }
    }
}
