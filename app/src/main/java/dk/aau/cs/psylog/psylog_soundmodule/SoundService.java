package dk.aau.cs.psylog.psylog_soundmodule;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Praetorian on 24-02-2015.
 */
public class SoundService extends Service {
    SoundMeter sm;

    @Override
    public int onStartCommand(Intent intent, int flag, int startid) {
        sm.startSensor();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sm = new SoundMeter();
    }

    @Override
    public void onDestroy() {
        sm.stopSensor();
        //Make sure to stop the sensors that have started
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
