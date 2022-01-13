package com.example.kasztelanzarnow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kasztelanzarnow.Common.Common;
import com.example.kasztelanzarnow.adapter.Single;
import com.example.kasztelanzarnow.adapter.SingleAttendanceAdapter;
import com.example.kasztelanzarnow.model.Player;
import com.example.kasztelanzarnow.model.SuperModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.kasztelanzarnow.TimeSheetView.eventKey;

public class SingleAttenddanceView extends AppCompatActivity {

    TextView teamName, date, event;
    RecyclerView recyclerView;

    String phone = Common.currentUser.getPhone();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference().child("User");
    SuperModel superModel;
    String key = "";

    SingleAttendanceAdapter singleAttendanceAdapter;

    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    List<Player> players;

    Single single;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_attenddance_view);

        teamName = (TextView) findViewById(R.id.teamNameSingleAttendance);
        date = (TextView) findViewById(R.id.dateSingleAttendance);
        event = (TextView) findViewById(R.id.eventSingleAttenDance);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSingleAttendance);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();
         superModel = (bundle.getParcelable("bun"));

        teamName.setText(superModel.getTeamName());
        date.setText(superModel.getDate());
        event.setText(superModel.getNameEvent());

//        recyclerView.setHasFixedSize(true);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//
//
//        singleAttendanceAdapter = new SingleAttendanceAdapter(superModel.getPlayers(), this.getLayoutInflater());
//
//        System.out.println(superModel.getPlayers().size());
//        recyclerView.setAdapter(singleAttendanceAdapter);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Player>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(phone).child("Attendance").child(eventKey).child("players"), Player.class)
                .build();


        players = new ArrayList<>();
        table_user.child(phone).child("Attendance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){

                   SuperModel s = data.getValue(SuperModel.class);
                   if(
                   s.getNameEvent().equals(superModel.getNameEvent()) &&
                   s.getDate().equals(superModel.getDate()) &&
                   s.getTeamName().equals(superModel.getTeamName())){

                      superModel.setPlayers(s.getPlayers());

                   }

                }

//                for(int i = 0; i < superModel.getPlayers().size(); i++){
//                    System.out.println(superModel.getPlayers().get(i).getName() + " " +
//                            superModel.getPlayers().get(i).getSurname());
//                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        single = new Single(options);
//        recyclerView.setAdapter(single);


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Player, HolderTimeSheetView>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HolderTimeSheetView holder, int position, @NonNull Player model) {
//                holder.name.setText(model.getPlayers().get(position).getName() + " " + model.getPlayers().get(position).getSurname());
//                holder.isOnEvent.setChecked(model.getPlayers().get(position).isOnEvent());
                holder.name.setText(model.getName() + " " + model.getSurname());
                holder.isOnEvent.setChecked(model.isOnEvent());
                holder.isOnEvent.setClickable(false);
                //System.out.println(model.getName());
            }

//            @Override
//            protected void onBindViewHolder(@NonNull newViewHolder holder, int position, @NonNull Player model) {
//
//            }
            //List<Player> players = new ArrayList<>(superModel.getPlayers());


            @NonNull
            @Override
            public HolderTimeSheetView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player_timesheet, parent, false);


                return new HolderTimeSheetView(v);
            }
        } ;


        System.out.println(superModel.getTeamName() + " " + superModel.getNameEvent());
        System.out.println("Super model size: " + superModel.getPlayers().size());

//
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
      //  single.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //single.stopListening();
    }
}