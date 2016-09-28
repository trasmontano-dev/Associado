package br.com.trasmontano.trasmontanoassociadomobile;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.drm.DrmStore;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import livroandroid.lib.utils.NotificationUtil;

/**
 * Created by rbarbosa on 22/07/2016.
 */
public class AlarmeService extends Service {
    MediaPlayer mMediaPlayer;
    Vibrator v;
    private int tocar = 1;
    private int vibrar = 1;

    public void gerarNotificacao(String paciente, String medicacao, int id, String mp3) {

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, MensagemActivity.class);
        i.putExtra("id", id);
        i.putExtra("paciente", paciente);
        i.putExtra("medicamento", medicacao);

        PendingIntent p = PendingIntent.getActivity(this, id, i, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("Ticker Texto");
        builder.setContentTitle(paciente);
        builder.setContentText(medicacao);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(p);

        Notification n = builder.build();

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrar == 1) {

            long[] pattern = {0, 2500, 700};
            v.vibrate(pattern, 0);
        }

        //n.vibrate = new long[]{0, 4000, 150, 4000};
        n.flags = Notification.FLAG_NO_CLEAR;
        nm.notify(id, n);


        mMediaPlayer = new MediaPlayer();
        try {
            String filename = "android.resource://br.com.trasmontano.trasmontanoassociadomobile/raw/highway_to_hell";
             //String filename = "android.resource://br.com.trasmontano.trasmontanoassociadomobile/raw/palmeiras";
             //mp3 = "android.resource://br.com.trasmontano.trasmontanoassociadomobile/raw/palmeiras";
            if (tocar == 1) {
                if (mp3.equalsIgnoreCase(""))
                    mMediaPlayer.setDataSource(this, Uri.parse(filename));
                else
                    mMediaPlayer.setDataSource(this, Uri.parse(mp3));
                final AudioManager audioManager = (AudioManager) this
                        .getSystemService(Context.AUDIO_SERVICE);
                if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                    mMediaPlayer.setLooping(true);
                    mMediaPlayer.prepare();


                    mMediaPlayer.start();
                }
            }
        } catch (IOException e) {
            System.out.println("OOPS");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle b = intent.getExtras();

        int id = 0;
        String paciente = "";
        String medicamento = "";
        String mp3 = "";

        if (b != null) {
            id = b.getInt("id");
            paciente = b.getString("paciente");
            medicamento = b.getString("medicamento");
            mp3 = b.getString("mp3");
            vibrar = b.getInt("vibrar");
            tocar = b.getInt("tocar");
        }
        gerarNotificacao(paciente, medicamento, id, mp3);
        return START_STICKY;
    }

    public void onDestroy() {
        if (tocar == 1) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
        }
        if (vibrar == 1) {
            if (v.hasVibrator())
                v.cancel();
        }

    }
}
