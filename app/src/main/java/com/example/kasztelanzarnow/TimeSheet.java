package com.example.kasztelanzarnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kasztelanzarnow.Common.Common;
import com.example.kasztelanzarnow.adapter.TimeSheetAdapter;
import com.example.kasztelanzarnow.model.Event;
import com.example.kasztelanzarnow.model.Player;
import com.example.kasztelanzarnow.model.SuperModel;
import com.example.kasztelanzarnow.model.Team;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.FloatingActionButton;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class TimeSheet extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public TimeSheet(){

    }

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference().child("User");

    FirebaseRecyclerAdapter<Player, newViewHolder> firebaseRecyclerAdapter ;
    FirebaseRecyclerOptions<Player> firebaseRecyclerOptions;

    RecyclerView recyclerView;
    String phone = Common.currentUser.getPhone();
    TimeSheetAdapter timeSheetAdapter;

    static String selected = "";
    TextView textViewDate;
    EditText editTextTeam;
    Spinner event;
    Spinner spinnerTeam;
    String date = "";
    com.google.android.material.floatingactionbutton.FloatingActionButton addAttendance;




    ArrayAdapter<String> teamArrayAdapter;
    ArrayList<String> spinnerDataList;


    public class att{
      public  String name;
        public  String date;
        public   String team;
        //HashMap<String, Player> playerHashMap;
        public FirebaseRecyclerOptions<Player> list;

        public att(String name, String date, String team) {
            this.name = name;
            this.date = date;
            this.team = team;
        }
    }

    private String events[] = {"MECZ", "TRENING", "INNE"}; // = new String[]{"MECZ", "TRENING", "INNE"};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_sheet);

        event = (Spinner) findViewById(R.id.eventSpinner);
        textViewDate = (TextView) findViewById(R.id.date);
        spinnerTeam = (Spinner) findViewById(R.id.teamSpinner);
        addAttendance = (com.google.android.material.floatingactionbutton.FloatingActionButton) findViewById(R.id.floatingActionButton);
       // editTextTeam = (EditText) findViewById(R.id.teamEditText);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.events, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        event.setAdapter(adapter);
        event.setOnItemSelectedListener(this);



        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(TimeSheet.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        date = day + "-" + month + "-" + year;
                        textViewDate.setText(date);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


///////////////////////////


      spinnerDataList = new ArrayList<>();
      teamArrayAdapter = new ArrayAdapter<String>(TimeSheet.this, android.R.layout.simple_spinner_dropdown_item, spinnerDataList);
      table_user.child(phone).child("TeamName").addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for(DataSnapshot item : dataSnapshot.getChildren()){
                Team team = item.getValue(Team.class);
                  spinnerDataList.add(team.getTeamName());
              }
              teamArrayAdapter.notifyDataSetChanged();

          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });

      spinnerTeam.setAdapter(teamArrayAdapter);
      spinnerTeam.setOnItemSelectedListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTeam);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


      spinnerTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, final View view, int i, long l) {
            TimeSheet.selected = spinnerTeam.getSelectedItem().toString();
            adapterView.getSelectedItem().toString();
            TimeSheet.selected = adapterView.getItemAtPosition(i).toString();

              final FirebaseRecyclerOptions<Player> options = new FirebaseRecyclerOptions.Builder<Player>()
                      .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(phone).child("TeamName").child(TimeSheet.selected).child("players"), Player.class)
                      .build();


              firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Player>().setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(phone).child("TeamName").child(TimeSheet.selected).child("players"), Player.class)
                      .build();

              firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Player, newViewHolder>(firebaseRecyclerOptions) {

                  @NonNull
                  @Override
                  public newViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player_timesheet, parent, false);
                      return new newViewHolder(v);
                  }

                  @Override
                  protected void onBindViewHolder(@NonNull newViewHolder holder, int position, @NonNull Player model) {
                      holder.name.setText(model.getName() + " " + model.getSurname());
                      holder.isOnEvent.setChecked(model.isOnEvent());

                      holder.setItemClickListener(new newViewHolder.ItemClickListener() {
                          @Override
                          public void onItemClick(View view, int pos) {
                              CheckBox check = (CheckBox) view;

                              if(check.isChecked()){
                                  int amountEvents = firebaseRecyclerOptions.getSnapshots().get(pos).getAmountEvents();
                                  int amountMatches = firebaseRecyclerOptions.getSnapshots().get(pos).getAmountMatches();
                                  int amountTrainings = firebaseRecyclerOptions.getSnapshots().get(pos).getAmountTrainings();
                                  firebaseRecyclerOptions.getSnapshots().get(pos).setOnEvent(true);
                              }
                              else{
                                  firebaseRecyclerOptions.getSnapshots().get(pos).setOnEvent(false);
                              }
                          }
                      });


                  }
              };




              firebaseRecyclerAdapter.startListening();
              recyclerView.setAdapter(firebaseRecyclerAdapter);


//              FirebaseRecyclerOptions<Player> options = new FirebaseRecyclerOptions.Builder<Player>()
//                      .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(phone).child("TeamName").child(TimeSheet.selected).child("players"), Player.class)
//                      .build();
//
//
//               // recyclerView.setAdapter(options);
//                  //timeSheetAdapter.updateOptions(options);
//
//                timeSheetAdapter = new TimeSheetAdapter(options);
//                timeSheetAdapter.updateOptions(options);
//                recyclerView.setAdapter(timeSheetAdapter);

          }


          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {
          }




      });

