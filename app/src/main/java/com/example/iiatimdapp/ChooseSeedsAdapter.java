package com.example.iiatimdapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseSeedsAdapter extends RecyclerView.Adapter<ChooseSeedsAdapter.SeedsViewHolder> {

    String data_title[], data_desc[];
    int data_img[];
    Context context;

    public ChooseSeedsAdapter(Context ct, String title[], String desc[], int images[] ){
        context = ct;
        data_title = title;
        data_desc = desc;
        data_img= images;
    }

    @NonNull
    @Override
    public ChooseSeedsAdapter.SeedsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_chooseseed_recyclerview, parent, false);
        return new ChooseSeedsAdapter.SeedsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseSeedsAdapter.SeedsViewHolder holder, int position) {
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
        Button buttonadd;
        Button buttoninfo;


        public SeedsViewHolder(@NonNull View itemView) {
            super(itemView);
            title_txt = itemView.findViewById(R.id.seed_title_txt);
            desc_txt= itemView.findViewById(R.id.seed_desc_txt);
            image = itemView.findViewById(R.id.search_seed_img);
            buttonadd = itemView.findViewById(R.id.buttonadd);
            buttoninfo =itemView.findViewById(R.id.buttoninfo);

            buttoninfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent myIntent = new Intent(context, SearchSeedsActivity.class);
                    context.startActivity(myIntent);

                }
            });

        }
    }
}
