package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OwnerActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText message,uid,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        mAuth = FirebaseAuth.getInstance();
        message=findViewById(R.id.editTextTextMultiLine);
        uid=findViewById(R.id.uidText);
        date=findViewById(R.id.dateText);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                message.setText(message.getText().toString()+"\n"+snapshot.getValue(String.class));
                String string = message.getText().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void orderTaken(View view)
    {
        if(uid.getText().toString()!=null && date.getText().toString()!=null) {
            myRef = database.getReference("Orders").child(uid.getText().toString()).child(date.getText().toString()).child("Taken");
            myRef.setValue("true");
            Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_LONG).show();
            String string = message.getText().toString();
            String[] parts = string.split("----------------------\n");
            String new_message="";
            for (String p:parts){
                if(!p.contains(uid.getText())||!p.contains(date.getText().toString())){
                    new_message+=p+"----------------------\n";
                }
            }
            message.setText(new_message);
        }
        else
            Toast.makeText(getApplicationContext(), "You have to put UID and date of order", Toast.LENGTH_LONG).show();
    }

    public void notTaken(View view)
    {
        if(uid.getText().toString()!=null && date.getText().toString()!=null) {
            myRef = database.getReference("Orders").child(uid.getText().toString()).child(date.getText().toString()).child("Taken");
            myRef.setValue("false");
            Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(getApplicationContext(), "You have to put UID and date of order", Toast.LENGTH_LONG).show();
    }



    public void edit(View view)
    {
        startActivity(new Intent(getApplicationContext(), EditInfoActivity.class));
        finish();
    }

    public void logout(View view)
    {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }


    public void addProduct(View view)
    {
        startActivity(new Intent(getApplicationContext(), AddProductActivity.class));
        finish();
    }
    public void showOrders(View view){

        myRef=database.getReference("Orders");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuilder builder = new StringBuilder();
                for (DataSnapshot datasnap : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot : datasnap.getChildren()) {
                        boolean taken = Boolean.parseBoolean((String) dataSnapshot.child("Taken").getValue());
                        if (!taken) {
                            String date = dataSnapshot.getKey();
                            DataSnapshot name = dataSnapshot.child("Name");
                            DataSnapshot phone = dataSnapshot.child("phone");
                            builder.append("Date:").append(date).append("\n");
                            builder.append("Name:").append(name.getValue(String.class)).append("\n\n");
                            builder.append("Phone:").append(phone.getValue(String.class)).append("\n");
                            for (DataSnapshot s : dataSnapshot.getChildren()) {

                                String item = s.getKey();

                                if (item.substring(0, 4).equals("item")) {
                                    DataSnapshot amount = s.child("Amount");
                                    DataSnapshot type = s.child("Type");

                                    builder.append(item).append("\n");

                                    builder.append("Product:").append(type.getValue(String.class)).append("\n");
                                    builder.append("Amount:").append(amount.getValue(String.class)).append("\n\n");
                                }
                            }
                            builder.append("----------------------\n");
                        }



                    }
                }
                showMessage(getString(R.string.my_orders), builder.toString());
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
}