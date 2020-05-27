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

import java.util.ArrayList;

public class OutfitsAdapter extends RecyclerView.Adapter<OutfitsAdapter.OutfitsHolder> {

    private Context oContext;
    private ArrayList<OutfitsItem> pOutfitsItems;

    public OutfitsAdapter(Context context, ArrayList<OutfitsItem> OutFitsItem) {
        oContext = context;
        pOutfitsItems = OutFitsItem;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OutfitsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(oContext).inflate(R.layout.oufits_card, parent, false);
        return new OutfitsHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull OutfitsHolder holder, int position) {
        OutfitsItem currentItem=pOutfitsItems.get(position);
        String ImageUrl=currentItem.getImageUrl();
        Picasso.get().load(ImageUrl).fit().into(holder.outfits_image);


    }

    @Override
    public int getItemCount() {
        return pOutfitsItems.size();
    }

    public class OutfitsHolder extends RecyclerView.ViewHolder {
        ImageView outfits_image;

        public OutfitsHolder(@NonNull View itemView) {
            super(itemView);
            outfits_image = itemView.findViewById(R.id.outfits_images);
        }
    }
}
