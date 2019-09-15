package com.example.costs.view.adapters;


import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.costs.R;
import com.example.costs.database.model.Cost;


public class CostsAdapter extends RecyclerView.Adapter<CostsAdapter.MyViewHolder> {
    private Context context;
    private List<Cost> costsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView note;
        public TextView dot;
        public TextView timestamp;
        public TextView price;

        public MyViewHolder(View view) {
            super(view);
            note = view.findViewById(R.id.note);
            dot = view.findViewById(R.id.dot);
            timestamp = view.findViewById(R.id.timestamp);
            price = view.findViewById(R.id.price);
        }
    }


    public CostsAdapter(Context context, List<Cost> costsList) {
        this.context = context;
        this.costsList = costsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.costs_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Cost note = costsList.get(position);

        holder.dot.setText(Html.fromHtml("&#8226;"));
        holder.dot.setTextColor(ColorStateList.valueOf(note.getColor()));

        holder.note.setText(note.getName());
        holder.timestamp.setText(note.getTimestamp());
        holder.price.setText(note.getPrice(true) + "€");
    }

    @Override
    public int getItemCount() {
        return costsList.size();
    }

}
