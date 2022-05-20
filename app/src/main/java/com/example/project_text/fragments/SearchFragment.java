package com.example.project_text.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.project_text.R;
import com.example.project_text.activity.SearchActivity;
import com.example.project_text.adpaters.categoryAdapter;
import com.example.project_text.models.category;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private List<category> itemList;
    private com.example.project_text.adpaters.categoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search, container, false);

        //Recycler View & Set Adapter
        recyclerView = v.findViewById(R.id.catergoryRecyclerView);
        categoryAdapter = new categoryAdapter(getContext(), itemList);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setHasFixedSize(true);

        //Intent Category Page to Search Page
        ImageButton searchBtn = v.findViewById(R.id.searchButton);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
            }
        });

        TextView hintText = v.findViewById(R.id.hintText);
        hintText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add categories
        itemList = new ArrayList<category>();
        itemList.add(new category(R.drawable.card1));
        itemList.add(new category(R.drawable.card2));
        itemList.add(new category(R.drawable.card3));
        itemList.add(new category(R.drawable.card4));
        itemList.add(new category(R.drawable.card5));
        itemList.add(new category(R.drawable.card6));
        itemList.add(new category(R.drawable.card7));
        itemList.add(new category(R.drawable.card8));
        itemList.add(new category(R.drawable.card9));
        itemList.add(new category(R.drawable.card10));
    }

}