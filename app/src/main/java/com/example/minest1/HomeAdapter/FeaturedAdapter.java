package com.example.minest1.HomeAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minest1.R;
import com.squareup.picasso.Picasso;

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
        //holder.img1.setImageResource(featuredHelperClass.getImg1());
        //holder.img2.setImageResource(featuredHelperClass.getImg2());

        Picasso.get().load(featuredHelperClass.getImg1()).into(holder.img1);
        Picasso.get().load(featuredHelperClass.getImg2()).into(holder.img2);
       holder.trash.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.d("TAG", "onClick:Trash ");
               Toast.makeText(v.getContext(), "Trash", Toast.LENGTH_SHORT).show();

           }
       });




    }

    @Override
    public int getItemCount() {
        return featuredLocations.size();    }

    public static  class FeaturedViewHolder extends RecyclerView.ViewHolder{
        ImageView img1,img2;
       Button trash,worn;


        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);
            //hooks
            img1=itemView.findViewById(R.id.img1);
            img2=itemView.findViewById(R.id.img2);

            trash=itemView.findViewById(R.id.trash);
            worn=itemView.findViewById(R.id.worn);

        }
    }
}
