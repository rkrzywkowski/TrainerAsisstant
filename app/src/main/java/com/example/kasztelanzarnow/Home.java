package com.example.kasztelanzarnow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kasztelanzarnow.Common.Common;
import com.example.kasztelanzarnow.adapter.TeamAdapter;
import com.example.kasztelanzarnow.model.Team;
import com.example.kasztelanzarnow.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;


public class Home extends AppCompatActivity {


    private DrawerLayout mlayout;
    private ActionBarDrawerToggle mtoggle;


    private Button buttonCalendar;
    private Button buttonTimeSheet;

    public static String nazwaDruzyny = "";

    private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference().child("User");

    User user = Common.currentUser;
    private DatabaseReference mPostReference;
    ArrayList<Team> tem;



//    ArrayList<Team> teams = new ArrayList<>(user.getTeams());
    String phone = Common.currentUser.getPhone();



    RecyclerView recview;
    //TeamAdapter adapter;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    public Home() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("","destroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("", "resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("","pause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("", "restart");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mlayout= (DrawerLayout)findViewById(R.id.home_activity);
        mtoggle= new ActionBarDrawerToggle(this, mlayout, R.string.open, R.string.close);
        mlayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        NavigationView mnavigation = (NavigationView) findViewById(R.id.nav_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ustawContent(mnavigation);


        buttonCalendar = (Button) findViewById(R.id.calendar);
        buttonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, Event_.class);
               // Intent i = new Intent(Home.this, Event_.class);
                startActivity(i);
            }
        });


        buttonTimeSheet = (Button) findViewById(R.id.timesheet);
        buttonTimeSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, TimeSheet.class);
                startActivity(i);
            }
        });


        recview=(RecyclerView)findViewById(R.id.recyclerView);
        recview.setLayoutManager(new GridLayoutManager(this,2));


        FirebaseRecyclerOptions<Team> options =
                new FirebaseRecyclerOptions.Builder<Team>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(phone).child("TeamName"), Team.class)
                        .build();


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Team, TeamViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final TeamViewHolder holder, final int position, @NonNull Team model) {

                holder.button.setText(model.getTeamName());

                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent teamDetail = new Intent(view.getContext(), TeamDetail.class);
                        teamDetail.putExtra("team", position);
                        Home.nazwaDruzyny = holder.button.getText().toString();
                        view.getContext().startActivity(teamDetail);
                    }

                });


                holder.button.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                        String [] options = { "Zmień nazwę" , "Usuń" };
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i == 0){

                                    showUpdateTeamFragment(holder.button.getText().toString());
                                }
                                else if( i == 1){
                                    Query queryDelete = table_user.child(phone)
                                            .child("TeamName")
                                            .orderByChild("teamName")
                                            .equalTo(holder.button.getText().toString());
                                    queryDelete.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        return true;
                    }
                });


            }

            @NonNull
            @Override
            public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
                return new TeamViewHolder(view);
            }
        };



        firebaseRecyclerAdapter.startListening();

//        adapter = new TeamAdapter(options);
        recview.setAdapter(firebaseRecyclerAdapter);





        Button buttonAddTeam = (Button) findViewById(R.id.addTeam);
        buttonAddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTeamFragment();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(mtoggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void ustawContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                wybranyElementMenu(item);
                return false;
            }
        });
    }

    public void wybranyElementMenu(MenuItem menuItem){


        int id = menuItem.getItemId();
        Intent homeIntent;
        switch(menuItem.getItemId()){
            case R.id.calendar:
                homeIntent = new Intent(this, Calendar_.class);
                startActivity(homeIntent);

                break;
            case R.id.logOut:
                homeIntent = new Intent(this, MainActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.attendance:
                homeIntent = new Intent(this, TimeSheetView.class);
                startActivity(homeIntent);
                break;

            default:
                homeIntent = new Intent(this, Home.class);
                startActivity(homeIntent);
        }

        //return true;
        mlayout.closeDrawers();
    }


    @Override
    protected void onStart(){
        super.onStart();
        //adapter.startListening();
        Log.d("", "start");
    }

    @Override
    protected void onStop(){
        super.onStop();
        //adapter.stopListening();
        Log.d("", "stop");
    }



    private void showAddTeamFragment() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Podaj nazwę drużyny");
        LayoutInflater inflater = this.getLayoutInflater();
        final View add_team_detail = inflater.inflate(R.layout.add_team_detail_layout, null);
        builder.setView(add_team_detail);


      // Button addTeamDetail = (Button) findViewById(R.id.addTeamDetail);



        builder.setNeutralButton("Dodaj druzyne", new DialogInterface.OnClickListener() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {
                MaterialEditText teamName = (MaterialEditText) add_team_detail.findViewById(R.id.teamNameDetail);


                Team team = new Team(teamName.getText().toString());

                ChildEventListener childEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        MaterialEditText teamName = (MaterialEditText) add_team_detail.findViewById(R.id.teamNameDetail);
                        Team team = dataSnapshot.getValue(Team.class);
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
                table_user.child(phone).child("TeamName").addChildEventListener(childEventListener);

                table_user.child(phone).child("TeamName").child(teamName.getText().toString()).setValue(team);

            }
        });

        builder.show();

    }

    private void showUpdateTeamFragment(final String oldTeamName) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Podaj nową nazwę drużyny");
        LayoutInflater inflater = this.getLayoutInflater();
        final View add_team_detail = inflater.inflate(R.layout.add_team_detail_layout, null);
        builder.setView(add_team_detail);


        // Button addTeamDetail = (Button) findViewById(R.id.addTeamDetail);



        builder.setNeutralButton("Zmień nazwę drużyny", new DialogInterface.OnClickListener() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {
                final MaterialEditText teamName = (MaterialEditText) add_team_detail.findViewById(R.id.teamNameDetail);

                Query queryUpdate = table_user.child(phone).child("TeamName").orderByChild("teamName").equalTo(oldTeamName);
                queryUpdate.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            ds.getRef().child("teamName").setValue(teamName.getText().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
            });



        builder.show();
    }
}
