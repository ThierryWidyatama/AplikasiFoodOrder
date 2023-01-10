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

public class AyamAdapter extends RecyclerView.Adapter<AyamAdapter.MyViewHolder> {
    private ItemClickListener clickListener;

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private List<Ayam> list;
    private Activity activity;

    public AyamAdapter(List<Ayam> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    public AyamAdapter(List<Ayam> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_ayam_dashboard, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ayam ayam = list.get(position);
        holder.tv_nama.setText(ayam.nama);
        holder.tv_harga.setText("Rp. " + String.valueOf(ayam.harga));
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView gambar;
        TextView tv_nama, tv_harga;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            gambar = itemView.findViewById(R.id.gambar);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_harga = itemView.findViewById(R.id.tv_harga);

            itemView.setOnClickListener(this);
            gambar.setOnClickListener(this);
            tv_nama.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null){
                clickListener.onClick(view, getAdapterPosition());
            }
        }
    }
}
