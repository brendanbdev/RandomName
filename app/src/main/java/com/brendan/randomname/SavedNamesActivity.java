package com.brendan.randomname;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SavedNamesActivity extends AppCompatActivity {
    private static DatabaseHelper databaseHelper;
    private static List<SavedNameModel> savedNames;
    ArrayList<String> existingSavedNames = MainActivity.getExistingSavedNames();
    private NameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_names);

        databaseHelper = new DatabaseHelper(SavedNamesActivity.this);
        savedNames = databaseHelper.getAllNames();

        // A Recycler View is a more efficient version of a list view.
        RecyclerView rv_saved_names = findViewById(R.id.rvSavedNames);
        rv_saved_names.setHasFixedSize(false);
        rv_saved_names.setLayoutManager(new LinearLayoutManager(this));
        /* This will adapt objects to become items on the Recycler View List.
          It also uses an ID that is a attribute of each object, to make sure each item is in order. */
        adapter = new NameAdapter(savedNames, this);
        rv_saved_names.setAdapter(adapter);

        /* This is an animation for swiping individual items off of the Recycler View list
        and consequentially deleting the respective data from the SQLite database. */
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
                existingSavedNames.remove(nameToDelete.getName());
            }
        }).attachToRecyclerView(rv_saved_names);

        ImageView iv_to_delete_all = findViewById(R.id.ivToDeleteAll);

        // This dialog will ask the user if they want to delete all of the saved random names.
        iv_to_delete_all.setOnClickListener(view -> {
            if(!savedNames.isEmpty()) {
                openDialog();
            }
        });

        // The SavedNameActivity finishes, and then the MainActivity will display again in the exact state that it was left.
        findViewById(R.id.ivNavToMainActivity).setOnClickListener(view -> finish());
    }

    // Dialog for asking if the user wants to delete all of their saved names.
    public void openDialog(){
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(), "Dialog");
    }

    // This method is called in the Dialog class.
    public void yesToDeleteAll(){
        databaseHelper.deleteAll();
        savedNames.removeAll(savedNames);
        adapter.notifyDataSetChanged();
        existingSavedNames.clear();
    }
}
