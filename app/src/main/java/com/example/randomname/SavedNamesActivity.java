package com.example.randomname;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SavedNamesActivity extends AppCompatActivity{

    RecyclerView rv_saved_names;

    ArrayList<String> namesArrayList = new ArrayList<String>();

//    String[] listOfNames = MainActivity.getNames();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_names);
        DatabaseHelper databaseHelper = new DatabaseHelper(SavedNamesActivity.this);
        List<SavedNameModel> everySavedName = databaseHelper.getAllNames();
        for (int i = 0; i < everySavedName.size(); i++) {
            namesArrayList.add(everySavedName.get(i).toString());
        }
        String[] namesArray = namesArrayList.toArray(new String[0]);
        rv_saved_names = findViewById(R.id.rv_saved_names);
        NameAdapter adapter = new NameAdapter(this, namesArray);
        rv_saved_names.setAdapter(adapter);
        rv_saved_names.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.nav_to_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
