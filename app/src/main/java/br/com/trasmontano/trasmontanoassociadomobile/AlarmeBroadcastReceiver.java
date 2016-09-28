package br.com.trasmontano.trasmontanoassociadomobile;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import livroandroid.lib.utils.NotificationUtil;

/**
 * Created by rbarbosa on 20/07/2016.
 */
public class AlarmeBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION = "br.com.trasmontano.trasmontanoassociadomobile.ALARME";

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle b = intent.getExtras();

        Log.d("TESTE", "BroadCast.");
        Intent newIntent = new Intent(context, AlarmeService.class);
        if(b != null)
        {
            newIntent.putExtra("id", b.getInt("id"));
            newIntent.putExtra("paciente", b.getString("paciente"));
            newIntent.putExtra("medicamento", b.getString("medicamento"));
            newIntent.putExtra("mp3", b.getString("mp3"));
            newIntent.putExtra("vibrar", b.getInt("vibrar"));
            newIntent.putExtra("tocar", b.getInt("tocar"));
        }

        context.startService(newIntent);



    }
}
