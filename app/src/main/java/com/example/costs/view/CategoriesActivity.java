package com.example.costs.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.costs.R;
import com.example.costs.database.CategoryDB;
import com.example.costs.database.CostDB;
import com.example.costs.database.model.Category;
import com.example.costs.utils.MyDividerItemDecoration;
import com.example.costs.utils.RecyclerTouchListener;
import com.example.costs.view.adapters.CategoriesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.github.naz013.colorslider.ColorSlider;

public class CategoriesActivity extends AppCompatActivity {
    private List<Category> catsList = new ArrayList<Category>();
    private static int selectedColour = 0;

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
                createCategory(false, null);
            }
        });

        setRecyclerView();
    }

    private void setRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCategories);

        CategoryDB db = new CategoryDB(this);
        catsList = db.getAllCategoriesPrices();

        CategoriesAdapter mAdapter = new CategoriesAdapter(this, catsList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Category cat = catsList.get(position);
                Snackbar.make(view, cat.getDescription(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }

    private void showActionsDialog(final int position) {
        CharSequence options[] = new CharSequence[]{"Edit", "Delete"};

        final Context context = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {   //EDIT
                    createCategory(true, catsList.get(position));
                }

                else {              //DELETE
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == DialogInterface.BUTTON_POSITIVE) {
                                CategoryDB db = new CategoryDB(context);
                                db.deleteCategory(catsList.get(position).getId());
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

    public void createCategory(final boolean update, final Category cat) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        final View view = layoutInflaterAndroid.inflate(R.layout.dialog_category, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(view);

        final TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!update ? getString(R.string.newCategory) : getString(R.string.editCategory));

        final EditText catName = view.findViewById(R.id.categoryName);
        final EditText catDescription = view.findViewById(R.id.categoryDescription);
        final ColorSlider colorSlider = findViewById(R.id.color_slider);

        if (update && cat != null) {
            catName.setText(cat.getName());
            catDescription.setText(cat.getDescription());
            selectedColour = cat.getColour();
            colorSlider.selectColor(selectedColour);
        }

        alertDialogBuilderUserInput.setCancelable(false)
                .setPositiveButton(update ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {}})
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        dialogBox.cancel();
                    }
                });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        final CategoryDB db = new CategoryDB(this);

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorSlider colorSlid = view.findViewById(R.id.color_slider);
                int newColor = colorSlid.getSelectedColor();

                if (TextUtils.isEmpty(catName.getText().toString())) {
                    Toast.makeText(CategoriesActivity.this, "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (update) {    //updating
                    Category newCat = new Category(cat.getId(), catName.getText().toString(), catDescription.getText().toString(), newColor);
                    db.updateCategory(newCat);
                } else {                        //creating new
                    db.insertCategory(catName.getText().toString(), catDescription.getText().toString(), newColor);
                }
                alertDialog.dismiss();
                setRecyclerView();
            }
        });
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
