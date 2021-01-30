package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class TshirtsDetails extends AppCompatActivity {

    ImageView img;
    TextView title;
    TextView price;
    TextView posot;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tshirts_details);

        img=findViewById(R.id.image);
        title=findViewById(R.id.title_dt);
        price=findViewById(R.id.price_dt);
        posot=findViewById(R.id.posotita);

        Intent intent = getIntent();
        String mtitle=intent.getStringExtra("title");
        String mprice=intent.getStringExtra("price");
        String mimage=intent.getStringExtra("image");
        String a=intent.getStringExtra("amount");
        title.setText(mtitle);
        price.setText(mprice);

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

    public void amount(View view)
    {
        count = count + 1;
        String a = String.valueOf(count);
        posot.setText(a);

    }
}