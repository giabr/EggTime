package com.giabusyra.eggtime;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    Boolean counterIsActive = false;
    Button button;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        //Reset or stop or no time left
        textView.setText("0:30");
        seekBar.setProgress(30);
        countDownTimer.cancel();
        button.setText("Go");
        seekBar.setEnabled(true);
        counterIsActive = false;
    }

    public void updateTimer(int secondLeft)
    {
        //Display minute and second in textview
        int minutes = (int) secondLeft/60;
        int second = (int) secondLeft - minutes*60;

        String secondString = String.valueOf(second);
        if(second <= 9){
            secondString="0"+secondString;
        }
        textView.setText(String.valueOf(minutes+":"+secondString));
    }

    //Countdown
    public void controlTimer(View view){

        if (counterIsActive == false){
        Log.i("Button", "Pressed");
        counterIsActive = true;
        seekBar.setEnabled(false);
        button.setText("Stop");

        countDownTimer = new CountDownTimer(seekBar.getProgress()*1000+100,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int) millisUntilFinished/1000);
            }
            @Override
            public void onFinish() {
                Log.i("Finish","Timer Done");
                textView.setText("0:00");
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell);
                mediaPlayer.start();
                resetTimer();
            }
        }.start();
        } else{
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize
        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.timer);
        button = findViewById(R.id.button);

        //Seekbar
        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Call function update from seekbar
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
