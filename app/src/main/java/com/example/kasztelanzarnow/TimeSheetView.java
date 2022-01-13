package com.example.kasztelanzarnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kasztelanzarnow.Common.Common;
import com.example.kasztelanzarnow.adapter.AttendanceAdapter;
import com.example.kasztelanzarnow.model.Event;
import com.example.kasztelanzarnow.model.SuperModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class TimeSheetView extends AppCompatActivity  {


    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference().child("User");
    AttendanceAdapter attendanceAdapter;
    static String eventKey = "";
    RecyclerView recyclerView;
    String phone = Common.currentUser.getPhone();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_sheet_view);



        recyclerView = findViewById(R.id.recyclerViewAttendance);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        FirebaseRecyclerOptions<SuperModel> options = new FirebaseRecyclerOptions.Builder<SuperModel>()
                      .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(phone).child("Attendance"), SuperModel.class)
                      .build();


        attendanceAdapter = new AttendanceAdapter(options);
        recyclerView.setAdapter(attendanceAdapter);






//           attendanceAdapter  = new AttendanceAdapter(options){
//            @Override
//            protected void onBindViewHolder(@NonNull AttendanceAdapter.myviewholder holder, int position, @NonNull Event model) {
//                holder.textViewEvent.setText("a");
//                holder.textViewDate.setText("u");
//            }
//
//            @NonNull
//            @Override
//            public AttendanceAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player_timesheet, parent, false);
//                return new myviewholder(v);
//
//            }
//
//        };


        attendanceAdapter.setOnItemClickListener(new AttendanceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DataSnapshot dataSnapshot, int position) {
                SuperModel superModel = dataSnapshot.getValue(SuperModel.class);
                System.out.println(superModel.getTeamName() + " " + superModel.getDate());
                eventKey = dataSnapshot.getKey();
                Intent i = new Intent(TimeSheetView.this, SingleAttenddanceView.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("bun", superModel);
               //i.putExtra("", superModel.getPlayers());
                i.putExtras(bundle);
                startActivity(i);
            }
        });




    }


    @Override
    protected void onStart() {
        super.onStart();
        attendanceAdapter.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();
        attendanceAdapter.stopListening();
    }
}