///////////////////////


//        final FirebaseRecyclerOptions<Player> options =
//                new FirebaseRecyclerOptions.Builder<Player>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(phone).child("TeamName").child("Druzyna A").child("players"), Player.class)
//                        .build();





//        timeSheetAdapter = new TimeSheetAdapter(options);
//        recyclerView.setAdapter(timeSheetAdapter);

        addAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  updateAttendance(firebaseRecyclerOptions);
                table_user
                        .child(phone)
                        .child("TeamName")
                        .child(TimeSheet.selected)
                        .child("players").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int i = 0;
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String key = ds.getKey();
                            Player player = ds.getValue(Player.class);

                            if(firebaseRecyclerOptions.getSnapshots().get(i).isOnEvent()){
                                if(event.getSelectedItem().toString().equals("MECZ")){
                                    table_user
                                            .child(phone)
                                            .child("TeamName")
                                            .child(TimeSheet.selected)
                                            .child("players").child(key).child("amountMatches").setValue(firebaseRecyclerOptions.getSnapshots().get(i).getAmountMatches() + 1);
                                } else if(event.getSelectedItem().toString().equals("TRENING")){
                                    table_user
                                            .child(phone)
                                            .child("TeamName")
                                            .child(TimeSheet.selected)
                                            .child("players").child(key).child("amountTrainings").setValue(firebaseRecyclerOptions.getSnapshots().get(i).getAmountTrainings() + 1);

                                } else if(event.getSelectedItem().toString().equals("INNE")){
                                    table_user
                                            .child(phone)
                                            .child("TeamName")
                                            .child(TimeSheet.selected)
                                            .child("players").child(key).child("amountEvents").setValue(firebaseRecyclerOptions.getSnapshots().get(i).getAmountEvents() + 1);

                                }

                            }
                            System.out.println(firebaseRecyclerOptions.getSnapshots().get(i).getSurname() +" kluczyk" + key + " " + firebaseRecyclerOptions.getSnapshots().get(i).isOnEvent() + " " + i);
                            i++;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                List<Player> players = new ArrayList<>();
                for(int i = 0; i< firebaseRecyclerOptions.getSnapshots().size(); i++){
                    players.add(firebaseRecyclerOptions.getSnapshots().get(i));
                }

//                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (dataSnapshot :
//                             ) {
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

//                timeSheetAdapter.
                if(date.equals("")){
                    Toast.makeText(TimeSheet.this,"Podaj date!", Toast.LENGTH_SHORT).show();
                }
                else{

                    SuperModel superModel = new SuperModel(
                            TimeSheet.selected,
                            date,
                            event.getSelectedItem().toString(),
                            players

                           );


                    table_user.child(phone).child("Attendance").push().setValue(superModel);

//                    table_user.child(phone).child("Attendance").child(TimeSheet.selected).child(date)
//                            .setValue(event.getSelectedItem().toString());
//                    for(int i = 0; i< firebaseRecyclerOptions.getSnapshots().size(); i++){
//                        table_user.child(phone).child("Attendance").child(TimeSheet.selected).child("dates").child(date)
//                                .child(event.getSelectedItem().toString()).push()
//                                .setValue(firebaseRecyclerOptions.getSnapshots().get(i));
//                    }


                    Intent i = new Intent(TimeSheet.this, Home.class);
                    startActivity(i);
                }
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        //timeSheetAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
       // timeSheetAdapter.stopListening();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void updateAttendance(final FirebaseRecyclerOptions<Player>firebaseRecyclerOptions){

        table_user
                .child(phone)
                .child("TeamName")
                .child(TimeSheet.selected)
                .child("players").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    Player player = ds.getValue(Player.class);

                    if(firebaseRecyclerOptions.getSnapshots().get(i).isOnEvent()){
                        if(event.getSelectedItem().toString().equals("MECZ")){
                            table_user
                                    .child(phone)
                                    .child("TeamName")
                                    .child(TimeSheet.selected)
                                    .child("players").child(key).child("amountMatches").setValue(firebaseRecyclerOptions.getSnapshots().get(i).getAmountMatches() + 1);
                        } else if(event.getSelectedItem().toString().equals("TRENING")){
                            table_user
                                    .child(phone)
                                    .child("TeamName")
                                    .child(TimeSheet.selected)
                                    .child("players").child(key).child("amountTrainings").setValue(firebaseRecyclerOptions.getSnapshots().get(i).getAmountTrainings() + 1);

                        } else if(event.getSelectedItem().toString().equals("INNE")){
                            table_user
                                    .child(phone)
                                    .child("TeamName")
                                    .child(TimeSheet.selected)
                                    .child("players").child(key).child("amountEvents").setValue(firebaseRecyclerOptions.getSnapshots().get(i).getAmountEvents() + 1);

                        }

                    }
                   // System.out.println(firebaseRecyclerOptions.getSnapshots().get(i).getSurname() +" kluczyk" + key + " " + firebaseRecyclerOptions.getSnapshots().get(i).isOnEvent() + " " + i);
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}