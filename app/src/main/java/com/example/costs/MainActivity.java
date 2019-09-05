package com.example.costs;

import android.content.Intent;
import android.os.Bundle;

import com.example.costs.model.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.DefaultItemAnimator;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.costs.utils.*;
import com.example.costs.model.CostDB;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<CostDB> daysList = new ArrayList<>();
    private DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setRecyclerView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {createNewCost();}
        });

    }

    /**
     * READ informations from database
     * SETUP RecyclerView and FILL
     */
    public void setRecyclerView(){
        recyclerView = findViewById(R.id.recyclerViewDates);

        DatabaseHelper db = new DatabaseHelper(this);

        daysList = db.getAllDates();
        CostsAdapter mAdapter = new CostsAdapter(this, daysList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                onDayRowClick(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                CostDB clicked = daysList.get(position);
                Snackbar.make(view, "Long Press Recycler " + clicked.getName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }));

    }

    private void createNewCost(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String today = dateFormat.format(date);

        Intent myIntent = new Intent(MainActivity.this, AddActivity.class);
        myIntent.putExtra("date", today);

        MainActivity.this.startActivity(myIntent);
    }

    /**
     * SELECTED day for detail, show costs_activity
     * @param position selected row
     */
    private void onDayRowClick(int position){
        CostDB clicked = daysList.get(position);

        Intent myIntent = new Intent(MainActivity.this, CostsActivity.class);
        myIntent.putExtra("date", clicked.getTimestamp());
        myIntent.putExtra("price", decimalFormat.format(clicked.getprice()));

        MainActivity.this.startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_add) {
            createNewCost();
        }

        if (id == R.id.action_refresh) {
            setRecyclerView();
        }

        return super.onOptionsItemSelected(item);
    }
}
