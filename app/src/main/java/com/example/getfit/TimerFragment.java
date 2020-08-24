package com.example.getfit;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class TimerFragment extends Fragment {
    EditText hour, min, sec;
    ImageButton pp, reset;
    int hourT, minT, secT,flag;
    Boolean running;
    long millis,count;
    CountDownTimer countDownTimer;
    KeyListener keyListener;
    View view;
    ProgressBar progressBar;

    public TimerFragment() {
        // Required empty public constructor
    }

    public static TimerFragment newInstance() {
        TimerFragment fragment = new TimerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_timer, container, false);
        hour = (EditText) view.findViewById(R.id.hour);
        min = (EditText) view.findViewById(R.id.min);
        sec = (EditText) view.findViewById(R.id.second);
        pp = (ImageButton) view.findViewById(R.id.playpause);
        reset = (ImageButton) view.findViewById(R.id.reset);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        running = false;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        keyListener = hour.getKeyListener();
        flag = 0;
        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (running) {
                    running = false;
                    pause();
                } else {
                    running = true;
                    hourT = hour.getText().toString().equals("") ? 0 : Integer.parseInt(hour.getText().toString());
                    minT = min.getText().toString().equals("") ? 0 : Integer.parseInt(min.getText().toString());
                    secT = sec.getText().toString().equals("") ? 0 : Integer.parseInt(sec.getText().toString());
                    millis = (hourT * 60 * 60 + minT * 60 + secT) * 1000;
                    if(flag == 0){
                        count = millis;
                        flag++;
                    }
                    if (millis == 0) {
                        err();
                    }
                    else {
                        play();
                        enableEdit(hour);
                        enableEdit(min);
                        enableEdit(sec);
                    }
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });
    }

    private void enableEdit(TextView text) {
        if (running) {
            text.setFocusable(false);
            text.setFocusableInTouchMode(false);
            text.setKeyListener(null);
        } else {
            text.setFocusable(true);
            text.setFocusableInTouchMode(true);
            text.setKeyListener(keyListener);
        }
    }

    private void err() {
        Toast.makeText(getContext(), "Enter Valid Data", Toast.LENGTH_SHORT).show();
    }

    private void play() {
        countDownTimer = new CountDownTimer(millis, 1000) {
            @Override
            public void onTick(long l) {
                millis = l;
                update();
            }

            @Override
            public void onFinish() {
                clear();
                MediaPlayer ring = MediaPlayer.create(getContext(),R.raw.win1);
                ring.start();
            }
        }.start();
        Resources resources = getResources();
        pp.setImageDrawable(resources.getDrawable(R.drawable.pause));
    }

    private void pause() {
        Resources resources = getResources();
        pp.setImageDrawable(resources.getDrawable(R.drawable.play));
        countDownTimer.cancel();
    }

    private void clear() {
        Resources resources = getResources();
        countDownTimer.cancel();
        running = false;
        pp.setImageDrawable(resources.getDrawable(R.drawable.play));
        millis = 0;
        flag = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(0,true);
        }
        enableEdit(hour);
        enableEdit(min);
        enableEdit(sec);
        hour.setText("");
        min.setText("");
        sec.setText("");
    }

    private void update() {
        hourT = (int) ((millis / 1000) / 60 / 3600);
        minT = (int) ((millis / 1000) / 60) % 3600;
        secT = (int) ((millis / 1000) % (60));
        hour.setText("" + hourT);
        min.setText("" + minT);
        sec.setText("" + secT);
        long q = (count - millis);
        long p =  (long)(((float)q/count*100)+5);
        Log.d("X",""+q);
        Log.d("X",""+p);
        Log.d("X",""+millis);
        Log.d("X",""+count );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress((int) p,true);
        }
    }
}
