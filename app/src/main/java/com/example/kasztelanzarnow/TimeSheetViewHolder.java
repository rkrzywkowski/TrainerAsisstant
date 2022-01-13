package com.example.kasztelanzarnow;

import android.view.View;
import android.widget.TextView;

import com.example.kasztelanzarnow.adapter.AttendanceAdapter;
import com.google.firebase.database.DataSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimeSheetViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewTeamName, textViewDate, textViewEvent;

    OnItemClickListener onItemClickListener;

    public TimeSheetViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewTeamName = itemView.findViewById(R.id.itemTeamName);
        textViewDate = itemView.findViewById(R.id.itemDate);
        textViewEvent = itemView.findViewById(R.id.itemEvent);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    //onItemClickListener.onItemClick(getSnapshots().getSnapshot(position), position);

                }
            }
        });

    }


    public interface OnItemClickListener {
        void onItemClick(DataSnapshot dataSnapshot, int position);

    }

    public void setOnItemClickListener(AttendanceAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = (OnItemClickListener) onItemClickListener;
    }
}