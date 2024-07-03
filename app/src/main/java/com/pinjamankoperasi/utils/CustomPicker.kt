package com.pinjamankoperasi.utils

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.DatePicker
import android.widget.NumberPicker
import java.util.Calendar

class CustomPicker(
    context: Context,
    private val listener: OnDateSetListener,
    year: Int,
    month: Int
) : DatePickerDialog(context, THEME_HOLO_LIGHT, null, year, month, 2) {

    interface OnDateSetListener {
        fun onDateSet(year: Int, month: Int)
    }

    init {
        setTitle("Pilih Bulan dan Tahun")

        datePicker.findViewById<NumberPicker>(
            context.resources.getIdentifier("day", "id", "android")
        ).visibility = View.GONE

        datePicker.findViewById<NumberPicker>(
            context.resources.getIdentifier("month", "id", "android")
        ).minValue = 0
        datePicker.findViewById<NumberPicker>(
            context.resources.getIdentifier("month", "id", "android")
        ).maxValue = 11

        setButton(DialogInterface.BUTTON_POSITIVE, "OK") { _, _ ->
            listener.onDateSet(datePicker.year, datePicker.month + 1)
        }
        setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") { dialog, _ ->
            dialog.cancel()
        }
    }
}
