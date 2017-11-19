package com.atifnaseem.mymediaplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;
    SeekBar sb;
    int SeekValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = new MediaPlayer();

        sb = (SeekBar) findViewById(R.id.MediaSeekBar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SeekValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(SeekValue);
            }
        });

        SeekThread seekThread = new SeekThread();
        seekThread.start();
    }

    public void PlayMedia(View view) {
        try{
            mp = new MediaPlayer();
            mp.setDataSource("http://server6.mp3quran.net/thubti/001.mp3");
            mp.prepare();
            mp.start();
            sb.setMax(mp.getDuration());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void PauseMedia(View view) {
        if(mp.isPlaying()){
            mp.pause();
        }
    }

    public void StopMedia(View view) {
        if(mp.isPlaying()){
            mp.release();
            mp = null;
        }
    }



    class SeekThread extends Thread {
        public void run() {
            while(true){
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mp!=null){
                            sb.setProgress(mp.getCurrentPosition());
                        }
                    }
                });
            }
        }
    }
}
