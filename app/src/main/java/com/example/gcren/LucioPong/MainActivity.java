package com.example.gcren.LucioPong;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    GameView gameView;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(gameView);
        player = MediaPlayer.create(this, R.raw.healingbeat);
    }

    @Override
    protected void onPause() {
        super.onPause();  // Always call the superclass method first
        //Toast.makeText(getApplicationContext(), "onPause", Toast.LENGTH_LONG).show();
        player.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();  // Always call the superclass method first
        //Toast.makeText(getApplicationContext(), "onStart", Toast.LENGTH_LONG).show();
        player.start();
    }
}