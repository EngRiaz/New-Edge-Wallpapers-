package com.example.newedgewallpapers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newedgewallpapers.R;
import com.example.newedgewallpapers.data.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {


    private ArrayList<Category> category;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CategoryAdapter(ArrayList<Category> category, Context context, CategoryClickInterface categoryClickInterface) {
        this.category = category;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        Category modal = category.get(position);
        holder.categoryTV.setText(modal.getCategory());
        if (!modal.getImgUrl().isEmpty()) {
            Glide.with(context).load(modal.getImgUrl()).into(holder.categoryIV);

        } else {
            holder.categoryIV.setImageResource(R.drawable.ic_launcher_background);
        }
        holder.itemView.setOnClickListener(v -> {
            categoryClickInterface.onCategoryClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public interface CategoryClickInterface {
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryTV;
        private ImageView categoryIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIV = itemView.findViewById(R.id.idIVCategory);
            categoryTV = itemView.findViewById(R.id.idTVCategory);
        }
    }
}
