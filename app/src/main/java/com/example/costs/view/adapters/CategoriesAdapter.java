package com.example.costs.view.adapters;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.costs.R;
import com.example.costs.database.model.Category;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
    private Context context;
    private List<Category> catsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView dot;
        public TextView description;
        public TextView price;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.nameCat);
            dot = view.findViewById(R.id.dotCat);
            description = view.findViewById(R.id.descriptionCat);
            price = view.findViewById(R.id.priceCat);
        }
    }


    public CategoriesAdapter(Context context, List<Category> catsList) {
        this.context = context;
        this.catsList = catsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cat_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        Category cat = catsList.get(position);

        holder.dot.setText(Html.fromHtml("&#8226;"));
        holder.dot.setTextColor(ColorStateList.valueOf(cat.getColour()));

        holder.name.setText(cat.getName());
        holder.description.setText(cat.getDescription());
        holder.price.setText(decimalFormat.format(cat.getPrice()) + "€");
    }

    @Override
    public int getItemCount() {
        return catsList.size();
    }

}
