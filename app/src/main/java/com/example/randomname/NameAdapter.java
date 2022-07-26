package com.example.randomname;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
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
        }
    }
}