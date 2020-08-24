package com.example.getfit.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.getfit.R;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class AlarmFragment extends Fragment {
    Calendar calendar;
    ImageButton setAlarm;
    ArrayList<Boolean> daycheck;
    AlarmManager alarmManager;
    ArrayList<String> ringtones;
    MediaPlayer ring;
    String alarm;
    TextView rname;
    int count;

    public AlarmFragment() {
        // Required empty public constructor
    }

    public static AlarmFragment newInstance() {
        AlarmFragment fragment = new AlarmFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
        ringtones = new ArrayList<>();
        ringtones.add("classic");
        ringtones.add("jazz");
        ringtones.add("loving");
        ringtones.add("pleasant");
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        daycheck = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            daycheck.add(i, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        TimePicker timePicker = (TimePicker) view.findViewById(R.id.clock);
        timePicker.setOnTimeChangedListener(timeChangedListener);
        setAlarm = (ImageButton) view.findViewById(R.id.setAlarm);
        rname = (TextView) view.findViewById(R.id.ringtone);
        rname.setText(ringtones.get(count));
        final ImageButton next = (ImageButton) view.findViewById(R.id.next);
        final ImageButton play = (ImageButton) view.findViewById(R.id.ringz);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextR(play);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playR(play);
            }
        });
        final TextView sun = view.findViewById(R.id.sun);
        final TextView mon = view.findViewById(R.id.mon);
        final TextView tue = view.findViewById(R.id.tue);
        final TextView wed = view.findViewById(R.id.wed);
        final TextView thu = view.findViewById(R.id.thu);
        final TextView fri = view.findViewById(R.id.fri);
        final TextView sat = view.findViewById(R.id.sat);
        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daycheck.get(0)) {
                    remove(sun, 0);
                } else {
                    add(sun, 0);
                }
            }
        });
        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daycheck.get(1)) {
                    remove(mon, 1);
                } else {
                    add(mon, 1);
                }
            }
        });
        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daycheck.get(2)) {
                    remove(tue, 2);
                } else {
                    add(tue, 2);
                }
            }
        });
        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daycheck.get(3)) {
                    remove(wed, 3);
                } else {
                    add(wed, 3);
                }
            }
        });
        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daycheck.get(4)) {
                    remove(thu, 4);
                } else {
                    add(thu, 4);
                }
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daycheck.get(5)) {
                    remove(fri, 5);
                } else {
                    add(fri, 5);
                }
            }
        });
        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daycheck.get(6)) {
                    remove(sat, 6);
                } else {
                    add(sat, 6);
                }
            }
        });
        return view;
    }

    private void nextR(ImageButton play) {
        play.setClickable(true);
        count++;
        if (count == 4)
            count = 0;
        alarm = ringtones.get(count);
        rname.setText(alarm);
        if (ring != null)
            ring.stop();
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

    private void playR(ImageButton play) {
        play.setClickable(false);
        Uri uri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + getRing());
        ring = MediaPlayer.create(getContext(), uri);
        ring.start();
    }

    private void add(TextView text, int index) {
        daycheck.set(index, true);
        text.setBackgroundColor(getResources().getColor(R.color.blue));
    }

    private void remove(TextView text, int index) {
        daycheck.set(index, false);
        text.setBackgroundColor(getResources().getColor(R.color.trans));
    }

    private TimePicker.OnTimeChangedListener timeChangedListener =
            new TimePicker.OnTimeChangedListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);
                }
            };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                Intent intent = new Intent(getContext(), AlarmReciever.class);
                intent.putExtra("alarm",count);
                int n = 0;
                for (int i = 0; i < 7; i++) {
                    if (daycheck.get(i)) {
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), i, intent, i);
                        setAlarm(pendingIntent, i);
                        n++;
                    }
                }
                if (n == 0)
                    Toast.makeText(getContext(), "Select Day of Week", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "Alarm is set", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setAlarm(PendingIntent pendingIntent, int wod) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        if (calendar.compareTo(cal) < 0) {
            cal.clear();
            cal = calendar;
            cal.add(Calendar.DATE, 7);
        } else {
            cal.clear();
            cal = calendar;
        }
        cal.set(Calendar.DAY_OF_WEEK, wod + 1);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 24*3600 * 1000, pendingIntent);
    }
}
