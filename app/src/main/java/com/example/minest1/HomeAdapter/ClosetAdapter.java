package com.example.minest1.HomeAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minest1.R;

import java.util.List;

public class ClosetAdapter extends RecyclerView.Adapter<ClosetAdapter.ViewHolder>{

    List<Integer>top_img;
    List<Integer>button_img;
    LayoutInflater inflater;

    public ClosetAdapter(Context context,  List<Integer>top_img,List<Integer>button_img){
        this.top_img=top_img;
        this.button_img=button_img;
        this.inflater=LayoutInflater.from(context);





    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.closet_grid,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.top_img.setImageResource(top_img.get(position));
        holder.button_img.setImageResource(button_img.get(position));

    }

    @Override
    public int getItemCount() {
        return top_img.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView top_img;
        ImageView button_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            top_img=itemView.findViewById(R.id.top_img);
            button_img=itemView.findViewById(R.id.button_img);
        }

    }


}
