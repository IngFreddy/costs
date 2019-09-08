package com.example.costs.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.costs.R;
import com.example.costs.database.CategoryDB;
import com.example.costs.database.CostDB;
import com.example.costs.database.DatabaseHelper;
import com.example.costs.database.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private List<Category> categories = new ArrayList<>();

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
        setResult(50, intent);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { addNewCosts(view); }
        });

        EditText addDateEdt = findViewById(R.id.editTextAddDate);
        addDateEdt.setText(date);

        ImageButton imgBtn = findViewById(R.id.imgBtnRecipe);
        //imgBtn.setOnClickListener(nnew View.on);

        getCategories();

    }

    private void getCategories(){
        CategoryDB db = new CategoryDB(this);
        categories = db.getAllCategories();

        Spinner addCategSpin = findViewById(R.id.spinnerChooseCategorie);

        List<String> spinnerArray =  new ArrayList<String>();
        for(Category cat: categories){
            spinnerArray.add(cat.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCategSpin.setAdapter(adapter);
    }


    private void addNewCosts(View view){
        EditText addNameEdt = findViewById(R.id.editTextAddName);
        EditText addDateEdt = findViewById(R.id.editTextAddDate);
        EditText addDescEdt = findViewById(R.id.editTextAddDescription);
        EditText addPriceEdt = findViewById(R.id.editTextAddPrice);
        Spinner addCategSpin = findViewById(R.id.spinnerChooseCategorie);

        String name = addNameEdt.getText().toString();
        float price = 0f;
        String date = addDateEdt.getText().toString();
        String desc = addDescEdt.getText().toString();
        int cats = categories.get(addCategSpin.getSelectedItemPosition()).getId();

        try {
            price = Float.valueOf(addPriceEdt.getText().toString());
        }
        catch (Exception e){
            Snackbar.make(view, "NOT VALID PRICE: "+addPriceEdt.getText(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            Log.d("FLOAT",e.getMessage());

            return;
        }

        Snackbar.make(view, "SAVED:" + name+desc+price+date, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        try {
            CostDB db = new CostDB(this);
            db.insertCost(name, desc, price, date, cats);
        }
        catch (Exception e){
            Snackbar.make(view, "ERROR writing in database:"+e.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        Intent intent = getIntent();
        setResult(RESULT_OK,intent);

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
            Intent intent = getIntent();
            setResult(RESULT_CANCELED, intent);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
