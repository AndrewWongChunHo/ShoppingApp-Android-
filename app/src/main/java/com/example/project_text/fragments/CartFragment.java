package com.example.project_text.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.project_text.R;
import com.example.project_text.activity.Product;
import com.example.project_text.adpaters.MyCartAdapter;
import com.example.project_text.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    MyCartAdapter myCartAdapter;
    List<MyCartModel> cartModelList;
    TextView overTotalAmount;
    View v;

   //Empty Constructor is necessary
    public CartFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_cart, container, false);

        //Back to previous page
        ImageButton imageButton = v.findViewById(R.id.back_cart);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), HomeActivity.class);
                startActivity(i);
            }
        });

        //define database
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        //Calculate the total amount
        overTotalAmount = v.findViewById(R.id.AllTotalPrice);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, new IntentFilter("MyTotalAmount"));

        //Set Adapter
        recyclerView = v.findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartModelList = new ArrayList<>();
        myCartAdapter = new MyCartAdapter(cartModelList);
        recyclerView.setAdapter(myCartAdapter);

        //Pass data to the cart fragment
        db.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        String documentId = documentSnapshot.getId();
                        MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                        cartModel.setDocumentId(documentId);
                        cartModelList.add(cartModel);
                        myCartAdapter.notifyDataSetChanged();
                    }
                }

            }
        });

        return v;
    }

    //Set the Text of total amount
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalPrice = intent.getIntExtra("totalAmount", 0);
            overTotalAmount.setText("Total: $"+totalPrice);

        }
    };
}