package br.com.trasmontano.trasmontanoassociadomobile.Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import br.com.trasmontano.trasmontanoassociadomobile.adapter.AlarmeAdapter;

/**
 * Created by rbarbosa on 20/07/2016.
 */
public class AlarmUtil {
    // Agenda o alarme
    public static void schedule(Context context, Intent intent, long triggerAtMillis, int id) {
        PendingIntent p = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarme = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarme.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, p);
        Log.d("TESTE", "Alarme agendado com sucesso.");
    }

    // Agenda o alarme com repeat
    public static void scheduleRepeat(Context context, Intent intent, long triggerAtMillis, long intervalMillis, int id) {
        PendingIntent p = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarme = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarme.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, p);
        Log.d("TESTE", "Alarme agendado com sucesso com repeat.");
    }

    public static void cancel(Context context, Intent intent, int id) {
        AlarmManager alarme = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent p = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarme.cancel(p);
        Log.d("livroandroid-alarm", "Alarme cancelado com sucesso.");
    }
}
