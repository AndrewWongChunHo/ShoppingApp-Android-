package com.example.project_text.adpaters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_text.activity.ProductDetailActivity;
import com.example.project_text.R;
import com.example.project_text.models.item;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<item> list;

    public MyAdapter(ArrayList<item> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.brand.setText(list.get(position).getBrand());
        Glide.with(holder.imageView.getContext()).load(list.get(position).getImage()).into(holder.imageView);

        //Move to the detail page when clicking the picture
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ProductDetailActivity.class);
                i.putExtra("detail", list.get(position));
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, brand;
        ImageView imageView;
       public MyViewHolder(@NonNull View itemView) {
           super(itemView);
           name = itemView.findViewById(R.id.productName);
           brand = itemView.findViewById(R.id.productBrand);
           imageView = itemView.findViewById(R.id.imageview);
       }
   }


}

