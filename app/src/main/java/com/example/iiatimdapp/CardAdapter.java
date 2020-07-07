package com.example.iiatimdapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.iiatimdapp.Room.Moestuin;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends PagerAdapter {

    private ArrayList<Moestuin> models;
    private LayoutInflater layoutInflater;
    private Context context;

    @Override
    public int getCount() {
        return models.size();

    }

    public CardAdapter(ArrayList<Moestuin> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, container, false);
        ImageView image;
        TextView title;
        TextView desc;

        image = view.findViewById(R.id.image);
        title= view.findViewById(R.id.title);
        desc = view. findViewById(R.id.desc);

//        image.setImageResource(models.get(position).getNaam());
        Picasso.get().load(models.get(position).getImg()).into(image);
        title.setText(models.get(position).getNaam());
        desc.setText("Bedekking: " + models.get(position).getMoestuin_lengte() + "/" + models.get(position).getMoestuin_breedte());

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
