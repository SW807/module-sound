package dk.aau.cs.psylog.sensor.sound;

import dk.aau.cs.psylog.module_lib.SensorService;

public class PsyLogService extends SensorService {
    @Override
    public void setSensor() {
        sensor = new SoundMeter(this);
    }
}
