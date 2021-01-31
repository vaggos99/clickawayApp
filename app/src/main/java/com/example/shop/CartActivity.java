package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import Model.Order;

public class CartActivity extends AppCompatActivity {
    private ArrayList<Order> orderList;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        Bundle bundle = getIntent().getExtras();
        orderList = bundle.getParcelableArrayList("orderlist");
        if (orderList.size()>0){
            Toast.makeText(getApplicationContext(), "list to buy", Toast.LENGTH_LONG).show();
        }
    }

   public void complete_purchase(View view){
       String currentTime = Calendar.getInstance().getTime().toString();


       int i=1;
       for (Order item:orderList) {
           myRef = database.getReference("Orders").child(user.getUid()).child(currentTime).child("item"+String.valueOf(i));

           myRef.child("Type").setValue(item.getTitle());
           myRef.child("Amount").setValue(item.getAmount());

           i++;
       }
       Toast.makeText(getApplicationContext(), "Your order is completed", Toast.LENGTH_LONG).show();
orderList.removeAll(orderList);
       Intent intent2 = new Intent(getApplicationContext(),MainActivity3.class);
       Bundle bundle = new Bundle();

       bundle.putParcelableArrayList("orderlist",  orderList);
       intent2.putExtras(bundle);
       startActivity(intent2);
   }
}