package com.example.kasztelanzarnow;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeamViewHolder extends RecyclerView.ViewHolder {

    Button button;

    public TeamViewHolder(@NonNull View itemView) {
        super(itemView);
        button = (Button) itemView.findViewById(R.id.team);
    }
}
