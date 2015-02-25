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
public class SoundService extends Service{
    SoundMeter sm;
    Timer timer;
    private boolean isRunning;
    public static boolean daState;


    @Override
    public int onStartCommand(Intent intent, int flag, int startid)
    {
        if(!isRunning){
            isRunning = true;
            sm =  new SoundMeter();
            timer = new Timer();
            sm.start();
            while(true)
            {
                Log.i("soundlevel", String.valueOf(sm.getAmplitude()) + " state:" + StateSingleton.getInstance().state);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /*
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {


                }
            };
            timer.schedule(tt, 1000, 1000);*/

        }

        //Skal være START_STICKY hvis servicen skal køre hele tiden, selv hvis den bliver dræbt. START_NOT_STICKY hjælper når man programmere.
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        isRunning = false;

    }

    @Override
    public void onDestroy(){
        timer.cancel();
        timer.purge();
        sm.stop();
        //Make sure to stop the sensors that have started
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
