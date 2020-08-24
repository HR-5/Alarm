package com.example.getfit.Alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getfit.R;

import java.io.IOException;
import java.util.Random;

public class AlarmActivity extends AppCompatActivity {
    int count;
    TextView solve;
    Button sub, cancel;
    Runnable runnable;
    Handler handler;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        handler = new Handler();
        Intent i = getIntent();
        count = i.getIntExtra("alarm", 0);
        solve = (TextView) findViewById(R.id.problem);
        sub = (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.cancel);
        final EditText answer = (EditText) findViewById(R.id.editText);
        Random rnd = new Random();
        int x = rnd.nextInt(15);
        int y = rnd.nextInt(15);
        final int ans = x * y;
        String q = "" + x + "*" + y + " = ";
        solve.setText(q);
        uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + getRing());
        final MediaPlayer ring;
        ring = MediaPlayer.create(this, uri);
        ring.start();
        ring(ring);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answer.getText().equals(""))
                    error();
                else if (Integer.parseInt(String.valueOf(answer.getText()))==ans)
                    cancel.setVisibility(View.VISIBLE);
                else
                    wrong();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(runnable);
                ring.stop();
                finish();
            }
        });
    }
    private void wrong(){
        Toast.makeText(this,"Wrong Answer",Toast.LENGTH_SHORT).show();
    }

    private void error(){
        Toast.makeText(this,"Enter valid data",Toast.LENGTH_SHORT).show();
    }

    private int getRing() {
        switch (count) {
            case 0:
                return R.raw.classic;
            case 1:
                return R.raw.jazz;
            case 2:
                return R.raw.loving;
            case 3:
                return R.raw.pleasant;

        }
        return 0;
    }
    private void ring(final MediaPlayer ring){
        runnable = new Runnable() {
            @Override
            public void run() {
                ring.reset();
                try {
                    ring.setDataSource(getApplicationContext(),uri);
                    ring.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ring.start();
                handler.postDelayed(runnable,15*1000);
            }
        };
        handler.postDelayed(runnable,15*1000);
    }
}
