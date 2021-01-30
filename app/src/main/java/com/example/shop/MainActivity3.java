package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import Model.Data;

public class MainActivity3 extends AppCompatActivity {

    private RecyclerView prd_recycler,prd_recycler2;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference,reference2;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mAuth = FirebaseAuth.getInstance();
        //αν δεν είναι συνδεδεμένος πήγαινε στο login
        if( mAuth.getCurrentUser()==null) {
            updateUI(LoginActivity.class);
            finish();
        }
        prd_recycler = findViewById(R.id.recycler);
       prd_recycler2 = findViewById(R.id.recycler2);


        LinearLayoutManager lm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        prd_recycler.setHasFixedSize(true);
        prd_recycler.setLayoutManager(lm);

        LinearLayoutManager lm2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        prd_recycler2.setHasFixedSize(true);
        prd_recycler2.setLayoutManager(lm2);

        reference = db.getReference().child("Buffan");
        reference2 = db.getReference().child("T-Shirts");


    }

  public void onStart() {

        super.onStart();
        FirebaseRecyclerAdapter<Data,ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Data, ProductViewHolder>
                (
                        Data.class,
                        R.layout.product_data,
                        ProductViewHolder.class,
                        reference
                ) {
            @Override
            protected void populateViewHolder(ProductViewHolder productViewHolder, Data data, int i) {
                       productViewHolder.setTitle(data.getTitle());
                productViewHolder.setPrice(data.getPrice());
                productViewHolder.setImage(data.getImage());
                productViewHolder.setAmmount(data.getAmmount());

                 productViewHolder.mview.setOnClickListener(new View.OnClickListener()
                 {

                     @Override
                     public void onClick(View v) {
                         Intent intent = new Intent(MainActivity3.this,BuffanDetails.class);
                         intent.putExtra("title",data.getTitle());
                         intent.putExtra("price",data.getPrice());
                         intent.putExtra("image",data.getImage());
                         intent.putExtra("amount",data.getAmmount());
                         startActivity(intent);

                     }
                 });
            }
        };

        prd_recycler.setAdapter(adapter);


      FirebaseRecyclerAdapter<Data,ProductViewHolder> adapter2 = new FirebaseRecyclerAdapter<Data, ProductViewHolder>
              (
                      Data.class,
                      R.layout.product_data,
                      ProductViewHolder.class,
                      reference2
              ) {
          @Override
          protected void populateViewHolder(ProductViewHolder productViewHolder2, Data data, int i) {
              productViewHolder2.setTitle(data.getTitle());
              productViewHolder2.setPrice(data.getPrice());
              productViewHolder2.setImage(data.getImage());
              productViewHolder2.setAmmount(data.getAmmount());
              productViewHolder2.mview.setOnClickListener(new View.OnClickListener()
              {

                  @Override
                  public void onClick(View v) {
                      Intent intent = new Intent(MainActivity3.this,TshirtsDetails.class);

                      intent.putExtra("title",data.getTitle());
                      intent.putExtra("price",data.getPrice());
                      intent.putExtra("image",data.getImage());
                      intent.putExtra("amount",data.getAmmount());
                      startActivity(intent);

                  }
              });
          }




      };

      prd_recycler2.setAdapter(adapter2);
    }




    //αλλάζει activity
    private void updateUI(Class activity){
        Intent intent = new Intent(this,activity);
        // intent.putExtra("stringKey1","Your message here!");
        startActivity(intent);

    }


}