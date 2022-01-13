package com.example.kasztelanzarnow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kasztelanzarnow.Common.Common;
import com.example.kasztelanzarnow.adapter.ItemPlayerAdapter;
import com.example.kasztelanzarnow.model.Player;
import com.example.kasztelanzarnow.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class TeamDetail extends AppCompatActivity {


    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference().child("User");
    static String key = new String("");
    User user = Common.currentUser;

    String phone = Common.currentUser.getPhone();
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    RecyclerView recyclerView;
    ItemPlayerAdapter itemPlayerAdapter;
    TextView textViewTeam;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("","destroy Detail");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("", "resume Detail");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //        Intent i = new Intent(this, TeamDetail.class);
        //        startActivity(i);
        //        finish();
        Log.d("","pause Detail");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("", "restart Detail");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);

        textViewTeam = (TextView) findViewById(R.id.textViewTeam);
        textViewTeam.setText(Home.nazwaDruzyny);



        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTeam);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Player> options =
                new FirebaseRecyclerOptions.Builder<Player>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(phone).child("TeamName").child(Home.nazwaDruzyny).child("players"), Player.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Player, PlayerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final PlayerViewHolder holder, int position, @NonNull final Player model) {

                holder.name.setText(model.getName() + " " + model.getSurname());

                holder.setOnItemClickListener(new PlayerViewHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //System.out.println("click " + holder.name.getText().toString());
                        Player player = model;
                       // System.out.println("player " + player.getAmountMatches() + "----" + model.getAmountMatches());
                        Intent i = new Intent(TeamDetail.this, SinglePlayer.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("player", player);
//                        int o = player.getAmountMatches();
                       // bundle.putParcelable("matches", player.getAmountMatches());
                        i.putExtras(bundle);
                        startActivity(i);
                    }

                    @Override
                    public void onItemlongClick(View view, int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(TeamDetail.this);
                        String [] options = {"Usuń" };
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if( i == 0){
                                    Query queryDeletePlayer = table_user.child(phone)
                                            .child("TeamName")
                                            .child(Home.nazwaDruzyny)
                                            .child("players")
                                            .orderByChild("phoneNumber")
                                            .equalTo(model.getPhoneNumber());
                                    queryDeletePlayer.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                                ds.getRef().removeValue();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        });
                        builder.create().show();
                    }
                });



            }


            @NonNull
            @Override
            public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent,false);
                return new PlayerViewHolder(view);
            }
        };



//            itemPlayerAdapter = new ItemPlayerAdapter(options);
//            recyclerView.setAdapter(itemPlayerAdapter);
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);


        Button buttonAddPlayer = (Button) findViewById(R.id.addPlayer);
        buttonAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddPlayerFragment();
                // show();
            }
        });



    }

    @Override
    protected void onStart(){
        super.onStart();
        //itemPlayerAdapter.startListening();
        Log.d("", "onStart Detail");
    }

    @Override
    protected void onStop(){
        super.onStop();
        // itemPlayerAdapter.stopListening();
        Log.d("", "onStop Detail");
    }


    private void showAddPlayerFragment() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Podaj dane Zawodnika");
        LayoutInflater inflater = this.getLayoutInflater();
        final View add_player_detail = inflater.inflate(R.layout.add_player_detail_layout, null);
        builder.setView(add_player_detail);


        // Button addTeamDetail = (Button) findViewById(R.id.addTeamDetail);



        builder.setNeutralButton("Dodaj Zawodnika", new DialogInterface.OnClickListener() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {
                MaterialEditText playerName = (MaterialEditText) add_player_detail.findViewById(R.id.playerName);
                MaterialEditText playerSurname = (MaterialEditText) add_player_detail.findViewById(R.id.playerSurname);
                MaterialEditText playerPhoneNumber = (MaterialEditText) add_player_detail.findViewById(R.id.playerPhoneNumber);
                MaterialEditText playerDateOfBirth = (MaterialEditText) add_player_detail.findViewById(R.id.playerDateOfBirth);

                Player player = new Player(
                        playerName.getText().toString(),
                        playerSurname.getText().toString(),
                        playerDateOfBirth.getText().toString(),
                        playerPhoneNumber.getText().toString(),
                        "p");

                ChildEventListener childEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        MaterialEditText playerName = (MaterialEditText) add_player_detail.findViewById(R.id.playerName);
                        MaterialEditText playerSurname = (MaterialEditText) add_player_detail.findViewById(R.id.playerSurname);
                        MaterialEditText playerDateOfBirth = (MaterialEditText) add_player_detail.findViewById(R.id.playerDateOfBirth);
                        Player player = dataSnapshot.getValue(Player.class);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };

                table_user.child(phone).child("TeamName").child(Home.nazwaDruzyny).child("players").addChildEventListener(childEventListener);
                table_user.child(phone).child("TeamName").child(Home.nazwaDruzyny).child("players").push().setValue(player);



            }


        });


        builder.show();

    }


}