package dk.aau.cs.psylog.sensor.sound;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import dk.aau.cs.psylog.module_lib.DBAccessContract;
import dk.aau.cs.psylog.module_lib.ISensor;

public class SoundMeter implements ISensor {

    private MediaRecorder mRecorder = null;
    private Timer timer;
    private TimerTask timerTask;

    private long delay;
    private long period;

    private ContentResolver resolver;

    public SoundMeter(Context context) {
        resolver = context.getContentResolver();
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
                double ampl =  getAmplitude();
                Uri uri = Uri.parse(DBAccessContract.DBACCESS_CONTENTPROVIDER + "amplitudes");
                ContentValues values = new ContentValues();
                values.put("amplitude", ampl);
                resolver.insert(uri, values);
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
        period = intent.getIntExtra("period",1000);
        delay = intent.getIntExtra("delay",0);
    }

    public double getAmplitude() {
        if (mRecorder != null)
            return mRecorder.getMaxAmplitude();
        else
            return 0;
    }
}
