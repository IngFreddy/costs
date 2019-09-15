package com.example.costs.view.adapters;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.costs.R;
import com.example.costs.database.model.Cost;


public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.MyViewHolder> {
    private Context context;
    private List<Cost> daysList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dot;
        public TextView timestamp;
        public TextView price;

        public MyViewHolder(View view) {
            super(view);
            dot = view.findViewById(R.id.dot);
            timestamp = view.findViewById(R.id.timestamp);
            price = view.findViewById(R.id.price);
        }
    }


    public DaysAdapter(Context context, List<Cost> costsList) {
        this.context = context;
        this.daysList = costsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Cost note = daysList.get(position);

        holder.dot.setText(Html.fromHtml("&#8226;"));

        holder.timestamp.setText(note.getTimestamp());
        holder.price.setText(note.getPrice(true) + "€");
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}
