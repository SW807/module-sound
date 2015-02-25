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

    public SoundMeter(){
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");
            try {
                mRecorder.prepare();
                mRecorder.start();
            }
            catch (IOException e)
            {
                Log.e("SoundMeter",e.getMessage());
            }
        }
    }

    public void startSensor() {
        Timer timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                //HER SKAL DATABASE KALDET VÆRE getAmplitude();
            }
        };
        timer.schedule(tt, 1000, 1000); //DENNE TID KAN ÆNDRES
    }

    public void stopSensor() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    public double getAmplitude() {
        if (mRecorder != null)
            return  mRecorder.getMaxAmplitude();
        else
            return 0;
    }
}
