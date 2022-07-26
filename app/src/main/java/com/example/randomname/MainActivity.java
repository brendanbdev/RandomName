package com.example.randomname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button declarations
        TextView randomNameTextView = findViewById(R.id.randomNameTextView);
        Button save_button = findViewById(R.id.saveButton);

        //Button that calls RandomNameGenerator to generate a random name.
        findViewById(R.id.randomNameButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RandomNameGenerator randomNameGenerator = new RandomNameGenerator();
                String randomName = randomNameGenerator.getRandomName();

                randomNameTextView.setText(randomName);
            }
        });

        //Button to save the name that is currently displaying, to the SQLite database.
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!randomNameTextView.getText().toString().isEmpty()) {
                    SavedNameModel savedNameModel = new SavedNameModel(-1, randomNameTextView.getText().toString());
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

        //Button to navigate to SavedNamesActivity, which is the saved names screen.
        findViewById(R.id.navToSavedNames).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SavedNamesActivity.class);
                startActivity(intent);
            }
        });

        //Ad on main screen.
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView_main_banner = (AdView)findViewById(R.id.adViewMainBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView_main_banner.loadAd(adRequest);
    }
}