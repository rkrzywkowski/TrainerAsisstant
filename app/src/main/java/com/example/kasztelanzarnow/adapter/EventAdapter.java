package com.example.kasztelanzarnow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kasztelanzarnow.R;
import com.example.kasztelanzarnow.model.Event;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;

import java.util.Date;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventAdapter extends FirebaseRecyclerAdapter<Event, EventAdapter.myviewholder> {

    private EventAdapter.OnItemClickListener onItemClickListener;

    public EventAdapter(@NonNull FirebaseRecyclerOptions<Event> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder myviewholder, int i, @NonNull Event event) {

        long timestamp = System.currentTimeMillis(); //pobiera czas jako timestamp
        Date date = new Date(timestamp); // tworzy obiekt daty na podstawie timestamp

        myviewholder.eventName.setText(event.getName());
        myviewholder.eventDate.setText(event.getDate());
        myviewholder.eventNote.setText(event.getNote());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
       return new myviewholder(view);
    }

    public static class myviewholder extends RecyclerView.ViewHolder {

        TextView eventName, eventDate, eventNote;
        private OnItemClickListener onItemClickListener;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.eventName);
            eventDate = (TextView) itemView.findViewById(R.id.eventDate);
            eventNote = (TextView) itemView.findViewById(R.id.eventNote);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onItemlongClick(view, getAdapterPosition());
                    return true;
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(DataSnapshot dataSnapshot, int position);
        void onItemlongClick(View view, int position);

    }

    public void  setOnItemClickListener(EventAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


}