package com.example.minest1.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minest1.R;

import java.util.ArrayList;

public class FeaturedAdapter  extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    ArrayList<FeaturedHelperClass>featuredLocations;

    public FeaturedAdapter(ArrayList<FeaturedHelperClass> featuredLocations) {
        this.featuredLocations = featuredLocations;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_view,parent,false);

        FeaturedViewHolder featuredViewHolder=new FeaturedViewHolder(view);
        return featuredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
        FeaturedHelperClass featuredHelperClass= featuredLocations.get(position);
        holder.img1.setImageResource(featuredHelperClass.getImg1());
        holder.img2.setImageResource(featuredHelperClass.getImg2());
        holder.txt1.setText(featuredHelperClass.getTxt1());
        holder.txt2.setText(featuredHelperClass.getTxt2());
        holder.txt3.setText(featuredHelperClass.getTxt3());




    }

    @Override
    public int getItemCount() {
        return featuredLocations.size();    }

    public static  class FeaturedViewHolder extends RecyclerView.ViewHolder{
        ImageView img1,img2;
        TextView txt1,txt2,txt3;


        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);
            //hooks
            img1=itemView.findViewById(R.id.img1);
            img2=itemView.findViewById(R.id.img2);
            txt1=itemView.findViewById(R.id.like);
            txt2=itemView.findViewById(R.id.dislike);
            txt3=itemView.findViewById(R.id.worn);

        }
    }
}
