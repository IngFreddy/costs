package com.example.costs.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.costs.R;
import com.example.costs.database.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActivityCompat.requestPermissions(SettingsActivity.this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                100);

        Button importBtn = findViewById(R.id.btnImport);
        importBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importDB(view);
            }
        });

        Button exportBtn = findViewById(R.id.btnExport);
        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportDB(view);
            }
        });
    }

    private void copyFile(File input, File output) throws IOException {
        if(!output.exists()){
            output.getParentFile().mkdirs();
            output.createNewFile();
        }

        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = new FileInputStream(input).getChannel();
            toChannel = new FileOutputStream(output).getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
                if (toChannel != null) {
                    toChannel.close();
                }
            } catch (Exception j){
                Toast.makeText(this, "Error writing file " + j.getMessage(), Toast.LENGTH_LONG);
            }
        }

    }

    private void importDB(View view) {
        final EditText importEDT = findViewById(R.id.importPath);
        DatabaseHelper db = new DatabaseHelper(this);
        db.close();

        try {
            File inFile = new File("/sdcard/" + importEDT.getText().toString());
            File outFile = new File(this.getDatabasePath(DatabaseHelper.DATABASE_NAME).getAbsolutePath());
            copyFile(inFile, outFile);

        } catch (IOException e) {
            Snackbar.make(view, "Error writing file: "+ e.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        finally {
            Snackbar.make(view, "FILE imported /sdcard/"+ importEDT.getText().toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            db.getWritableDatabase();
        }
    }

    private void exportDB(View view) {
        final EditText exportEDT = findViewById(R.id.exportPath);

        try {
            File outFile = new File("/sdcard/" + exportEDT.getText().toString());
            File inFile = new File(this.getDatabasePath(DatabaseHelper.DATABASE_NAME).getAbsolutePath());
            copyFile(inFile, outFile);

        } catch (IOException e) {
            Snackbar.make(view, "Error writing file: "+ e.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } finally {
            Snackbar.make(view, "FILE exported /sdcard/"+ exportEDT.getText().toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
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
