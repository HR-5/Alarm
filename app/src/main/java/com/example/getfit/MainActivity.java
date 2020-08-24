package com.example.getfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.getfit.Alarm.AlarmFragment;
import com.example.getfit.Stopwatch.StopwatchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        AlarmFragment alarmFragment = AlarmFragment.newInstance();
        transaction.add(R.id.fragcontainer, alarmFragment, "Alarm").commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Menu menu = bottomNavigationView.getMenu();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            switch (menuItem.getItemId()) {
                case R.id.alarm:
                    if (!menu.getItem(0).isChecked()) {
                        AlarmFragment alarmFragment = AlarmFragment.newInstance();
                        transaction.add(R.id.fragcontainer, alarmFragment, "Alarm").commit();
                    }
                    break;
                case R.id.timer:
                    if (!menu.getItem(1).isChecked()) {
                        TimerFragment timerFragment = TimerFragment.newInstance();
                        transaction.add(R.id.fragcontainer, timerFragment, "Timer")
                                .commit();
                    }
                    break;
                case R.id.stopwatch:
                    if (!menu.getItem(2).isChecked()) {
                        StopwatchFragment stopwatchFragment = StopwatchFragment.newInstance();
                        transaction.add(R.id.fragcontainer, stopwatchFragment, "Stopwatch").commit();
                    }
                    break;
            }
            return true;
        }
    };
}
