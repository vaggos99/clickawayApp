package com.example.shop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText email,password,nm;
    Button b;
    TextView login;
    ProgressBar bar;
    FirebaseAuth auth;
    FirebaseFirestore store;
    RadioButton owner,user;
    SharedPreferences pref;
    private static final int REC_RESULT = 653;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        nm=findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        b =findViewById(R.id.button);
        login = findViewById(R.id.textView);
        bar = findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();
        owner = findViewById(R.id.radioButton);
        user = findViewById(R.id.radioButton2);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public void register(View view) {
        String e = email.getText().toString();
        String p = password.getText().toString();

        if (e.isEmpty() || e == null) {
            Toast.makeText(SignupActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
        } else if (e.isEmpty() || e == null) {
            email.setError("Email is required!");
            return;
        } else if (p.length() < 6) {
            password.setError("Password must be at least 6 characters!");
            return;
        } else if (owner.isChecked() == false && user.isChecked() == false) {
            Toast.makeText(SignupActivity.this, "You must select the type", Toast.LENGTH_SHORT).show();
        } else {
            bar.setVisibility(View.VISIBLE);


            auth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    FirebaseUser usr = auth.getCurrentUser();
                    if (task.isSuccessful()) {

                        String n = nm.getText().toString();
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("name", n);
                        editor.apply();
                        if (owner.isChecked()) {
                            DocumentReference r = store.collection("Users").document(usr.getUid());
                            Map<String, Object> userType = new HashMap<>();
                            userType.put("Type", owner.getText().toString());
                            r.set(userType);
                            Toast.makeText(SignupActivity.this, "User Created!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        } else if (user.isChecked()) {
                            DocumentReference r = store.collection("Users").document(usr.getUid());
                            Map<String, Object> userType = new HashMap<>();
                            userType.put("Type", user.getText().toString());
                            r.set(userType);
                            Toast.makeText(SignupActivity.this, "User Created!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();

                        }


                    } else {
                        Toast.makeText(getApplicationContext(),
                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.INVISIBLE);
                    }

                }
            });

        }
    }

    // //Αν πατησει στο textView Login here τοτε μεταφερεται στο MainActivity2 και κλεινει αυτο το activity

    public void login(View view)
    {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void signup(View view)
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please say something!");
        startActivityForResult(intent,REC_RESULT);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REC_RESULT && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches.contains("register") || matches.contains("εγγραφή"))
            {
                String e = email.getText().toString();
                String p = password.getText().toString();

                if (e.isEmpty() || e == null) {
                    Toast.makeText(SignupActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
                } else if (e.isEmpty() || e == null) {
                    email.setError("Email is required!");
                    return;
                } else if (p.length() < 6) {
                    password.setError("Password must be at least 6 characters!");
                    return;
                } else if (owner.isChecked() == false && user.isChecked() == false) {
                    Toast.makeText(SignupActivity.this, "You must select the type", Toast.LENGTH_SHORT).show();
                } else {
                    bar.setVisibility(View.VISIBLE);


                    auth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser usr = auth.getCurrentUser();
                            if (task.isSuccessful()) {

                                String n = nm.getText().toString();
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("name", n);
                                editor.apply();
                                if (owner.isChecked()) {
                                    DocumentReference r = store.collection("Users").document(usr.getUid());
                                    Map<String, Object> userType = new HashMap<>();
                                    userType.put("Type", owner.getText().toString());
                                    r.set(userType);
                                    Toast.makeText(SignupActivity.this, "User Created!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    finish();
                                } else if (user.isChecked()) {
                                    DocumentReference r = store.collection("Users").document(usr.getUid());
                                    Map<String, Object> userType = new HashMap<>();
                                    userType.put("Type", user.getText().toString());
                                    r.set(userType);
                                    Toast.makeText(SignupActivity.this, "User Created!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    finish();

                                }


                            } else {
                                Toast.makeText(getApplicationContext(),
                                        task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                bar.setVisibility(View.INVISIBLE);
                            }

                        }
                    });

                }
            }
            }
        }

}