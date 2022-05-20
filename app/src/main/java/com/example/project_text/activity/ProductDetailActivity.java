package com.example.project_text.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_text.R;
import com.example.project_text.models.item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class ProductDetailActivity extends AppCompatActivity {

    TextView quantity;
    int totalQuantity = 1;
    int totalPrice = 0;
    ImageView detailedImg;
    TextView name, brand, description, price;
    ImageView addItem, removeItem;
    Button addToCart;
    com.example.project_text.models.item item = null;
    FirebaseFirestore fireStore;
    FirebaseAuth firebaseAuth;

    //Display the toast message when the option is selected
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        //Go back to previous page
        ImageButton imageButton = findViewById(R.id.BackToShop);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Firebase
        fireStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        //Call back the data
        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof item){
            item = (item) object;
        }

        quantity = findViewById(R.id.quantity);
        detailedImg = findViewById(R.id.productImage);
        name = findViewById(R.id.productName);
        brand = findViewById(R.id.productBrand);
        price = findViewById(R.id.productPrice);
        description = findViewById(R.id.productDescription);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);

        //get data
        if(item != null){
            Glide.with(getApplicationContext()).load(item.getImage()).into(detailedImg);
            description.setText(item.getDescription());
            name.setText(item.getName());
            brand.setText(item.getBrand());
            price.setText("$" + item.getPrice());

            totalPrice = item.getPrice() * totalQuantity;
        }

        //Add to Cart
        addToCart = findViewById(R.id.add_to_cart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToCart();
            }
        });

        //Quantity button
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = item.getPrice() * totalQuantity;
                }

            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = item.getPrice() * totalQuantity;
                }
            }
        });

    }

    //Pass data to the FireStore
    private void addedToCart() {
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", item.getName());
        cartMap.put("productBrand", item.getBrand());
        cartMap.put("productImage", item.getImage());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        //build the path in FirebaseStore and show toast message after the action completes
        fireStore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(ProductDetailActivity.this, "Added to Cart", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

}