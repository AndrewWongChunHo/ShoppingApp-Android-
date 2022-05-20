package com.example.project_text.adpaters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_text.R;
import com.example.project_text.activity.Login;
import com.example.project_text.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.CartViewHolder> {

    List<MyCartModel> cartModelList;
    Context context;
    int totalPrice = 0;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public MyCartAdapter(List<MyCartModel> cartModelList) {
        this.cartModelList = cartModelList;
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.name.setText(cartModelList.get(position).getProductName());
        holder.brand.setText(cartModelList.get(position).getProductBrand());
        holder.quantity.setText("QTY: " + cartModelList.get(position).getTotalQuantity());
        holder.price.setText("$" + String.valueOf(cartModelList.get(position).getTotalPrice()));
        Glide.with(holder.image.getContext()).load(cartModelList.get(position).getProductImage()).into(holder.image);

        //delete items in the cart
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid())
                        .collection("User")
                        .document(cartModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    cartModelList.remove(cartModelList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(v.getContext(), "Item Deleted", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(v.getContext(), "Error"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }
        });

        //Pass total amount to the cart fragment
        totalPrice = totalPrice + cartModelList.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    //ViewHolder
    class CartViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, brand, quantity, totalPrice;
        ImageView image, deleteItem;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            brand = itemView.findViewById(R.id.product_brand);
            price = itemView.findViewById(R.id.product_price);
            image = itemView.findViewById(R.id.product_image);
            quantity = itemView.findViewById(R.id.total_quantity);
            deleteItem = itemView.findViewById(R.id.delete_item);
        }
    }
}
