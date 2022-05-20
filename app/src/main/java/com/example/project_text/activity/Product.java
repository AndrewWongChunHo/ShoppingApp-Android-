package com.example.project_text.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.project_text.R;
import com.example.project_text.adpaters.productAdapter;
import com.example.project_text.fragments.HomeActivity;
import com.example.project_text.models.item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Product extends AppCompatActivity {

    ImageButton imageButton;
    DatabaseReference ref;
    ArrayList<item> list;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        ref = FirebaseDatabase.getInstance().getReference().child("Item");
        recyclerView = findViewById(R.id.productRecyclerView);

        imageButton = findViewById(R.id.BackShop);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Product.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    //Pass data to the app
    @Override
    protected void onStart() {
        super.onStart();
        if(ref != null){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        list = new ArrayList<>();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            list.add(ds.getValue(item.class));
                        }
                        productAdapter productAdapter = new productAdapter(list);
                        recyclerView.setAdapter(productAdapter);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Product.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}