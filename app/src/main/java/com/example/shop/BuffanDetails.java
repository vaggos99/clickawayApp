package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class BuffanDetails extends AppCompatActivity {

    ImageView img;
    TextView title,price,posot,am;
    Button add;
    int count;
    FirebaseDatabase db;
     DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffan_details);

        img=findViewById(R.id.image);
        title=findViewById(R.id.title_dt);
        price=findViewById(R.id.price_dt);
        posot=findViewById(R.id.textView6);
        am=findViewById(R.id.posotita);
        add=findViewById(R.id.button4);
        db = FirebaseDatabase.getInstance();

        count=0;

        Intent intent = getIntent();
        String mtitle=intent.getStringExtra("title");
        String mprice=intent.getStringExtra("price");
        String a=intent.getStringExtra("amount");
        String mimage=intent.getStringExtra("image");

        title.setText(mtitle);
        price.setText(mprice);
        posot.setText(a);

        Picasso.get().load(mimage).networkPolicy(NetworkPolicy.OFFLINE).into(img, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

                Picasso.get().load(mimage).into(img);

            }
        });


    }

    public void addToCart(View view)
    {
        Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_LONG).show();
      
    }

    public void addProduct(View view)
    {
        count = count + 1;
        String a = String.valueOf(count);
        am.setText(a);

    }
    public void removeProduct(View view)
    {
        count = count - 1;
        String a = String.valueOf(count);
        am.setText(a);

    }
}