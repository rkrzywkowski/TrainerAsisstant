package com.example.kasztelanzarnow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kasztelanzarnow.R;
import com.example.kasztelanzarnow.model.Player;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SingleAttendanceAdapter extends RecyclerView.Adapter<SingleAttendanceAdapter.myviewholder> {

    public SingleAttendanceAdapter(List<Player> players, LayoutInflater layoutInflater) {
        this.players = players;
    }

    List<Player> players = new ArrayList<>();
    private Context mContext;

    public SingleAttendanceAdapter(List<Player> players, Context mContext) {
        this.players = players;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player_timesheet, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.name.setText(players.get(position).getName() + " " + players.get(position).getSurname());
        holder.isOnEvent.setChecked(players.get(position).isOnEvent());
        holder.isOnEvent.setClickable(false);
    }



    @Override
    public int getItemCount() {
        return players.size();
    }


    public static class myviewholder extends RecyclerView.ViewHolder{

        public TextView name;
        CheckBox isOnEvent;



        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.itemPlayerName);
            isOnEvent = (CheckBox) itemView.findViewById(R.id.checkBox);

        }

    }

}
