package com.example.reto1pmd;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BackgroundSound extends Service {

    MediaPlayer mediaPlayer;
    public List<Integer> music = new ArrayList<>();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        music.add(R.raw.background_music_c418);
        music.add(R.raw.background_music_gwyn);
        Collections.shuffle(music);

        mediaPlayer = MediaPlayer.create(this, music.get(0));
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return startId;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

}