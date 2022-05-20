package com.example.project_text.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_text.R;
import com.example.project_text.models.category;

import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.categoryViewHolder> {
    Context context;
    List<category> itemList;

    public categoryAdapter(Context context, List<category> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new categoryViewHolder(LayoutInflater.from(context).inflate(R.layout.catergory_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull categoryViewHolder holder, int position) {
        holder.imageView.setImageResource(itemList.get(position).getCategoryImage());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    //ViewHolder
    class categoryViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.categoryImage);
        }
    }


}
