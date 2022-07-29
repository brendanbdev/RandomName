package com.example.randomname;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class SavedNamesActivity extends AppCompatActivity {

    private static DatabaseHelper databaseHelper;
    private static List<SavedNameModel> savedNames;
    //The adapter is for the Recycler View.
    private static NameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_names);

        //For the SQLite Database.
        databaseHelper = new DatabaseHelper(SavedNamesActivity.this);
        savedNames = databaseHelper.getAllNames();

        //For the Recycler View.
        RecyclerView rv_saved_names = findViewById(R.id.rvSavedNames);
        rv_saved_names.setHasFixedSize(false);
        rv_saved_names.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NameAdapter(savedNames, this);
        rv_saved_names.setAdapter(adapter);

        //This is an animation for swiping individual names off of the list and consequentially deleting them from the SQLite database.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int positionOfItemToDelete = viewHolder.getBindingAdapterPosition();
                SavedNameModel nameToDelete = savedNames.get(positionOfItemToDelete);
                databaseHelper.delete(nameToDelete);
                savedNames.remove(positionOfItemToDelete);
                adapter.notifyItemRemoved(positionOfItemToDelete);
            }
        }).attachToRecyclerView(rv_saved_names);

        //Button that prompts a dialog to appear, which asks if the user would like to delete all of their saved names from the SQLite database.
        ImageView iv_to_delete_all = findViewById(R.id.ivToDeleteAll);
        iv_to_delete_all.setOnClickListener(view -> openDialog());

        //Button to navigate to SavedNamesActivity, which is the saved names screen.
        findViewById(R.id.ivNavToMainActivity).setOnClickListener(view -> finish());

        //Ad on saved names screen.
        MobileAds.initialize(this, initializationStatus -> {
        });
        AdView adView_main_banner = findViewById(R.id.adViewSavedNamesBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView_main_banner.loadAd(adRequest);
    }

    //Dialog for asking if the user wants to delete all of their saved names.
    public void openDialog(){
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(), "Dialog");
    }

    //Method called in the Dialog class
    public static void yesToDeleteAll(){
        databaseHelper.deleteAll();
        savedNames.removeAll(savedNames);
        adapter.notifyDataSetChanged();
    }
}
