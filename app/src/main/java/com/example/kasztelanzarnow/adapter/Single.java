package com.example.kasztelanzarnow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kasztelanzarnow.R;
import com.example.kasztelanzarnow.model.Player;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Single extends FirebaseRecyclerAdapter<Player, Single.myviewholder> {

    public Single(@NonNull FirebaseRecyclerOptions<Player> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Player model) {
        holder.name.setText(model.getName());
        holder.isOnEvent.setChecked(model.isOnEvent());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player_timesheet, parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView name;
        CheckBox isOnEvent;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.itemPlayerName);
            isOnEvent = (CheckBox) itemView.findViewById(R.id.checkBox);

        }
    }
}
