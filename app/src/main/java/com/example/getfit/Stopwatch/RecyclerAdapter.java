package com.example.getfit.Stopwatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getfit.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<String> times;

    public RecyclerAdapter() {
        times = new ArrayList<String>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new RecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.detail.setText(times.get(position));
        holder.num.setText(""+(position+1)+".");
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public void notifyset(ArrayList<String> flags){
        times = new ArrayList<>();
        times.addAll(flags);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView detail;
        private TextView num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detail = (TextView) itemView.findViewById(R.id.label);
            num = (TextView) itemView.findViewById(R.id.num);
        }
    }
}
