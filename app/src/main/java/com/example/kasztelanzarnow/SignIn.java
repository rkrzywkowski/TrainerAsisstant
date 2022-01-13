package com.example.kasztelanzarnow;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




import com.example.kasztelanzarnow.Common.Common;
import com.example.kasztelanzarnow.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

public class SignIn extends AppCompatActivity {

    EditText editLogin,editPassword;

    Button btnSign1;


    com.rey.material.widget.CheckBox Remember;

    TextView txtForgotPassword;

    FirebaseDatabase database;
    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editLogin=(MaterialEditText)findViewById(R.id.phone);
        editPassword=(MaterialEditText)findViewById(R.id.password);

        btnSign1=(Button) findViewById(R.id.btsign1);
        Remember=(com.rey.material.widget.CheckBox) findViewById(R.id.Remember);
        txtForgotPassword=(TextView)findViewById(R.id.txtForgotPwd);

        Paper.init(this);
        database =FirebaseDatabase.getInstance();
        table_user=database.getReference("User");

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPwDialog();
            }
        });


        btnSign1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Save user &password

                if(Remember.isChecked())
                {
                    Paper.book().write(Common.USER_KEY,editLogin.getText().toString());
                    Paper.book().write(Common.PWD_KEY,editPassword.getText().toString());


                }
                if (editLogin.length() != 0) {

                    final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                    mDialog.setMessage("Please Waiting....");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            if (dataSnapshot.child(editLogin.getText().toString()).exists()) {

                                mDialog.dismiss();

                                User user = dataSnapshot.child(editLogin.getText().toString()).getValue(User.class);

                                user.setPhone(editLogin.getText().toString()); //Set phone


                                if (user.getPassword().equals(editPassword.getText().toString())) {
                                    Intent homeIntent = new Intent(SignIn.this, Home.class);
                                    Common.currentUser = user;
                                    startActivity(homeIntent);
                                    finish();

                                } else {
                                    Toast.makeText(SignIn.this, "Złe hasło !!!", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SignIn.this, "Użytkownik nie istnieje", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else
                {
                    Toast.makeText(SignIn.this, "WPROWADZ DANE !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showForgotPwDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Zapomniałeś hasła?");
        builder.setMessage("Podaj Rok urodzin");
        LayoutInflater inflater = this.getLayoutInflater();
        View forgot_view = inflater.inflate(R.layout.forgot_password_layout, null);
        builder.setView(forgot_view);
        builder.setIcon(R.drawable.ic_security_black_24dp);

        final MaterialEditText phone = (MaterialEditText) forgot_view.findViewById(R.id.phone);


        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Check if user available
                if (phone.length() != 0) {
                    table_user.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.child(phone.getText().toString()).getValue(User.class);

                            if (user.getPhone().equals(phone.getText().toString()))
                                Toast.makeText(SignIn.this, "Twoje hasło:  " + user.getPassword(), Toast.LENGTH_LONG).show();

                            phone.setText("");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                }else
                {
                    Toast.makeText(SignIn.this, "WPROWADZ DANE !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();

    }



}

