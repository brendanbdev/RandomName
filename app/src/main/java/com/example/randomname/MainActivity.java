package com.example.randomname;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView randomNameTextView;
    Button save_button;
    String currentRandomName;
    static String[] names = {"Rob", "Slob", "Bob", "Cob", "Brendan", "Courtney", "Adam", "Gary", "Steve", "Carl", "Jimmy", "Sheen", "Shane",
        "Sam", "Carmelita", "Sly", "Murray", "Bentley", "Steve-o", "Steve Jobs", "Jeff", "Smeff", "Streff", "Kleft", "Smleft", "Streltft",
        "Smrakelpft", "Pete", "John", "Johnny", "Jon", "Johnathan", "Jonathan", "Jonnythan", "Joe", "Mama", "Got 'em", "Alex"};

    public static String[] getNames() {
        return names;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        randomNameTextView = findViewById(R.id.random_name_text_view);
        save_button = findViewById(R.id.save_button);

        findViewById(R.id.random_name_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomNameTextView.setText(RandomNameGenerator.getRandomName());
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavedNameModel savedNameModel;
                if (!randomNameTextView.getText().toString().isEmpty()) {
                    savedNameModel = new SavedNameModel(-1, randomNameTextView.getText().toString());
                    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                    boolean success = databaseHelper.addOne(savedNameModel);
                    if (success) {
                        Toast.makeText(MainActivity.this, savedNameModel.toString() + " was saved", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "There was an error, and nothing was saved.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //do nothing
                }
            }
        });

        findViewById(R.id.nav_to_saved_names).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SavedNamesActivity.class);
                startActivity(intent);
            }
        });
    }

/*

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("RandomNameState", currentRandomName);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentRandomName = savedInstanceState.getString("RandomNameState");
    }

*/

}

/*

    This is how you tag:
    First: private static final String TAG = GreenAdapter.class.getSimpleName();
    Second: Log.d(TAG, "message");

*/
