package com.example.iiatimdapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchSeedsAdapter extends RecyclerView.Adapter<SearchSeedsAdapter.SeedsViewHolder> {
    String data_title[], data_desc[];
    int data_img[];
    Context context;
    public SearchSeedsAdapter(Context ct, String title[], String desc[], int images[] ){
        context = ct;
        data_title = title;
        data_desc = desc;
        data_img= images;
    }

    @NonNull
    @Override
    public SeedsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_seed_recylcerview, parent, false);
        return new SeedsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeedsViewHolder holder, int position) {
        holder.title_txt.setText(data_title[position]);
        holder.desc_txt.setText(data_desc[position]);
        holder.image.setImageResource(data_img[position]);
    }

    @Override
    public int getItemCount() {
        return data_img.length;
    }

    public class SeedsViewHolder extends RecyclerView.ViewHolder {

        TextView title_txt, desc_txt;
        ImageView image;


        public SeedsViewHolder(@NonNull View itemView) {
            super(itemView);
            title_txt = itemView.findViewById(R.id.seed_title_txt);
            desc_txt= itemView.findViewById(R.id.seed_desc_txt);
            image = itemView.findViewById(R.id.search_seed_img);
        }
    }
}
