package dk.aau.cs.psylog.psylog_soundmodule;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Praetorian on 24-02-2015.
 */
public class SoundMeter {

    private MediaRecorder mRecorder = null;
    private Timer timer;
    private TimerTask timerTask;

    public SoundMeter() {
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");
            try {
                mRecorder.prepare();
                mRecorder.start();
            } catch (IOException e) {
                Log.e("SoundMeter", e.getMessage());
            }
        }

        timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.i("DATSHIT", "ampl: " + getAmplitude());
            }
        };
    }



    public void startSensor() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(timerTask, 1000, 1000); //DENNE TID KAN ÆNDRES
    }

    public void stopSensor() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
        timer.cancel();
        timer.purge();
    }

    public double getAmplitude() {
        if (mRecorder != null)
            return mRecorder.getMaxAmplitude();
        else
            return 0;
    }
}
