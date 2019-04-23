package com.christopherwmurphy.boringbutbigapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.christopherwmurphy.boringbutbigapp.R;

public class WorkoutArrayAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] objects;

    public WorkoutArrayAdapter(@NonNull Context context, @NonNull String[] objects) {
        super(context, -1, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.widget_row_layout, parent, false);

        TextView lift = (TextView) row.findViewById(R.id.widget_lift);
        TextView reps = (TextView) row.findViewById(R.id.widget_reps);
        TextView weight = (TextView) row.findViewById(R.id.widget_weight);

        lift.setText(objects[0]);
        reps.setText(objects[1]);

        if(2 < objects.length){
            weight.setText(objects[2]);
        }

        return row;
    }
}
