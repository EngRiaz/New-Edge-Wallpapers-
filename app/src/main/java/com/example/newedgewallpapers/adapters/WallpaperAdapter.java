package com.example.newedgewallpapers.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newedgewallpapers.FullWallpaperActivity;
import com.example.newedgewallpapers.R;

import java.util.ArrayList;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViewHolder> {
    private ArrayList<String> wallPaperList;
    private Context context;

    // creating a constructor.
    public WallpaperAdapter(ArrayList<String> wallPaperList, Context context) {
        this.wallPaperList = wallPaperList;
        this.context = context;
    }

    @NonNull
    @Override
    public WallpaperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wallpaper, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperAdapter.ViewHolder holder,int Position) {

        Glide.with(context).load(wallPaperList.get(Position)).into(holder.wallpaperIV);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, FullWallpaperActivity.class);
            i.putExtra("imgUrl", wallPaperList.get(Position));
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return wallPaperList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView imageCV;
        private ImageView wallpaperIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wallpaperIV = itemView.findViewById(R.id.idIVWallpaper);
            imageCV = itemView.findViewById(R.id.idCVWallpaper);
        }
    }
}