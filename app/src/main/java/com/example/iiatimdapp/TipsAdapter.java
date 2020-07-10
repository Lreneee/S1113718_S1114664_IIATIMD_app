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

import com.example.iiatimdapp.Room.Tips;
import com.example.iiatimdapp.Room.Zaadjes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipsViewHolder> {

    private ArrayList<Tips> tips;

    public TipsAdapter(ArrayList<Tips> tips) {
        this.tips = tips;
    }

    public class TipsViewHolder extends RecyclerView.ViewHolder {

        TextView title_txt, desc_txt;
        ImageView image;


        public TipsViewHolder(@NonNull View itemView) {
            super(itemView);
            title_txt = itemView.findViewById(R.id.tips_title);
            desc_txt = itemView.findViewById(R.id.tips_desc);
            image = itemView.findViewById(R.id.tips_img);
        }
    }

    @NonNull
    @Override
    public TipsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.tips_recyclerview, parent, false);
        TipsViewHolder seedsViewHolder = new TipsViewHolder(v);
        return seedsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TipsViewHolder holder, int position) {
        holder.title_txt.setText(tips.get(position).getTitle());
        holder.desc_txt.setText(tips.get(position).getShort_description());
//        Picasso.get().load(tips.get(position).getImg()).resize(100, 100).centerCrop().transform(new CircleTransform()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }

}
