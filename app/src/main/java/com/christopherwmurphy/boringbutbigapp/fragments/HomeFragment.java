package com.christopherwmurphy.boringbutbigapp.fragments;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.christopherwmurphy.boringbutbigapp.Callbacks.HomeCallback;
import com.christopherwmurphy.boringbutbigapp.Callbacks.isDefinedCallback;
import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.Util.Task.GenerateCurrentWorkoutTask;
import com.christopherwmurphy.boringbutbigapp.Util.Task.IsWorkoutDefined;
import com.christopherwmurphy.boringbutbigapp.database.Entity.CurrentWorkoutPlanEntity;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment {

    private View currentWorkoutPlan;
    private List<CurrentWorkoutPlanEntity> todaysPlan;
    private HashMap<Integer, Long> calculatedWeight;
    String regex = "\\d+";
    Pattern pattern;

    @BindView(R.id.workoutTable)
    TableLayout workoutTable;

    @BindView(R.id.complete)
    Button complete;

    private HomeCallback callback = new HomeCallback() {

        @Override
        public void callback(List<CurrentWorkoutPlanEntity> todaysPlan, HashMap<Integer, Long> calculatedWeight) {
            createTable(todaysPlan, calculatedWeight);
        }
    };

    public HomeFragment(){
        super();
        this.pattern = Pattern.compile(regex);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currentWorkoutPlan = inflater.inflate(R.layout.home_fragment_layout, container,false);
        ButterKnife.bind(this, currentWorkoutPlan);

        return currentWorkoutPlan;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        new IsWorkoutDefined(getContext(), new isDefinedCallback() {
            @Override
            public void callback(boolean isDefined) {
                if(!isDefined){
                    createPopup();
                }else{
                    new GenerateCurrentWorkoutTask(getContext(), callback).execute();
                }
            }
        }).execute();

        super.onActivityCreated(savedInstanceState);
    }

    public void createPopup(){
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.home_popup_layout,null);

        Button okay = (Button) customView.findViewById(R.id.okay_button);

        final PopupWindow popup = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.showAtLocation(currentWorkoutPlan , Gravity.CENTER, 0, 0);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
    }

    public void createTable(List<CurrentWorkoutPlanEntity> todaysPlan, HashMap<Integer, Long> calculatedWeight){
        LinearLayout.LayoutParams tableRowParams = new LinearLayout.LayoutParams(
                                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                                            LinearLayout.LayoutParams.MATCH_PARENT);

        //Setup header
        TableRow header = new TableRow(getContext());
        header.setLayoutParams(tableRowParams);
        header.setGravity(Gravity.CENTER);
        header.setBackgroundResource(R.drawable.toolbar_rounded_edges);

        TextView currentWorkout = new TextView(getContext());
        currentWorkout.setText(R.string.workout_table_title);
        currentWorkout.setTextSize(18.0f);

        header.addView(currentWorkout);
        workoutTable.addView(header);

        //This is the body of the table
        for(CurrentWorkoutPlanEntity w : todaysPlan){
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(tableRowParams);
            int cellWidth = workoutTable.getWidth() / 3;

            TextView lift = new TextView(getContext());
            lift.setText(w.getExercise().getName());
            lift.setGravity(Gravity.START);
            lift.setPadding(20,0,0,0);

            TextView reps = new TextView(getContext());
            StringBuilder sb = new StringBuilder();

            if(w.getScheme().getSet().intValue() > 1){
                sb.append(w.getScheme().getSet());
                sb.append(Constants.SPACE);
                sb.append(Constants.X);
                sb.append(Constants.SPACE);
            }

            sb.append(w.getScheme().getReps());

            if(w.getScheme().getPercentage() > 0.0) {
                sb.append(Constants.SPACE);
                sb.append(Constants.AT);
                sb.append(Constants.SPACE);
                sb.append((w.getScheme().getPercentage() * 100));
                sb.append(Constants.PERCENT_SIGN);
            }
            reps.setText(sb.toString());
            reps.setGravity(Gravity.CENTER);
            reps.setPadding(0,0, (cellWidth-160), 0);

            EditText perscribedWeight = new EditText(getContext());
            Long weight = calculatedWeight.get(w.getSeqNum());
            if(weight != null){
                perscribedWeight.setText(weight.toString());
                perscribedWeight.setInputType(InputType.TYPE_CLASS_PHONE);
                perscribedWeight.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
                perscribedWeight.setWidth(160);

                perscribedWeight.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Matcher matcher = pattern.matcher(s.toString());

                        if(!matcher.matches()){
                            Toast.makeText(getContext(),getContext().getResources().getString(R.string.numbers_only),Toast.LENGTH_LONG).show();
                            String newVal = (s.toString()).replaceAll("\\D", "");
                            s.clear();
                            s.append(newVal);
                        }
                    }
                });
            }

            tableRow.addView(lift);
            tableRow.addView(reps);

            if(weight != null) {
                tableRow.addView(perscribedWeight);
            }

            workoutTable.addView(tableRow);
        }
    }

    @OnClick(R.id.complete)
    public void onClick(){

    }
}

