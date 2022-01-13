package com.example.kasztelanzarnow.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kasztelanzarnow.Event_;
import com.example.kasztelanzarnow.Home;
import com.example.kasztelanzarnow.R;
import com.example.kasztelanzarnow.TeamDetail;
import com.example.kasztelanzarnow.model.Team;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeamAdapter extends FirebaseRecyclerAdapter<Team, TeamAdapter.myviewholder> {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference().child("User");


    public TeamAdapter(@NonNull FirebaseRecyclerOptions<Team> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull Team model) {
        holder.button.setText(model.getTeamName());

        holder.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent teamDetail = new Intent(view.getContext(), TeamDetail.class);
                teamDetail.putExtra("team", position);
                Home.nazwaDruzyny = holder.button.getText().toString();
                view.getContext().startActivity(teamDetail);

            }

//            public void onItemLongClick(View view, int position){
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(Team.this);
//                String [] options = { "Zmień nazwę" , "Usuń" };
//                builder.setItems(options, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//
//            }


        });



    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{


        Button button;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            button = (Button)itemView.findViewById(R.id.team);

        }
    }
}
