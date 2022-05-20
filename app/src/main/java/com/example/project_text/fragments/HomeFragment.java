package com.example.project_text.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_text.activity.Product;
import com.example.project_text.R;
import com.example.project_text.adpaters.productAdapter;
import com.example.project_text.models.item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    View v;
    RecyclerView newRecyclerView, allRecyclerView;
    DatabaseReference ref;
    ArrayList<item> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);

        //reference the data in Firebase
        ref = FirebaseDatabase.getInstance().getReference().child("Item");

        // New In Products
        newRecyclerView = v.findViewById(R.id.newRecyclerView);

        // All Products on Home Page
        allRecyclerView = v.findViewById(R.id.allRecyclerView);


        //Move to the All Product page when clicking the more button
        TextView textView = v.findViewById(R.id.btn_AllProductMore);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Product.class);
                startActivity(i);
            }
        });
        return v;
    }

    //Pass database data to newRecyclerView & allRecyclerView
    @Override
    public void onStart() {
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
                        newRecyclerView.setAdapter(productAdapter);
                        allRecyclerView.setAdapter(productAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}