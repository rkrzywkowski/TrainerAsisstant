package com.example.kasztelanzarnow;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kasztelanzarnow.Common.Common;
import com.example.kasztelanzarnow.adapter.ItemPlayerAdapter;
import com.example.kasztelanzarnow.model.Event;
import com.example.kasztelanzarnow.model.Player;
import com.example.kasztelanzarnow.model.Team;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Event_ extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public Event_() {
    }

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference().child("User");

    TextView textViewDate;
    EditText editTextInfo;
    Spinner event;
    Button addEvent;
    String date;
    String phone = Common.currentUser.getPhone();

    List<Player> players = new ArrayList<>();
    FirebaseRecyclerOptions<Player> options;

    Spinner spinnerTeam;
    ArrayAdapter<String> teamArrayAdapter;
    ArrayList<String> spinnerDataList;

    private String events[] = {"MECZ", "TRENING", "INNE"}; // = new String[]{"MECZ", "TRENING", "INNE"};

    DatePickerDialog.OnDateSetListener setListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_activity);


        event = (Spinner) findViewById(R.id.event);
        textViewDate = (TextView) findViewById(R.id.date);
        editTextInfo = (EditText) findViewById(R.id.info);
        addEvent = (Button) findViewById(R.id.addEvent);
        spinnerTeam = (Spinner) findViewById(R.id.teamName);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.events, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        event.setAdapter(adapter);
        event.setOnItemSelectedListener(this);


        spinnerDataList = new ArrayList<>();
        teamArrayAdapter = new ArrayAdapter<String>(Event_.this, android.R.layout.simple_spinner_dropdown_item, spinnerDataList);
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

        spinnerTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, final View view, int i, long l) {
                TimeSheet.selected = spinnerTeam.getSelectedItem().toString();
                adapterView.getSelectedItem().toString();
                TimeSheet.selected = adapterView.getItemAtPosition(i).toString();


//                FirebaseRecyclerOptions<Player> options = new FirebaseRecyclerOptions.Builder<Player>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(phone).child("TeamName").child(TimeSheet.selected).child("players"), Player.class)
//                        .build();


//                firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Player>().setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(phone).child("TeamName").child(TimeSheet.selected).child("players"), Player.class)
//                        .build();
//                firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Player, newViewHolder>(firebaseRecyclerOptions) {
//
//                    @NonNull
//                    @Override
//                    public newViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player_timesheet, parent, false);
//                        return new newViewHolder(v);
//                    }
//
//                    @Override
//                    protected void onBindViewHolder(@NonNull newViewHolder holder, int position, @NonNull Player model) {
//                        holder.name.setText(model.getName() + " " + model.getSurname());
//                        holder.isOnEvent.setChecked(model.isOnEvent());
//
//                        holder.setItemClickListener(new newViewHolder.ItemClickListener() {
//                            @Override
//                            public void onItemClick(View view, int pos) {
//                                CheckBox check = (CheckBox) view;
//
//                                if(check.isChecked()){
//                                    firebaseRecyclerOptions.getSnapshots().get(pos).setOnEvent(true);
//                                }
//                                else{
//                                    firebaseRecyclerOptions.getSnapshots().get(pos).setOnEvent(false);
//                                }
//
//
//                            }
//                        });
//
//
//                    }
//                };




//                firebaseRecyclerAdapter.startListening();
//                recyclerView.setAdapter(firebaseRecyclerAdapter);


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





        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);



        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Event_.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        date = day + "/" + month + "/" + year;
                        textViewDate.setText(date);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Event ev = new Event(event.getSelectedItem().toString(),
                        date,
                        editTextInfo.getText().toString());

                table_user.child(phone).child("Events").push().setValue(ev);
                Intent i = new Intent(Event_.this, Calendar_.class);
                startActivity(i);





                table_user.child(phone)
                        .child("TeamName")
                        .child(TimeSheet.selected)
                        .child("players")
                        .addValueEventListener(new ValueEventListener() {

                    int index = 0;
                    SmsManager sms=SmsManager.getDefault();
                    PendingIntent sentPI;

                    String textSMS = event.getSelectedItem().toString() + " " +
                                    date + " " +
                                    editTextInfo.getText().toString();


                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for(DataSnapshot data : dataSnapshot.getChildren()){
//
//                            players.add(data.getValue(Player.class));
//                            sms.sendTextMessage(players.get(index).getPhoneNumber()
//                                    , null
//                                    , textSMS
//                                    , null
//                                    , null);
//                            index++;
//
//                        }
                        sms.sendTextMessage("799904383"
                                    , null
                                    , textSMS
                                    , null
                                    , null);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }


        });



    }





    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
