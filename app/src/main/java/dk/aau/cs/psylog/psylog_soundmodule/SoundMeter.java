package dk.aau.cs.psylog.psylog_soundmodule;

import android.content.Intent;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import dk.aau.cs.psylog.module_lib.ISensor;

public class SoundMeter implements ISensor {

    private MediaRecorder mRecorder = null;
    private Timer timer;
    private TimerTask timerTask;

    private long delay;
    private long period;

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
        // skal muligvis stoppes inden reschedule
        timer.schedule(timerTask, delay, period);
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

    @Override
    public void sensorParameters(Intent intent) {
        period = intent.getIntExtra("period",0);
        delay = intent.getIntExtra("delay",100000);
    }

    public double getAmplitude() {
        if (mRecorder != null)
            return mRecorder.getMaxAmplitude();
        else
            return 0;
    }
}
