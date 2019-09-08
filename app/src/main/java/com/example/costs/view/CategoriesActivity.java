package com.example.costs.view;

import android.os.Bundle;

import com.example.costs.R;
import com.example.costs.database.CategoryDB;
import com.example.costs.database.CostDB;
import com.example.costs.database.model.Category;
import com.example.costs.database.model.Cost;
import com.example.costs.utils.MyDividerItemDecoration;
import com.example.costs.utils.RecyclerTouchListener;
import com.example.costs.view.adapters.CategoriesAdapter;
import com.example.costs.view.adapters.CostsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    private List<Category> catsList = new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setRecyclerView();
    }

    private void setRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCategories);

        CategoryDB db = new CategoryDB(this);
        catsList = db.getAllCategories();

        CategoriesAdapter mAdapter = new CategoriesAdapter(this, catsList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Category cost = catsList.get(position);
                Snackbar.make(view, cost.getName()+" "+cost.getDescription(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                //showActionsDialog(position);
            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case( android.R.id.home):
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
