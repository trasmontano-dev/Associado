package br.com.trasmontano.trasmontanoassociadomobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.FirebaseApp;

import java.util.Timer;
import java.util.TimerTask;


public class ActivitySplash extends AppCompatActivity {
    ImageView imageView;
    boolean ret = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_splash);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageView = (ImageView) findViewById(R.id.imageView1);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash);
        imageView.startAnimation(animation);

       /* animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        imageView.startAnimation(animation);*/


        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                finish();
                Intent intent = new Intent(ActivitySplash.this, MainActivity.class);
                startActivity(intent);

            }
        }, 6600);


    }


    /*public boolean ValidaExisteSensorBiometrico() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            //Fingerprint API only available on from Android 6.0 (M)
            FingerprintManager fingerprintManager = (FingerprintManager) this.getSystemService(Context.FINGERPRINT_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ret = true;
            }
            if (!fingerprintManager.isHardwareDetected()) {
                ret = false;
                // Device doesn't support fingerprint authentication
            }

            ret = true;
        } else {
            ret = false;
        }
        return ret;
    }*/


}
