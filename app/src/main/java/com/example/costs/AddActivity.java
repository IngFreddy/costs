package com.example.costs;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { addNewCosts(view); }
        });

        EditText addDateEdt = findViewById(R.id.editTextAddDate);
//        addDateEdt.setAutofillId();
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
        String price = addPriceEdt.getText().toString();

        Snackbar.make(view, name+date+desc+price, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

}
