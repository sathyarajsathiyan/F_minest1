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

public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.PreviewHolder> {
    private Context pContext;
    private ArrayList<PreviewItem> mPreviewList;

    public PreviewAdapter(Context context, ArrayList<PreviewItem> PreviewList) {
        pContext = context;
        mPreviewList = PreviewList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PreviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(pContext).inflate(R.layout.preview_card, parent, false);
        return new PreviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewHolder holder, int position) {
       PreviewItem currentItem=mPreviewList.get(position);
        String imageUrl=currentItem.getImageUrl();
        Picasso.get().load(imageUrl).fit().into(holder.Preview_image);

    }

    @Override
    public int getItemCount() {
        return mPreviewList.size();
    }

    public class PreviewHolder extends RecyclerView.ViewHolder {
        public ImageView Preview_image;

        public PreviewHolder(@NonNull View itemView) {

            super(itemView);
            Preview_image = itemView.findViewById(R.id.Preview_image);
        }


    }
}
