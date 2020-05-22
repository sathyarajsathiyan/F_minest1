package com.example.minest1.HomeAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minest1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Priview_closet extends RecyclerView.Adapter<Priview_closet.ViewHolder> {
    List<Priview>img1;
    LayoutInflater inflater;

    public Priview_closet(Context context, List<Priview> img1) {
        this.img1 = img1;
        this.inflater=LayoutInflater.from(context);

    }

    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.preview_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(img1.get(position).getImg1()).into(holder.img1);
        //holder.img1.setImageResource(img1.get(position));
    }

    @Override
    public int getItemCount() {
        return img1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img1 = itemView.findViewById(R.id.pre_img1);
        }


    }

}
