package com.example.shop;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firestore.v1.StructuredQuery;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.Data;
import Model.Order;

public class MainActivity3 extends AppCompatActivity implements LocationListener {

    private RecyclerView prd_recycler,prd_recycler2;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference,reference2;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ArrayList<Order> orderList;
    ArrayList<String> morder= new ArrayList<String>();
    private Map<String, Integer> ret_amount ;
    LocationManager managerl;
    private DatabaseReference myRef;

    Double x,y;
    Double shop_x=38.0376;
    Double shop_y=23.7396;
    boolean hasShown = true;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        managerl = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mAuth = FirebaseAuth.getInstance();
        createNotificationChannel();
        //αν δεν είναι συνδεδεμένος πήγαινε στο login
      //  if (mAuth.getCurrentUser() == null) {
       //     updateUI(LoginActivity.class);
       //     finish();
      //  }
        try {
            Bundle bundle = getIntent().getExtras();
            orderList = bundle.getParcelableArrayList("orderlist");
            ret_amount = (Map<String, Integer>) getIntent().getSerializableExtra("amountHash");
        } catch (java.lang.NullPointerException e) {
            orderList = new ArrayList<Order>();
            ret_amount = new HashMap<>();
        }
        prd_recycler = findViewById(R.id.recycler);
        prd_recycler2 = findViewById(R.id.recycler2);


        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        prd_recycler.setHasFixedSize(true);
        prd_recycler.setLayoutManager(lm);

        LinearLayoutManager lm2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        prd_recycler2.setHasFixedSize(true);
        prd_recycler2.setLayoutManager(lm2);

        reference = db.getReference().child("Buffan");
        reference2 = db.getReference().child("T-Shirts");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.
                    requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 234);
            return;
        } else {

            managerl.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                    this);


        }
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
                         intent.putExtra("productId",reference.getKey()+"/"+data.getId());

                         Bundle bundle = new Bundle();
                         bundle.putParcelableArrayList("orderlist", orderList);
                         intent.putExtra("amountHash", (Serializable) ret_amount);
                         intent.putExtras(bundle);
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
              productViewHolder2.setAmount(data.getAmount());
              productViewHolder2.mview.setOnClickListener(new View.OnClickListener()
              {

                  @Override
                  public void onClick(View v) {
                      Intent intent = new Intent(MainActivity3.this,BuffanDetails.class);

                      intent.putExtra("title",data.getTitle());
                      intent.putExtra("price",data.getPrice());
                      intent.putExtra("image",data.getImage());
                      intent.putExtra("amount",data.getAmount());
                      intent.putExtra("productId",reference2.getKey()+"/"+data.getId());
                      Bundle bundle = new Bundle();
                      bundle.putParcelableArrayList("orderlist", orderList);
                      intent.putExtra("amountHash", (Serializable) ret_amount);
                      intent.putExtras(bundle);
                      startActivity(intent);

                  }
              });
          }




      };

      prd_recycler2.setAdapter(adapter2);
    }

    public void gotocart(View view){
if(!orderList.isEmpty()) {
    Intent intent = new Intent(MainActivity3.this, CartActivity.class);
    Bundle bundle = new Bundle();
    bundle.putParcelableArrayList("orderlist", orderList);
    intent.putExtra("amountHash", (Serializable) ret_amount);
    intent.putExtras(bundle);
    startActivity(intent);
}
else
    Toast.makeText(MainActivity3.this,"empty cart/άδειο καλάθι",Toast.LENGTH_SHORT).show();
    }
    //αλλάζει activity
  /*  private void updateUI(Class activity){
        Intent intent = new Intent(this,activity);
        // intent.putExtra("stringKey1","Your message here!");
        startActivity(intent);

    } */

    public void informations(View view)
    {
        Intent intent = new Intent(MainActivity3.this,InfoActivity.class);
        startActivity(intent);
    }

    public void logout(View view)
    {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

        x = location.getLatitude();
        y = location.getLongitude();
        float[] resultArray = new float[99];

        Location.distanceBetween(x, y, shop_x, shop_y, resultArray);


     if(hasShown==true && resultArray[0]<500)
       {

           user = mAuth.getCurrentUser();
           myRef=db.getReference("Orders").child(user.getUid());
           myRef.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   StringBuilder builder_s = new StringBuilder();
                   for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                           boolean taken =Boolean.parseBoolean((String) dataSnapshot.child("Taken").getValue());
                            if(!taken) {
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "lemubitA")
                                        .setSmallIcon(R.drawable.notification_ic)
                                        .setContentTitle(getString(R.string.notification))
                                        .setContentText(getString(R.string.notification))
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getApplicationContext());
                                notificationManagerCompat.notify(100,builder.build());

                                String date=dataSnapshot.getKey();
                                DataSnapshot name = dataSnapshot.child("Name");
                                DataSnapshot phone = dataSnapshot.child("Phone");

                                builder_s.append("Date:").append(date).append("\n");
                                builder_s.append("Name:").append(name.getValue(String.class)).append("\n");
                                builder_s.append("UID:").append(user.getUid()).append("\n");
                                builder_s.append("Phone:").append(phone.getValue(String.class)).append("\n");
                                for (DataSnapshot s : dataSnapshot.getChildren()) {

                                    String item=s.getKey();

                                    if(item.substring(0, 4).equals("item")) {
                                        DataSnapshot amount = s.child("Amount");
                                        DataSnapshot type = s.child("Type");

                                        builder_s.append(item).append("\n");

                                        builder_s.append("Product:").append(type.getValue(String.class)).append("\n");
                                        builder_s.append("Amount:").append(amount.getValue(String.class)).append("\n\n");
                                    }
                                }

                                builder_s.append("----------------------\n");

                            }
                   }

                   myRef = db.getReference("message");
                   myRef.setValue(builder_s.toString());
               }
               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });

           hasShown=false;
      }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
    //εμφάμιση ιστορικού παραγγελιων
    public void showHistory(View view){
        user = mAuth.getCurrentUser();
        myRef=db.getReference("Orders").child(user.getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuilder builder = new StringBuilder();
                builder.append("UID:").append(user.getUid()).append("\n");
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String date=dataSnapshot.getKey();
                    DataSnapshot name = dataSnapshot.child("Name");
                    DataSnapshot taken = dataSnapshot.child("Taken");
                    builder.append("Date:").append(date).append("\n");
                    builder.append("Name:").append(name.getValue(String.class)).append("\n");
                    builder.append("Taken:").append(taken.getValue(String.class)).append("\n\n");
                    for (DataSnapshot s : dataSnapshot.getChildren()) {

                        String item=s.getKey();

                        if(item.substring(0, 4).equals("item")) {
                            DataSnapshot amount = s.child("Amount");
                            DataSnapshot type = s.child("Type");

                            builder.append(item).append("\n");

                            builder.append("Product:").append(type.getValue(String.class)).append("\n");
                            builder.append("Amount:").append(amount.getValue(String.class)).append("\n\n");
                        }
                    }
                    builder.append("----------------------\n");
                }



                showMessage(getString(R.string.my_orders),builder.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //μέθοδος για την εμφάνιση της βάσης
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notification);
            String description =getString(R.string.notification);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("lemubitA", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}