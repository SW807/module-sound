package dk.aau.cs.psylog.psylog_soundmodule;

import dk.aau.cs.psylog.module_lib.SuperService;

public class PsyLogService extends SuperService {
    @Override
    public void setSensor() {
        sensor = new SoundMeter();
    }
}