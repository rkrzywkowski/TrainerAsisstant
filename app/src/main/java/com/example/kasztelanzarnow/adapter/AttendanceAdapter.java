package com.example.kasztelanzarnow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.kasztelanzarnow.R;
import com.example.kasztelanzarnow.model.Event;
import com.example.kasztelanzarnow.model.SuperModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AttendanceAdapter extends FirebaseRecyclerAdapter<SuperModel, AttendanceAdapter.myviewholder>  {

    private OnItemClickListener onItemClickListener;


    public AttendanceAdapter(@NonNull FirebaseRecyclerOptions<SuperModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull SuperModel model) {
        holder.textViewTeamName.setText(model.getTeamName());
        holder.textViewDate.setText(model.getDate());
        holder.textViewEvent.setText(model.getNameEvent());

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance, parent, false);
        return new myviewholder(v);

    }

    public class myviewholder extends RecyclerView.ViewHolder {


        public TextView textViewTeamName, textViewDate, textViewEvent;



        public myviewholder(@NonNull View itemView) {
            super(itemView);

            textViewTeamName = itemView.findViewById(R.id.itemTeamName);
            textViewDate = itemView.findViewById(R.id.itemDate);
            textViewEvent = itemView.findViewById(R.id.itemEvent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null){
                        onItemClickListener.onItemClick(getSnapshots().getSnapshot(position), position);

                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(DataSnapshot dataSnapshot, int position);

    }

    public void  setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
