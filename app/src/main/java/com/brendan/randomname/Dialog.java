package com.brendan.randomname;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.brendan.randomname.R;

public class Dialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.deleteAllDialogTitle)
                .setPositiveButton(getString(R.string.deleteAllPositiveButtonText), (dialog, which) -> ((SavedNamesActivity)getActivity()).yesToDeleteAll())
                .setNegativeButton(getString(R.string.deleteAllNegativeButtonText), (dialog, which) -> {});
        return builder.create();
    }
}
