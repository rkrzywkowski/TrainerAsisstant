package com.example.kasztelanzarnow;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class newViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public Object setItemClickListener;
    TextView name;
    CheckBox isOnEvent;

    ItemClickListener itemClickListener;

    public newViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.itemPlayerName);
        isOnEvent = (CheckBox) itemView.findViewById(R.id.checkBox);

        isOnEvent.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic;
    }
    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(view, getLayoutPosition());
    }

    interface ItemClickListener {
        void onItemClick(View view, int pos);
    }

}
