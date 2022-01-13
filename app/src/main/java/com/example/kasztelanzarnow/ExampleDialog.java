package com.example.kasztelanzarnow;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;


public class ExampleDialog extends AppCompatDialogFragment implements NumberPicker.OnValueChangeListener {
    //    private EditText editTextUsername;
//    private EditText editTextPassword;
    private ExampleDialogListener listener;
//    private Button click;

    private NumberPicker numberPicker;
    private String title;


    public ExampleDialog(String textFromClick) {
        this.title = textFromClick;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.number_picker, null);
        numberPicker = (NumberPicker) view.findViewById(R.id.picker);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(15);
        numberPicker.setValue(0);
        numberPicker.setOnValueChangedListener(this);


        builder.setView(view)
                .setTitle(title)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        String username = editTextUsername.getText().toString();
//                        String password = editTextPassword.getText().toString();

                        int number = numberPicker.getValue();
//                        listener.applyTexts(username, password);
                        listener.applyTexts(number);
                    }
                });



//        editTextUsername = view.findViewById(R.id.edit_username);
//        editTextPassword = view.findViewById(R.id.edit_password);

        return builder.create();
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        //        void applyTexts(String username, String password);
        void applyTexts(int number);
    }

}