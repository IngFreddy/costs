package com.example.costs;

import android.content.Intent;
import android.os.Bundle;

import com.example.costs.model.CostDB;
import com.example.costs.model.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();

        String date = intent.getStringExtra("date");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { addNewCosts(view); }
        });

        EditText addDateEdt = findViewById(R.id.editTextAddDate);
        addDateEdt.setText(date);

        ImageButton imgBtn = findViewById(R.id.imgBtnRecipe);
        //imgBtn.setOnClickListener(nnew View.on);

    }


    private void addNewCosts(View view){
        EditText addNameEdt = findViewById(R.id.editTextAddName);
        EditText addDateEdt = findViewById(R.id.editTextAddDate);
        EditText addDescEdt = findViewById(R.id.editTextAddDescription);
        EditText addPriceEdt = findViewById(R.id.editTextAddPrice);

        String name = addNameEdt.getText().toString();

        String date = addDateEdt.getText().toString();
        String desc = addDescEdt.getText().toString();
        float price = Float.valueOf(addPriceEdt.getText().toString());

        Snackbar.make(view, name+date+desc+price, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        DatabaseHelper db = new DatabaseHelper(this);
        db.insertCost(name, price, date);


        //MainActivity.setRecyclerView();

        finish();
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
