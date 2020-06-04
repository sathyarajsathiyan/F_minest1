package com.example.minest1.HomeAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minest1.DashbordMain;
import com.example.minest1.Predcit_Activity;
import com.example.minest1.R;
import com.example.minest1.util.CombinationPoJo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PredcitCombinatonnAdapter extends RecyclerView.Adapter<PredcitCombinatonnAdapter.PredcitViewHolder> {

    private Context fContext;
    ArrayList<CombinationPoJo> DressCombinationsPoJo;


    public PredcitCombinatonnAdapter(Context context, ArrayList<CombinationPoJo> DressCombinations) {
        DressCombinationsPoJo = DressCombinations;
        fContext = (Context) context;
        this.notifyDataSetChanged();


    }
    @NonNull
    @Override
    public PredcitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.predcit_recyclerview_card, parent, false);
        return new PredcitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PredcitViewHolder holder, int position) {
        final CombinationPoJo combination = DressCombinationsPoJo.get(position);

        //holder.img1.setImageResource(featuredHelperClass.getImg1());
        //holder.img2.setImageResource(featuredHelperClass.getImg2());

        String top_image = combination.getTop_image();
        String bottom_image = combination.getBottom_image();
        Picasso.get().load(top_image).fit().into(holder.topImage);
        Picasso.get().load(bottom_image).fit().into(holder.botImage);

        holder.worn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                ((Predcit_Activity) fContext).uploadworn(combination.getTop_image(), combination.getBottom_image(), combination.getWear_date(),
                        combination.getTop_type(), combination.getTop_color(), combination.getBottom_type(), combination.getBottom_color());


            }

        });


    }

    @Override
    public int getItemCount() {
        return DressCombinationsPoJo.size();
    }

    public class PredcitViewHolder extends RecyclerView.ViewHolder {
        ImageView topImage, botImage;
        Button trash, worn;


        public PredcitViewHolder(@NonNull View itemView) {
            super(itemView);
            topImage = itemView.findViewById(R.id.top_image);
            botImage = itemView.findViewById(R.id.bottom_images);


            worn = itemView.findViewById(R.id.worn);
        }
    }
}
