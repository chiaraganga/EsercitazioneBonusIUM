package com.example.esbonuscg;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    private Calendar date;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        DatePicker datePicker = new DatePicker(getActivity());
        date = Calendar.getInstance();

        return new AlertDialog.Builder(getActivity())
                .setMessage("Inserisci la tua data di nascita")
                .setView(datePicker)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                date.set(Calendar.YEAR, datePicker.getYear());
                                date.set(Calendar.MONDAY, datePicker.getMonth());
                                date.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());


                                ((SigninActivity) getActivity()).doPositiveClick(date);
                            }
                        }

                )

                .setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                })

                .create();

    }

    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(3, 218, 197));
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(255, 51, 51));
    }

    public static String TAG = "DatePickerDialog";

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
