package com.example.final_main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    List<HistoryData> historyData;
    int size;

    public MyAdapter(Context ct, List<HistoryData> data, int size) {
        context = ct;
        historyData = data;
        this.size = size;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Map<String, Object> map = (Map<String, Object>) historyData.get(position);
        holder.time.setText("Time: " + map.get("time"));
        holder.date.setText("Date: " + map.get("date"));
        holder.latitude.setText("Latitude: " + map.get("latitude"));
        holder.longitude.setText("Longitude: " + map.get("longitude"));
        holder.yourSpeed.setText(map.get("yourSpeed").toString());
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView time, date, yourSpeed, latitude, longitude;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);
            yourSpeed = itemView.findViewById(R.id.yourSpeed);

        }

    }
}
