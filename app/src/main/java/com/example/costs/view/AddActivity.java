package com.example.costs.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.costs.R;
import com.example.costs.database.CategoryDB;
import com.example.costs.database.CostDB;
import com.example.costs.database.DatabaseHelper;
import com.example.costs.database.model.Category;
import com.example.costs.database.model.Cost;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private List<Category> categories = new ArrayList<>();
    private String date;
    private int _id;
    private int update = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // ENABLE TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button save=new Button(this);       //SAVE BUTTON TOOLBAR
        save.setBackground(getDrawable(R.drawable.ic_done_button_white));
        Toolbar.LayoutParams tl=new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        tl.gravity= Gravity.END;
        save.setLayoutParams(tl);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { addNewCosts(view); }
        });
        toolbar.addView(save);

        // SAVE BUTTON
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { addNewCosts(view); }
        });


        ImageButton imgBtn = findViewById(R.id.imgBtnRecipe);
        //imgBtn.setOnClickListener(new View.on);

        getCategories();        // FILL CATEGORIES SPINNER FROM DB

        // GET DATA FROM PARENT ACTIVITY
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        update = intent.getIntExtra("update", 0);
        if(update == 1){
            updatingCost(intent.getIntExtra("id", 0));
        }

        EditText addDateEdt = findViewById(R.id.editTextAddDate);
        addDateEdt.setText(date);
    }


    /**
     * UPDATING cost in database
     * prefilll informations into formullar
     * @param costId    ID of editing item
     */
    private void updatingCost(int costId){
        final EditText addNameEdt = findViewById(R.id.editTextAddName);
        final EditText addDescEdt = findViewById(R.id.editTextAddDescription);
        final EditText addPriceEdt = findViewById(R.id.editTextAddPrice);
        final Spinner addCategSpin = findViewById(R.id.spinnerChooseCategorie);

        Cost cost;
        try {
            CostDB db = new CostDB(this);
            cost = db.selectCost(costId);

            addNameEdt.setText(cost.getName());
            addDescEdt.setText(cost.getDescription());
            addPriceEdt.setText(cost.getPrice(true));
            int i=0;
            for(; i<categories.size(); i++){
                if(categories.get(i).getId() == cost.getCategoryID())  break;
            }
            addCategSpin.setSelection(i);

            this._id = costId;
        }
        catch (NoSuchFieldException e){
            Snackbar.make(this.getCurrentFocus(), "Error reading cost DB: "+ e.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    /**
     * READ from DB and FILL categories into Spinner
     */
    private void getCategories(){
        try {
            CategoryDB db = new CategoryDB(this);
            categories = db.getAllCategories();

            Spinner addCategSpin = findViewById(R.id.spinnerChooseCategorie);

            List<String> spinnerArray = new ArrayList<String>();
            for (Category cat : categories) {
                spinnerArray.add(cat.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            addCategSpin.setAdapter(adapter);
        }
        catch (Exception e){
            Snackbar.make(this.getCurrentFocus(), "ERROR reading Categories: "+ e.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    /**
     * SAVE cost into DB with filled informations
     * - name, description, price, date, category
     * @param view
     */
    private void addNewCosts(View view){
        final EditText addNameEdt = findViewById(R.id.editTextAddName);
        final EditText addDescEdt = findViewById(R.id.editTextAddDescription);
        final EditText addPriceEdt = findViewById(R.id.editTextAddPrice);
        final EditText addDateEdt = findViewById(R.id.editTextAddDate);
        final Spinner addCategSpin = findViewById(R.id.spinnerChooseCategorie);

        String name = addNameEdt.getText().toString();      //read input
        float price = 0f;
        String date = addDateEdt.getText().toString();
        String desc = addDescEdt.getText().toString();
        int cat = categories.get(addCategSpin.getSelectedItemPosition()).getId();  //category ID

        try {
            price = Float.valueOf(addPriceEdt.getText().toString());        //parse price into float
        }
        catch (Exception e){
            Snackbar.make(view, "NOT VALID PRICE: "+addPriceEdt.getText(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        try {
            CostDB db = new CostDB(this);
            if(update == 1){    // UPDATE
                db.updateCost(_id, name, desc, price, date, cat);
            }
            else {              // ADD NEW
                db.insertCost(name, desc, price, date, cat);
            }
        }
        catch (Exception e){
            Snackbar.make(view, "ERROR writing in database: "+e.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        setResult(1);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {        //GO BACK
            setResult(RESULT_CANCELED);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
