package com.example.kasztelanzarnow;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HolderTimeSheetView extends RecyclerView.ViewHolder {

    TextView name;
    CheckBox isOnEvent;



    public HolderTimeSheetView(@NonNull View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.itemPlayerName);
        isOnEvent = (CheckBox) itemView.findViewById(R.id.checkBox);

    }


}
