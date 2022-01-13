package com.example.kasztelanzarnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kasztelanzarnow.Common.Common;
import com.example.kasztelanzarnow.adapter.EventAdapter;
import com.example.kasztelanzarnow.model.Event;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.EventTarget;


public class Calendar_ extends AppCompatActivity {

    RecyclerView recyclerView;
    String phone = Common.currentUser.getPhone();
    EventAdapter eventAdapter;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference().child("User");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Event> options =
                new FirebaseRecyclerOptions.Builder<Event>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(phone).child("Events"), Event.class)
                .build();


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter <Event, EventAdapter.myviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull EventAdapter.myviewholder holder, final int position, @NonNull Event model) {
                //holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View view) {
//                        System.out.println("loong " + position);
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(Calendar_.this);
//                        String [] options = {"Usuń" };
//                        Query queryDeletePlayer = table_user.child(phone)
//                                .child("Events");
//                        queryDeletePlayer.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                for(DataSnapshot ds : dataSnapshot.getChildren()){
//                                    Event event = ds.getValue(Event.class);
//                                    //ds.getRef(position).removeValue();
//                                    //dataSnapshot.getKey()
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//                        System.out.println(queryDeletePlayer);
//                        return false;
//                    }
                };
          //  }

            @NonNull
            @Override
            public EventAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent,false);
                return new EventAdapter.myviewholder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

//        eventAdapter = new EventAdapter(options);
//
//        recyclerView.setAdapter(eventAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
      //  eventAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
       // eventAdapter.stopListening();
    }
}