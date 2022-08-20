package com.brendan.randomname;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> existingSavedNames = new ArrayList<>();

    public static ArrayList<String> getExistingSavedNames() {
        return existingSavedNames;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TextView randomNameTextView = findViewById(R.id.randomNameTextView);

        Button saveButton = findViewById(R.id.saveButton);

        findViewById(R.id.randomNameButton).setOnClickListener(view -> {
            RandomNameGenerator randomNameGenerator = new RandomNameGenerator();
            String randomName = randomNameGenerator.getRandomName();
            randomNameTextView.setText(randomName);
        });

        findViewById(R.id.randomNameTextView).setOnLongClickListener(view -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(randomNameTextView.getText() + " copied.", randomNameTextView.getText());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(MainActivity.this, randomNameTextView.getText() + " copied.", Toast.LENGTH_SHORT).show();
            return true;
        });

        saveButton.setOnClickListener(view -> {
            if (!randomNameTextView.getText().toString().isEmpty()) {
                SavedNameModel savedNameModel = new SavedNameModel(-1, randomNameTextView.getText().toString());
                if(existingSavedNames.contains(randomNameTextView.getText().toString())) {
                    String toastText = savedNameModel.getName() + " is already saved.";
                    Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                    boolean success = databaseHelper.addOne(savedNameModel);
                    if (success) {
                        String toastText = savedNameModel.getName() + " was saved.";
                        Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT).show();
                        existingSavedNames.add(savedNameModel.getName());
                    } else {
                        Toast.makeText(MainActivity.this, "There was an error, and nothing was saved.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        findViewById(R.id.navToSavedNames).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SavedNamesActivity.class);
            startActivity(intent);
        });
    }
}