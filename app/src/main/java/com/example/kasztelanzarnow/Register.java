package com.example.kasztelanzarnow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kasztelanzarnow.Common.Common;
import com.example.kasztelanzarnow.model.Team;
import com.example.kasztelanzarnow.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {

    MaterialEditText editPhone,editLogin,editPassword, editEmail;

    Button btsign;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editLogin=(MaterialEditText)findViewById(R.id.login);
        editPassword=(MaterialEditText)findViewById(R.id.password);
        ////////////////////////////////////////////////
        editPhone=(MaterialEditText)findViewById(R.id.phone);
        editEmail = (MaterialEditText)findViewById(R.id.email);



        btsign=(Button)findViewById(R.id.btsign1);

        final FirebaseDatabase database =FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

        btsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editPhone.length() == 9 && editLogin.length()!=0 && editPassword.length() >= 5 && validateEmail()) {
                    final ProgressDialog mDialog = new ProgressDialog(Register.this);
                    mDialog.setMessage("Please Waiting....");
                    mDialog.show();


                    table_user.addListenerForSingleValueEvent(new ValueEventListener() {


                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(editPhone.getText().toString()).exists()) {
                                mDialog.dismiss();
                                Toast.makeText(Register.this, "Jest juz taki telefon w bazie", Toast.LENGTH_SHORT).show();

                            } else {
                                mDialog.dismiss();
                                User user = new User(editLogin.getText().toString(), editPassword.getText().toString(), editPhone.getText().toString(), editEmail.getText().toString());
                                table_user.child(editPhone.getText().toString()).setValue(user);
                                //table_user.child("tabl").
                                Toast.makeText(Register.this, "Rejestracja Pomyślna", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent homeIntent = new Intent(Register.this, Home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }


                    });


                }else
                {
                    Toast.makeText(Register.this, "WPROWADZ DANE !", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

    private boolean validateEmail() {
        String emailInput = editEmail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            editEmail.setError("Pole nie moze byc puste!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            editEmail.setError("Wprowadz poprawny adres email");
            return false;
        } else {
            editEmail.setError(null);
            return true;
        }
    }
}