package com.example.kasztelanzarnow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kasztelanzarnow.R;
import com.example.kasztelanzarnow.model.Player;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class ItemPlayerAdapter extends FirebaseRecyclerAdapter<Player, ItemPlayerAdapter.myviewholder> {


    public ItemPlayerAdapter(@NonNull FirebaseRecyclerOptions<Player> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Player model) {
        holder.name.setText(model.getName() + " " + model.getSurname());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent,false);


        return new myviewholder(view);
    }

    class myviewholder extends ViewHolder{

        TextView name;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.itemPlayerName);
        }
    }


}