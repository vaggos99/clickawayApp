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
                productViewHolder.setAmount(data.getAmount());

                 productViewHolder.mview.setOnClickListener(new View.OnClickListener()
                 {

                     @Override
                     public void onClick(View v) {
                         Intent intent = new Intent(MainActivity3.this,BuffanDetails.class);
                         intent.putExtra("title",data.getTitle());
                         intent.putExtra("price",data.getPrice());
                         intent.putExtra("image",data.getImage());
                         intent.putExtra("amount",data.getAmount());
                         startActivity(intent);

                     }
                 });
            }
        };

        prd_recycler.setAdapter(adapter);


      FirebaseRecyclerAdapter<Data,ProductViewHolder2> adapter2 = new FirebaseRecyclerAdapter<Data, ProductViewHolder2>
              (
                      Data.class,
                      R.layout.product_data,
                      ProductViewHolder2.class,
                      reference2
              ) {
          @Override
          protected void populateViewHolder(ProductViewHolder2 productViewHolder2, Data data, int i) {
              productViewHolder2.setTitle(data.getTitle());
              productViewHolder2.setPrice(data.getPrice());
              productViewHolder2.setImage(data.getImage());

              productViewHolder2.mview.setOnClickListener(new View.OnClickListener()
              {

                  @Override
                  public void onClick(View v) {
                      Intent intent = new Intent(MainActivity3.this,TshirtsDetails.class);
                      intent.putExtra("title",data.getTitle());
                      intent.putExtra("price",data.getPrice());
                      intent.putExtra("image",data.getImage());
                      startActivity(intent);

                  }
              });
          }




      };

      prd_recycler2.setAdapter(adapter2);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder
    {
       View mview;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mview=itemView;
        }

         public void setTitle(String title)
         {
             TextView mtitle = mview.findViewById(R.id.title);
             mtitle.setText(title);
         }

        public void setAmount(String amount)
        {
            TextView am = mview.findViewById(R.id.ammount);
            am.setText(amount);
        }

        public void setPrice(String price)
        {
            TextView mtitle = mview.findViewById(R.id.price);
            mtitle.setText(price);
        }

        public void setImage(String image)
        {
            ImageView mImage = mview.findViewById(R.id.imageView);
            Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE).into(mImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(image).into(mImage);

                }
            });
        }
    }

    public static class ProductViewHolder2 extends RecyclerView.ViewHolder
    {
        View mview;


        public ProductViewHolder2(@NonNull View itemView) {
            super(itemView);
            mview=itemView;
        }

        public void setTitle(String title)
        {
            TextView mtitle = mview.findViewById(R.id.title);
            mtitle.setText(title);
        }

        public void setPrice(String price)
        {
            TextView mtitle = mview.findViewById(R.id.price);
            mtitle.setText(price);
        }

        public void setImage(String image)
        {
            ImageView mImage = mview.findViewById(R.id.imageView);
            Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE).into(mImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(image).into(mImage);

                }
            });
        }
    }
    //αλλάζει activity
    private void updateUI(Class activity){
        Intent intent = new Intent(this,activity);
        // intent.putExtra("stringKey1","Your message here!");
        startActivity(intent);

    }


}