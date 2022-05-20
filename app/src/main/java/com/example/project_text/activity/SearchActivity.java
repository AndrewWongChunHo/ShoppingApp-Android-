package com.example.project_text.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_text.R;
import com.example.project_text.adpaters.MyAdapter;
import com.example.project_text.fragments.HomeActivity;
import com.example.project_text.models.item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    TextView textView;
    DatabaseReference ref;
    ArrayList<item> list;
    RecyclerView recyclerView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ref = FirebaseDatabase.getInstance().getReference().child("Item");
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);

        //Move to the previous page when clicking cancel button
        textView = findViewById(R.id.cancel_button);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
            }
        });
    }

    //Search function through database
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
                        MyAdapter myAdapter = new MyAdapter(list);
                        recyclerView.setAdapter(myAdapter);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        //OnQueryTextListener is essential in search function
        if (searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }

    //Search in Lowercase
    private void search(String str){
        ArrayList<item> myList = new ArrayList<>();
        for(item object : list){
            if(object.getName().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
        }
        MyAdapter myAdapter = new MyAdapter(myList);
        recyclerView.setAdapter(myAdapter);

    }

}




