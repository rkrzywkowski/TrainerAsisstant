package com.example.kasztelanzarnow;

import android.view.View;
import android.widget.TextView;

import com.example.kasztelanzarnow.adapter.AttendanceAdapter;
import com.google.firebase.database.DataSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlayerViewHolder extends RecyclerView.ViewHolder {

    TextView name;
//    OnItemClickListener onItemClickListener;
    PlayerViewHolder.OnItemClickListener onItemClickListener;

    public PlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.itemPlayerName);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemClickListener.onItemlongClick(view, getAdapterPosition());
                return true;
            }
        });

    }




    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemlongClick(View view, int position);
    }


    public void setOnItemClickListener(PlayerViewHolder.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
