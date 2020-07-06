package com.example.iiatimdapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iiatimdapp.Room.Zaadjes;

import java.util.ArrayList;

public class SearchSeedsAdapter extends RecyclerView.Adapter<SearchSeedsAdapter.SeedsViewHolder> {

    private ArrayList<Zaadjes> zaadjes;
    int data_img[];

    public SearchSeedsAdapter(ArrayList<Zaadjes> zaadjes, int images[]) {
        this.zaadjes = zaadjes;
        this.data_img = images;
    }

    public class SeedsViewHolder extends RecyclerView.ViewHolder {

        TextView title_txt, desc_txt;
        ImageView image;


        public SeedsViewHolder(@NonNull View itemView) {
            super(itemView);
            title_txt = itemView.findViewById(R.id.seed_title_txt);
            desc_txt = itemView.findViewById(R.id.seed_desc_txt);
            image = itemView.findViewById(R.id.search_seed_img);
        }
    }

    @NonNull
    @Override
    public SeedsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_seed_recylcerview, parent, false);
        SeedsViewHolder seedsViewHolder = new SeedsViewHolder(v);
        return seedsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SeedsViewHolder holder, int position) {
        holder.title_txt.setText(zaadjes.get(position).getName());
        holder.desc_txt.setText(zaadjes.get(position).getDescription());
        holder.image.setImageResource(data_img[position]);
    }

    @Override
    public int getItemCount() {
        return data_img.length;
    }

}
