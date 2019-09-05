package com.example.costs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.costs.model.CostDB;
import com.example.costs.model.DatabaseHelper;
import com.example.costs.utils.MyDividerItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CostsActivity extends AppCompatActivity {

    private List<CostDB> costsList = new ArrayList<>();
    private RecyclerView recyclerView;
    String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costs);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();

        date = intent.getStringExtra("date");
        String price = intent.getStringExtra("price");

        setRecyclerView(date);

        TextView totalPrice = findViewById(R.id.totalDayPrice);
        totalPrice.setText(date + "  " + price+"€");

        FloatingActionButton addNew = findViewById(R.id.addNewCosts);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {createNewCost();}
        });

    }

    private void setRecyclerView(String date){
        recyclerView = findViewById(R.id.recyclerViewCosts);

        DatabaseHelper db = new DatabaseHelper(this);
        costsList = db.getCostsForDate(date);

        CostsAdapter mAdapter = new CostsAdapter(this, costsList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

    }

    private void createNewCost(){
        Intent myIntent = new Intent(CostsActivity.this, AddActivity.class);
        myIntent.putExtra("date", date);

        CostsActivity.this.startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_costs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
