package com.example.iiatimdapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iiatimdapp.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import com.example.iiatimdapp.Room.Zaadjes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChooseSeedsAdapter extends RecyclerView.Adapter<ChooseSeedsAdapter.SeedsViewHolder> {

    String data_title[], data_desc[];
    int data_img[];
    private ArrayList<Zaadjes> zaadjes = new ArrayList<>();
    Context context;
    String moestuin_id;
    String x;


    public ChooseSeedsAdapter(ArrayList<Zaadjes> zaadjes, String moestuin_id, String x) {
        this.zaadjes = zaadjes;
        this.moestuin_id = moestuin_id;
        this.x = x;
    }

    @NonNull
    @Override
    public SeedsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_chooseseed_recyclerview, parent, false);
        return new ChooseSeedsAdapter.SeedsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeedsViewHolder holder, int position) {
        holder.title_txt.setText(zaadjes.get(position).getName());
        holder.desc_txt.setText(zaadjes.get(position).getDescription());
        Picasso.get().load(zaadjes.get(position).getImg()).resize(100, 100).centerCrop().transform(new CircleTransform()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return zaadjes.size();
    }

    public class SeedsViewHolder extends RecyclerView.ViewHolder {

        TextView title_txt, desc_txt;
        ImageView image;
        Button buttonadd;
        Button buttoninfo;


        public SeedsViewHolder(@NonNull View itemView) {
            super(itemView);
            title_txt = itemView.findViewById(R.id.seed_title_txt);
            desc_txt = itemView.findViewById(R.id.seed_desc_txt);
            image = itemView.findViewById(R.id.search_seed_img);
            buttonadd = itemView.findViewById(R.id.buttonadd);
            buttoninfo = itemView.findViewById(R.id.buttoninfo);

            buttoninfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent myIntent = new Intent(context, HomeActivity.class);
                    context.startActivity(myIntent);

                }
            });
            buttonadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition() + 1;
                    APIManager.getInstance(context).postZaadjeMoestuin(Integer.parseInt(moestuin_id), position, Integer.parseInt(x));
                }
            });

        }
    }
}
