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
import android.widget.FrameLayout;
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

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutHistoryDetailFragment extends Fragment {

    private Bundle parameters;

    @BindView(R.id.workoutHistoryDetailTable)
    TableLayout detailHistory;

    @BindView(R.id.optional)
    TextView optional;

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

        optional.setText("* = "+getContext().getResources().getString(R.string.opt));
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(Constants.PARAMETERS, parameters);
    }
    public void createTable(List<WorkoutHistoryEntity> results){

        //Setup header
        TableRow headerRow = (TableRow) getActivity().getLayoutInflater().inflate(R.layout.tablerow_workout_history_header, null);
        TextView hRow = (TextView) headerRow.getChildAt(0);
        hRow.setText(new Timestamp( parameters.getLong(Constants.DATE)).toString());
        detailHistory.addView(headerRow);

        boolean opt = false;

        //This is the body of the table
        for(WorkoutHistoryEntity w : results) {

            TableRow row = (TableRow) getActivity().getLayoutInflater().inflate(R.layout.tablerow_workout_history, null);

            TextView lift = (TextView) row.getChildAt(0);
            TextView reps = (TextView) row.getChildAt(1);
            TextView weight = (TextView) row.getChildAt(2);

            if(w.getOptional()) {
                lift.setText(w.getExercise().getName()+"*");
                opt = true;
            }
            else{
                lift.setText(w.getExercise().getName());
            }
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

            if(w.getWeight() != null) {
                weight.setText(w.getWeight().toString());
            }

            detailHistory.addView(row);
        }

        if(opt){
            optional.setVisibility(View.VISIBLE);
        }
    }
}
