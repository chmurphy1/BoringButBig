package com.christopherwmurphy.boringbutbigapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.christopherwmurphy.boringbutbigapp.Callbacks.HistoryDetailCallback;
import com.christopherwmurphy.boringbutbigapp.Callbacks.WorkoutHistoryCallback;
import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.Util.Task.HistoryDetailsTask;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutHistoryEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutHistoryDetailFragment extends Fragment {

    private Bundle parameters;

    @BindView(R.id.workoutHistoryDetailTable)
    TableLayout detailHistory;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View exerciseListView = inflater.inflate(R.layout.workout_history_detail_fragment, container,false);
        ButterKnife.bind(this, exerciseListView);

        return exerciseListView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null){
            parameters = savedInstanceState.getBundle(Constants.PARAMETERS);
        }
        else{
            parameters = this.getArguments();
        }
        new HistoryDetailsTask(getContext(), new HistoryDetailCallback() {
            @Override
            public void callback(List<WorkoutHistoryEntity> results) {
                createTable(results);
            }
        }).execute(parameters.getLong(Constants.DATE));
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(Constants.PARAMETERS, parameters);
    }
    public void createTable(List<WorkoutHistoryEntity> results){
        LinearLayout.LayoutParams tableRowParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        //Setup header
        TableRow header = new TableRow(getContext());
        header.setLayoutParams(tableRowParams);
        header.setGravity(Gravity.CENTER);
        header.setBackgroundResource(R.drawable.toolbar_rounded_edges);

        TextView currentWorkout = new TextView(getContext());
        currentWorkout.setText(new Timestamp( parameters.getLong(Constants.DATE)).toString());
        currentWorkout.setTextSize(18.0f);

        header.addView(currentWorkout);
        detailHistory.addView(header);

        //This is the body of the table
        for(WorkoutHistoryEntity w : results) {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(tableRowParams);
            int cellWidth = detailHistory.getWidth() / 3;

            TextView lift = new TextView(getContext());
            lift.setText(w.getExercise().getName());
            lift.setGravity(Gravity.START);
            lift.setPadding(20, 0, 0, 0);

            TextView reps = new TextView(getContext());
            StringBuilder sb = new StringBuilder();

            if (w.getScheme().getSet().intValue() > 1) {
                sb.append(w.getScheme().getSet());
                sb.append(Constants.SPACE);
                sb.append(Constants.X);
                sb.append(Constants.SPACE);
            }

            sb.append(w.getScheme().getReps());

            if (w.getScheme().getPercentage() > 0.0) {
                sb.append(Constants.SPACE);
                sb.append(Constants.AT);
                sb.append(Constants.SPACE);
                sb.append((w.getScheme().getPercentage() * 100));
                sb.append(Constants.PERCENT_SIGN);
            }
            reps.setText(sb.toString());
            reps.setGravity(Gravity.CENTER);

            TextView weight = new TextView(getContext());

            tableRow.addView(lift);
            tableRow.addView(reps);

            if(w.getWeight() != null) {
                weight.setText(w.getWeight().toString());
                tableRow.addView(weight);
            }

            detailHistory.addView(tableRow);
        }
    }
}
