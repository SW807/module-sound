package dk.aau.cs.psylog.sound;

import dk.aau.cs.psylog.module_lib.SuperService;

public class PsyLogService extends SuperService {
    @Override
    public void setSensor() {
        sensor = new SoundMeter();
    }
}
