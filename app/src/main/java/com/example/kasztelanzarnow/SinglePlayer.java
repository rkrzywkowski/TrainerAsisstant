package com.example.kasztelanzarnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.internal.platform.Platform;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.kasztelanzarnow.Common.Common;
import com.example.kasztelanzarnow.model.Player;
import com.example.kasztelanzarnow.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class SinglePlayer extends AppCompatActivity implements ExampleDialog.ExampleDialogListener, NumberPicker.OnValueChangeListener  {

    Player p;
    Player player1;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference().child("User");
    static String key = new String("");
    User user = Common.currentUser;
    String phone = Common.currentUser.getPhone();

    TextView name, amountMatches, amountTrainings, amountEvents;
    TextView mark, goal, yellowCard, redCard;
    TextView markPlayer, goalPlayer, yellowCardPlayer, redCardPlayer;
    List<Player> players = new ArrayList<>();

    int num = 0;
    int choiceCase = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        name = (TextView) findViewById(R.id.textViewPlayer);
        amountMatches = (TextView) findViewById(R.id.matchAmount);
        amountTrainings = (TextView) findViewById(R.id.trainingAmount);
        amountEvents = (TextView) findViewById(R.id.eventAmount);

        mark = (TextView) findViewById(R.id.mark);
        markPlayer = (TextView) findViewById(R.id.markPlayer);
        goal = (TextView) findViewById(R.id.goal);
        goalPlayer = (TextView) findViewById(R.id.goalPlayer);
        yellowCard = (TextView) findViewById(R.id.yellowCard);
        yellowCardPlayer = (TextView) findViewById(R.id.yellowCardPlayer);
        redCard = (TextView) findViewById(R.id.redCard);
        redCardPlayer = (TextView) findViewById(R.id.redCardPlayer);

        Bundle bundle = getIntent().getExtras();

        p = (Player) bundle.getParcelable("player");

                table_user.child(phone)
                .child("TeamName")
                .child(Home.nazwaDruzyny)
                .child("players").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Player player = ds.getValue(Player.class);


                            if (player.getPhoneNumber().equals(p.getPhoneNumber())) {
                                name.setText(player.getName() + " " + player.getSurname());
                                amountMatches.setText(String.valueOf(player.getAmountMatches()));
                                amountTrainings.setText(String.valueOf(player.getAmountTrainings()));
                                amountEvents.setText(String.valueOf(player.getAmountEvents()));
                                if(player.getAmountMatches() > 0) {
                                    markPlayer.setText(String.valueOf(player.getAverageMark() / player.getAmountMatches()));
                                }
                                else {
                                    markPlayer.setText("0");
                                }
                                if(player.getAmountMatches() > 0) {
                                    goalPlayer.setText(String.valueOf(player.getAmountGoals() / player.getAmountMatches()));
                                }
                                else{
                                    goalPlayer.setText("0");
                                }
                                yellowCardPlayer.setText(String.valueOf(player.getAmountYellowCard()));
                                redCardPlayer.setText(String.valueOf(player.getAmountRedCard()));

                            }

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                mark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        choiceCase = 1;
                        updateStats("Ocena");
                    }
                });


                goal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        choiceCase = 2;
                        updateStats("Bramki");
                    }
                });

                yellowCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        choiceCase = 3;
                        updateStats("Żółte karki");
                    }
                });


                redCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        choiceCase = 4;
                        updateStats("Czerwone kartki");
                    }
                });


    }



    @Override
    protected void onStart() {
        super.onStart();
        //name.setText(player.getName() + " " + player.getSurname());

    }


    void updateStats(String textFromClick){

        ExampleDialog exampleDialog = new ExampleDialog(textFromClick);
        exampleDialog.show(getSupportFragmentManager(), "example dialog");


    }

    @Override
    public void applyTexts(final int number) {
//                textViewUsername.setText(username);
//                textViewPassword.setText(password);

        if(choiceCase == 1){
            Query queryUpdate = table_user.child(phone)
                    .child("TeamName")
                    .child(Home.nazwaDruzyny)
                    .child("players").orderByChild("phoneNumber").equalTo(p.getPhoneNumber());
            queryUpdate.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        Player player = ds.getValue(Player.class);
//                        ds.getRef().child("teamName").setValue(teamName.getText().toString());
                        double sum = player.getAverageMark() + number;

                        ds.getRef().child("averageMark").setValue(sum);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            choiceCase = 0;
        }
        else if(choiceCase == 2){
            Query queryUpdate = table_user.child(phone)
                    .child("TeamName")
                    .child(Home.nazwaDruzyny)
                    .child("players").orderByChild("phoneNumber").equalTo(p.getPhoneNumber());
            queryUpdate.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        Player player = ds.getValue(Player.class);
//                        ds.getRef().child("teamName").setValue(teamName.getText().toString());
                        double sum = player.getAmountGoals() + number;

                        ds.getRef().child("amountGoals").setValue(sum);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            choiceCase = 0;
        }
        else if(choiceCase == 3){
           // yellowCardPlayer.setText(String.valueOf(number));
            Query queryUpdate = table_user.child(phone)
                    .child("TeamName")
                    .child(Home.nazwaDruzyny)
                    .child("players").orderByChild("phoneNumber").equalTo(p.getPhoneNumber());
            queryUpdate.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        Player player = ds.getValue(Player.class);
//                        ds.getRef().child("teamName").setValue(teamName.getText().toString());

                        ds.getRef().child("amountYellowCard").setValue(number);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            choiceCase = 0;
        }
        else if(choiceCase == 4){
            //redCardPlayer.setText(String.valueOf(number));
            Query queryUpdate = table_user.child(phone)
                    .child("TeamName")
                    .child(Home.nazwaDruzyny)
                    .child("players").orderByChild("phoneNumber").equalTo(p.getPhoneNumber());
            queryUpdate.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        Player player = ds.getValue(Player.class);
//                        ds.getRef().child("teamName").setValue(teamName.getText().toString());

                        ds.getRef().child("amountRedCard").setValue(number);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            choiceCase = 0;
        }

    }


    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

    }

}