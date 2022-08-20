package com.brendan.randomname;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NameAdapter extends RecyclerView.Adapter<NameAdapter.MyViewHolder> {
    Context context;
    List<SavedNameModel> listOfNames;

    public NameAdapter( List<SavedNameModel> listOfNames, Context context) {
        this.context = context;
        this.listOfNames = listOfNames;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.savedNameTextView.setText(listOfNames.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listOfNames.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView savedNameTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            savedNameTextView = itemView.findViewById(R.id.tvSavedName);
            itemView.setOnLongClickListener(v -> {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(savedNameTextView.getText() + " copied.", savedNameTextView.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, savedNameTextView.getText() + " copied.", Toast.LENGTH_SHORT).show();
                return true;
            });
        }
    }
}