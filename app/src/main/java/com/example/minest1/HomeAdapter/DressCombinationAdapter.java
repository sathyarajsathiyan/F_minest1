package com.example.minest1.HomeAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.example.minest1.DashbordMain;
import com.example.minest1.R;
import com.example.minest1.util.CombinationPoJo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;

public class DressCombinationAdapter extends RecyclerView.Adapter<DressCombinationAdapter.FeaturedViewHolder> {
    private Context fContext;
    ArrayList<CombinationPoJo> DressCombinationsPoJo;


    public DressCombinationAdapter(Context context, ArrayList<CombinationPoJo> DressCombinations) {
        DressCombinationsPoJo = DressCombinations;
    fContext = (Context) context;
        this.notifyDataSetChanged();


    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_view, parent, false);
        return new FeaturedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {

        final CombinationPoJo combination = DressCombinationsPoJo.get(position);

        //holder.img1.setImageResource(featuredHelperClass.getImg1());
        //holder.img2.setImageResource(featuredHelperClass.getImg2());

        String top_image = combination.getTop_image();
        String bottom_image = combination.getBottom_image();
        Picasso.get().load(top_image).fit().into(holder.img1);
        Picasso.get().load(bottom_image).fit().into(holder.img2);
        holder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick:Trash ");
                Toast.makeText(v.getContext(), "Trash", Toast.LENGTH_SHORT).show();

            }
        });
        holder.worn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                ((DashbordMain) fContext).uploadworn(combination.getTop_image(), combination.getBottom_image(), combination.getWear_date(),
                        combination.getTop_type(), combination.getTop_color(), combination.getBottom_type(), combination.getBottom_color());


            }

        });


    }


    @Override
    public int getItemCount() {
        return DressCombinationsPoJo.size();
    }

    public static class FeaturedViewHolder extends RecyclerView.ViewHolder {
        ImageView img1, img2;
        Button trash, worn;


        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);
            //hooks
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);

            trash = itemView.findViewById(R.id.trash);
            worn = itemView.findViewById(R.id.worn);

        }
    }
}
