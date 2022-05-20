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

public class productAdapter extends RecyclerView.Adapter<productAdapter.ProductViewHolder> {

    ArrayList<item> list;

    public productAdapter(ArrayList<item> list){

        this.list = list;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_item_view, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.brand.setText(list.get(position).getBrand());
        holder.price.setText("$" + list.get(position).getPrice());
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

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, brand, price, description;
        ImageView imageView;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productNameAll);
            brand = itemView.findViewById(R.id.productBrandAll);
            imageView = itemView.findViewById(R.id.productImage);
            price = itemView.findViewById(R.id.productPrice);
        }
    }
}
