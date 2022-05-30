package com.example.randomname;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NameAdapter extends RecyclerView.Adapter<NameAdapter.ViewHolder> {

    private static final String TAG = NameAdapter.class.getSimpleName();

    Context context;

    String[] listOfNames;

    public NameAdapter(Context context, String[] listOfNames) {
        this.context = context;
        this.listOfNames = listOfNames;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.name_list_item, viewGroup, false);

        return new NameAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.savedNameTextView.setText(listOfNames[position]);
    }

    @Override
    public int getItemCount() {
        return listOfNames.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView savedNameTextView;

        public ViewHolder(View view) {
            super(view);
            savedNameTextView = view.findViewById(R.id.tv_saved_name);
        }
    }
}