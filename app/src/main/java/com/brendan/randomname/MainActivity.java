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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                boolean success = databaseHelper.addOne(savedNameModel);
                if (success) {
                    String toastText = savedNameModel.getName() + " was saved";
                    Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "There was an error, and nothing was saved.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.navToSavedNames).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SavedNamesActivity.class);
            startActivity(intent);
        });

        MobileAds.initialize(this, initializationStatus -> {
        });

        AdView adView_main_banner = findViewById(R.id.adViewMainBanner);

        AdRequest adRequest = new AdRequest.Builder().build();

        adView_main_banner.loadAd(adRequest);
    }
}