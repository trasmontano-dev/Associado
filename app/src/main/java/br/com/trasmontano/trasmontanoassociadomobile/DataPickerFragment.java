package br.com.trasmontano.trasmontanoassociadomobile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by rbarbosa on 21/07/2016.
 */
public class DataPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public EditText editText;

    public DataPickerFragment(EditText et)
    {
        this.editText = et;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String dia = String.valueOf(dayOfMonth);
        String mes = String.valueOf(monthOfYear + 1);
        String paddeddia = "00".substring(dia.length()) + dia;
        String paddedmes = "00".substring(mes.length()) + mes;

        editText.setText(paddeddia + "/" + paddedmes + "/" + year);
    }
}
