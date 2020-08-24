package com.example.getfit.Stopwatch;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.getfit.R;

import java.util.ArrayList;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;


public class StopwatchFragment extends Fragment {
    Chronometer chronometer;
    ImageButton pp, reset, flag;
    boolean running;
    long offset;
    RecyclerView recyclerView;
    ArrayList<String> flags;
    RecyclerAdapter adapter;

    public StopwatchFragment() {
        // Required empty public constructor
    }

    public static StopwatchFragment newInstance() {
        StopwatchFragment fragment = new StopwatchFragment();
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
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        flags = new ArrayList<>();
        adapter = new RecyclerAdapter();
        chronometer = (Chronometer) view.findViewById(R.id.stopwatch);
        pp = (ImageButton) view.findViewById(R.id.playpause);
        reset = (ImageButton) view.findViewById(R.id.reset);
        flag = (ImageButton) view.findViewById(R.id.flag);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chronometer.setBase(SystemClock.elapsedRealtime());
        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (running) {
                    stop();
                } else
                    start();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flagset();
            }
        });

    }

    public void start() {
        flag.setVisibility(View.VISIBLE);
        chronometer.setBase(SystemClock.elapsedRealtime() - offset);
        chronometer.start();
        running = true;
        Resources resources = getResources();
        pp.setImageDrawable(resources.getDrawable(R.drawable.pause));
    }

    public void stop() {
        chronometer.stop();
        flag.setVisibility(View.INVISIBLE);
        offset = SystemClock.elapsedRealtime() - chronometer.getBase();
        running = false;
        Resources resources = getResources();
        pp.setImageDrawable(resources.getDrawable(R.drawable.play));
    }

    public void reset() {
        flag.setVisibility(View.VISIBLE);
        chronometer.setBase(SystemClock.elapsedRealtime());
        offset = 0;
        running = false;
        chronometer.stop();
        flags = new ArrayList<>();
        adapter.notifyset(flags);
        Resources resources = getResources();
        pp.setImageDrawable(resources.getDrawable(R.drawable.play));
    }

    public void flagset() {
        long time = SystemClock.elapsedRealtime() - chronometer.getBase();
        int h = (int) (time / 3600000);
        int m = (int) (time - h * 3600000) / 60000;
        int s = (int) (time - h * 3600000 - m * 60000) / 1000;
        String t = "" + h + ":" + m + ":" + s;
        flags.add(t);
        adapter.notifyset(flags);
    }
}
