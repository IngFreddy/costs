package com.example.costs.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.costs.database.CategoryDB;
import com.example.costs.database.CostDB;
import com.example.costs.database.model.Category;
import com.example.costs.view.adapters.CategoriesAdapter;
import com.example.costs.view.adapters.DaysAdapter;
import com.example.costs.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.DefaultItemAnimator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.costs.utils.*;
import com.example.costs.database.model.Cost;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Cost> daysList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            setRecyclerView();
        }
        catch (Exception e){
            View view = findViewById(android.R.id.content);
            Snackbar.make(view, "Error in reading database! " + e.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

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

        CostDB db = new CostDB(this);
        daysList = db.getAllDates();

        DaysAdapter mAdapter = new DaysAdapter(this, daysList);

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
                Cost clicked = daysList.get(position);
                Snackbar.make(view, "Long Press Recycler " + clicked.getName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }));

        float totalPrice = 0.0f;
        for(Cost day: daysList){
            totalPrice += day.getPrice();
        }

        TextView totalP = findViewById(R.id.totalPrice);
        totalP.setText("Total price: "+ totalPrice + "€");

    }

    private void createNewCost(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String today = dateFormat.format(date);

        Intent myIntent = new Intent(MainActivity.this, AddActivity.class);
        myIntent.putExtra("date", today);
        MainActivity.this.startActivityForResult(myIntent, 1 );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("DEBUG", "End of Activity. Result:"+resultCode);
        switch(resultCode){
            case 1:
                setRecyclerView();
                break;
            case RESULT_CANCELED:
                System.out.println("ERROR creating Cost");
                break;
        }
    }

    /**
     * SELECTED day for detail, show costs_activity
     * @param position selected row
     */
    private void onDayRowClick(int position){
        Cost clicked = daysList.get(position);

        Intent myIntent = new Intent(MainActivity.this, CostsActivity.class);
        myIntent.putExtra("date", clicked.getTimestamp());
        myIntent.putExtra("price", clicked.getPrice(true));

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
        switch (item.getItemId()){
            case(R.id.action_add):
                createNewCost();
                setRecyclerView();
                break;

            case(R.id.action_refresh):
                setRecyclerView();
                break;

            case(R.id.action_categories):
                Intent myIntent = new Intent(MainActivity.this, CategoriesActivity.class);
                MainActivity.this.startActivity(myIntent);
                break;

            case(R.id.action_settings):
                return true;

            case( android.R.id.home):
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
