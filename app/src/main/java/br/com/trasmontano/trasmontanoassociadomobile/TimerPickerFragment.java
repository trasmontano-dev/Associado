package br.com.trasmontano.trasmontanoassociadomobile;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by rbarbosa on 21/07/2016.
 */
public class TimerPickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener{

    public EditText textView;

    public TimerPickerFragment(EditText tv)
    {
        this.textView = tv;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String minuto = String.valueOf(minute);
        String hora = String.valueOf(hourOfDay);
        String paddedminuto = "00".substring(minuto.length()) + minuto;
        String paddedHora = "00".substring(hora.length()) + hora;

        /*String rs = getResources().getString (R.string.horaIntervalo);
        String novo = paddedHora + ":" + paddedminuto + " em " + paddedHora + ":" + paddedminuto + " horas";*/

        String horaEscolhida = paddedHora + ":" + paddedminuto;
        textView.setText(horaEscolhida);
    }
}
