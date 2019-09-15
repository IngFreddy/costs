package com.example.costs.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.costs.R;
import com.example.costs.database.CategoryDB;
import com.example.costs.database.CostDB;
import com.example.costs.database.model.Cost;
import com.example.costs.database.DatabaseHelper;
import com.example.costs.utils.MyDividerItemDecoration;
import com.example.costs.utils.RecyclerTouchListener;
import com.example.costs.view.adapters.CostsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class CostsActivity extends AppCompatActivity {

    private List<Cost> costsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private String date = "";
    private int category_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costs);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();

        date = intent.getStringExtra("date");               //for selecting day
        String price = intent.getStringExtra("price");      //total price to show
        category_id = intent.getIntExtra("category", 0);   //category ID when calling from category

        loadCostsList();
        setRecyclerView();

        TextView totalPrice = findViewById(R.id.totalDayPrice);
        totalPrice.setText(date + "  " + price+"€");

        FloatingActionButton addNew = findViewById(R.id.addNewCosts);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {createNewCost();}
        });

    }

    private void loadCostsList(){
        try {
            if (this.category_id == 0) {        //no category selected
                CostDB db = new CostDB(this);
                costsList = db.getCostsForDate(date);
            } else {                            //selected category
                CategoryDB db = new CategoryDB(this);
                costsList = db.getAllCostsForCategory(category_id);
            }
        }
        catch (Exception e){
            Snackbar.make(this.getCurrentFocus(), "Error reading costs list: "+ e.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            costsList = new ArrayList<>();
        }
    }

    private void setRecyclerView(){
        recyclerView = findViewById(R.id.recyclerViewCosts);

        CostsAdapter mAdapter = new CostsAdapter(this, costsList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Cost cost = costsList.get(position);
                Snackbar.make(view, cost.getName()+" "+cost.getDescription(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }


    /**
     * Open at long press on cost item
     * EDIT/DELETE
     * @param position - position clicked item
     */
    private void showActionsDialog(final int position) {
        CharSequence options[] = new CharSequence[]{"Edit", "Delete"};
        final Cost cost = costsList.get(position);

        final Context context = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {   //EDIT
                    Intent myIntent = new Intent(CostsActivity.this, AddActivity.class);
                    myIntent.putExtra("date", date);
                    myIntent.putExtra("update", 1);
                    myIntent.putExtra("id", cost.getId());

                    CostsActivity.this.startActivityForResult(myIntent, 1 );
                }

                else {              //DELETE
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == DialogInterface.BUTTON_POSITIVE) {
                                CostDB db = new CostDB(context);
                                db.deleteCost(cost.getId());

                                loadCostsList();
                                setRecyclerView();
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            }
        });
        builder.show();
    }

    private void createNewCost(){
        Intent myIntent = new Intent(CostsActivity.this, AddActivity.class);
        myIntent.putExtra("date", date);

        CostsActivity.this.startActivityForResult(myIntent, 1 );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("DEBUG", "End of Activity. Result:"+resultCode);
        switch(resultCode){
            case 1:
                loadCostsList();
                setRecyclerView();
                break;
            case RESULT_CANCELED:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_costs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case(R.id.action_add):
                createNewCost();
                break;

            case(R.id.action_refresh):
                loadCostsList();
                setRecyclerView();
                break;

            case(R.id.action_categories):
                Intent myIntent = new Intent(CostsActivity.this, CategoriesActivity.class);
                CostsActivity.this.startActivity(myIntent);
                break;

            case(R.id.action_settings):
                return true;

            case( android.R.id.home):
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
