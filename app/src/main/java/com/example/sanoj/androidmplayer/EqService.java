package com.example.sanoj.androidmplayer;

import android.app.Service;
import android.content.Intent;
import android.media.audiofx.Equalizer;
import android.os.IBinder;


public class EqService extends Service {

    public Equalizer equalizer;
    final int audiosessionId = 1088112;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        equalizer = new Equalizer(0, audiosessionId);
        equalizer.setEnabled(true);

    }
}
