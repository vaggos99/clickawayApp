package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OwnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
    }

    public void edit(View view)
    {
        startActivity(new Intent(getApplicationContext(), EditInfoActivity.class));
        finish();
    }

    public void logout(View view)
    {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void addProduct(View view)
    {
        startActivity(new Intent(getApplicationContext(), AddProductActivity.class));
        finish();
    }

}