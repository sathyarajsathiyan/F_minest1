package com.example.minest1.HomeAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minest1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PopUpPredcitAdapter extends RecyclerView.Adapter<PopUpPredcitAdapter.PopUpPredcitHolder> {
    //ImageView pop_Image;
    private Context hContext;
    private ArrayList<PopUpPredcitItems> popUpPredcitItemsArrayList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PopUpPredcitAdapter(Context context, ArrayList<PopUpPredcitItems> popUpPrecisItems) {
        hContext = context;
        popUpPredcitItemsArrayList = popUpPrecisItems;
        this.notifyDataSetChanged();


    }

    @NonNull
    @Override
    public PopUpPredcitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(hContext).inflate(R.layout.pop_recyclerview_card, parent, false);
        return new PopUpPredcitHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PopUpPredcitHolder holder, int position) {
        PopUpPredcitItems CurrentItem = popUpPredcitItemsArrayList.get(position);
        String popimageUrl = CurrentItem.getImageUrl();

        Picasso.get().load(popimageUrl).fit().into(holder.pop_Image);
        //this.notifyItemChanged(position);

    }

    @Override
    public int getItemCount() {
        return popUpPredcitItemsArrayList.size();
    }

    public class PopUpPredcitHolder extends RecyclerView.ViewHolder {
        ImageView pop_Image;

        public PopUpPredcitHolder(@NonNull final View itemView) {
            super(itemView);
            pop_Image = itemView.findViewById(R.id.pop_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }
}
