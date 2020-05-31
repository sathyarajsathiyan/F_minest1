package com.example.minest1.HomeAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minest1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistroyHolder> {
    private Context hContext;
    private ArrayList<HistoryItem> oHistoryItems;
    public HistoryAdapter(Context context,ArrayList<HistoryItem> HistoryList){
        hContext=context;
        oHistoryItems=HistoryList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistroyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(hContext).inflate(R.layout.closet_grid,parent,false);
        return new HistroyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistroyHolder holder, int position) {
        HistoryItem CurrentItem=oHistoryItems.get(position);
        String top_imageURl=CurrentItem.getTopImageUrl();
        String bot_imageURl=CurrentItem.getBotImageUrl();
        String date=CurrentItem.getMdate();
        Picasso.get().load(top_imageURl).fit().into(holder.top_image);
        Picasso.get().load(bot_imageURl).fit().into(holder.bot_image);
     holder.date.setText(date);

    }

    @Override
    public int getItemCount() {
        return oHistoryItems.size();
    }


    public class HistroyHolder extends RecyclerView.ViewHolder {
        ImageView top_image;
        ImageView bot_image;
        TextView date;


        public HistroyHolder(@NonNull View itemView) {
            super(itemView);
            top_image=itemView.findViewById(R.id.top_img);
            bot_image=itemView.findViewById(R.id.button_img);
            date=itemView.findViewById(R.id.date_txt);



        }
    }
}
