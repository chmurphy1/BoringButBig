package com.christopherwmurphy.boringbutbigapp.fragments;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.christopherwmurphy.boringbutbigapp.Callbacks.CompleteCallback;
import com.christopherwmurphy.boringbutbigapp.Callbacks.HomeCallback;
import com.christopherwmurphy.boringbutbigapp.Callbacks.isDefinedCallback;
import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.Util.Task.GenerateCurrentWorkoutTask;
import com.christopherwmurphy.boringbutbigapp.Util.Task.IsWorkoutDefined;
import com.christopherwmurphy.boringbutbigapp.Util.Task.OnWorkoutCompleteTask;
import com.christopherwmurphy.boringbutbigapp.WorkoutProvider;
import com.christopherwmurphy.boringbutbigapp.analytics.AnalyticsApplication;
import com.christopherwmurphy.boringbutbigapp.database.Entity.CurrentWorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.widget.ParcelableData;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class HomeFragment extends Fragment {

    private View currentWorkoutPlan;
    private List<CurrentWorkoutPlanEntity> workout;
    private HashMap<Integer, Long> cWeight;
    String regex = "\\d+";
    Pattern pattern;
    private HashMap<Integer, Long> calWeight;
    private Tracker mTracker;

    @BindView(R.id.workoutTable)
    TableLayout workoutTable;

    @BindView(R.id.complete)
    Button complete;

    @BindView(R.id.optional)
    TextView optional;

    @BindView(R.id.message)
    TextView message;

    @BindView(R.id.homeAd)
    AdView homeAd;

    private int instantiated;

    private HomeCallback callback = new HomeCallback() {

        @Override
        public void callback(List<CurrentWorkoutPlanEntity> todaysPlan, HashMap<Integer, Long> calculatedWeight) {
            workout = todaysPlan;
            calWeight = calculatedWeight;
            createTable(todaysPlan, calculatedWeight);
            sendDataToWidget();
        }
    };

    public interface HomeFragmentCallback{
        public void callback();
    };
    private HomeFragmentCallback homeCallback;

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
        optional.setText("* = "+getContext().getResources().getString(R.string.opt));
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        homeAd.loadAd(adRequest);

        if(savedInstanceState != null){
            instantiated = savedInstanceState.getInt(Constants.INSTANTIATED);
        }

        if(instantiated == 0) {
            AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
            mTracker = application.getDefaultTracker();

            new IsWorkoutDefined(getContext(), new isDefinedCallback() {
                @Override
                public void callback(boolean isDefined) {
                    if (!isDefined) {
                        message.setVisibility(View.VISIBLE);
                        complete.setVisibility(View.GONE);

                        //Tell it to show a message saying something about no
                        //workouts being selected
                        Intent intent = new Intent(getContext(), WorkoutProvider.class);
                        Bundle intentBundle = new Bundle();

                        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                        intentBundle.putBoolean(Constants.SHOW_MESSAGE, true);
                        intent.putExtra(Constants.WIDGET_BUNDLE, intentBundle);

                        int[] ids = AppWidgetManager.getInstance(getContext()).getAppWidgetIds(new ComponentName(getContext(), WorkoutProvider.class));
                        if(ids != null && ids.length > 0) {
                            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                            getContext().sendBroadcast(intent);
                        }
                    } else {
                        new GenerateCurrentWorkoutTask(getContext(), callback).execute();
                        complete.setVisibility(View.VISIBLE);
                        message.setVisibility(View.INVISIBLE);
                    }
                }
            }).execute();

            mTracker.setScreenName("Home Fragment");
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Home Fragment")
                    .setAction("OnActivityCreated")
                    .build());

        }
    }

    public void createTable(List<CurrentWorkoutPlanEntity> todaysPlan, HashMap<Integer, Long> calculatedWeight){
        TableRow headerRow = (TableRow) getActivity().getLayoutInflater().inflate(R.layout.tablerow_home_layout_header, null);
        boolean opt = false;

        //Setup header
        TextView currentWorkout = (TextView) headerRow.getChildAt(0);
        currentWorkout.setText(R.string.workout_table_title);

        workoutTable.addView(headerRow);

        //This is the body of the table
        for(CurrentWorkoutPlanEntity w : todaysPlan){
            TableRow tableRow = (TableRow) getActivity().getLayoutInflater().inflate(R.layout.tablerow_home_layout_body, null);

            TextView lift = (TextView) tableRow.getChildAt(0);
            TextView reps = (TextView) tableRow.getChildAt(1);
            EditText perscribedWeight = (EditText) tableRow.getChildAt(2);
            TextView units =  (TextView) tableRow.getChildAt(3);

            if(w.getOptional()){
                lift.setText(w.getExercise().getName()+"*");
                opt = true;
            }
            else {
                lift.setText(w.getExercise().getName());
            }

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
            else{
                units.setVisibility(View.INVISIBLE);
            }
            reps.setText(sb.toString());

            Long weight = calculatedWeight.get(w.getSeqNum());
            if(weight != null){
                perscribedWeight.setText(weight.toString());

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
                        sendDataToWdigetFromTable();
                    }
                });
            }
            else{
                perscribedWeight.setVisibility(View.INVISIBLE);
            }

            workoutTable.addView(tableRow);
        }
        if(opt){
            optional.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.complete)
    public void onClick(){
        new OnWorkoutCompleteTask(workout, workoutTable, getContext(), new CompleteCallback() {
            @Override
            public void completeCallback() {
                homeCallback.callback();
            }
        }).execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeCallback = (HomeFragmentCallback) getActivity();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.INSTANTIATED, ++instantiated);
    }

    private void sendDataToWdigetFromTable(){
        Intent intent = new Intent(getContext(), WorkoutProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        List<ParcelableData> dataList = new ArrayList<>();
        Bundle intentBundle = new Bundle();

        int size = workoutTable.getChildCount();
        for(int r = 1; r < size; r++){
            Object oRow = workoutTable.getChildAt(r);

            if(oRow instanceof TableRow){
                TableRow row = (TableRow) oRow;
                int rowSize = row.getChildCount();
                ParcelableData data = new ParcelableData();

                for(int c = 0; c < rowSize; c++){
                    Object cell = row.getChildAt(c);

                    if(cell instanceof EditText){
                        EditText weight = (EditText) cell;
                        if(weight.getText().toString() != null && !Constants.EMPTY.equals(weight.getText().toString())) {
                            data.setWeight(weight.getText().toString());
                        }
                    }
                    else if(cell instanceof TextView){
                        TextView tmp = (TextView) cell;

                        if(c == 0){
                            data.setLift(tmp.getText().toString());
                        }else if(c == 1){
                            data.setReps(tmp.getText().toString());
                        }
                    }
                }
                dataList.add(data);
            }
        }

        intentBundle.putParcelable(Constants.DATA, Parcels.wrap(dataList));
        intent.putExtra(Constants.WIDGET_BUNDLE, intentBundle);

        int[] ids = AppWidgetManager.getInstance(getContext()).getAppWidgetIds(new ComponentName(getContext(), WorkoutProvider.class));
        if(ids != null && ids.length > 0) {
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            getContext().sendBroadcast(intent);
        }
    }

    private void sendDataToWidget(){
        Intent intent = new Intent(getContext(), WorkoutProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        Bundle intentBundle = new Bundle();

        List<ParcelableData> dataList = new ArrayList<>();
        for(CurrentWorkoutPlanEntity c : workout){
            ParcelableData data = new ParcelableData();
            Long weight = calWeight.get(c.getSeqNum());

            if(c.getOptional()){
                data.setLift(c.getExercise().getName()+"*");
            }
            else {
                data.setLift(c.getExercise().getName());
            }

            StringBuilder sb = new StringBuilder();

            if(c.getScheme().getSet().intValue() > 1){
                sb.append(c.getScheme().getSet());
                sb.append(Constants.SPACE);
                sb.append(Constants.X);
                sb.append(Constants.SPACE);
            }

            sb.append(c.getScheme().getReps());

            if(c.getScheme().getPercentage() > 0.0) {
                sb.append(Constants.SPACE);
                sb.append(Constants.AT);
                sb.append(Constants.SPACE);
                sb.append((c.getScheme().getPercentage() * 100));
                sb.append(Constants.PERCENT_SIGN);
            }

            data.setReps(sb.toString());

            data.setLift(c.getExercise().getName());
            if(weight != null) {
                data.setWeight(weight.toString());
            }
            dataList.add(data);
        }

        intentBundle.putParcelable(Constants.DATA, Parcels.wrap(dataList));
        intent.putExtra(Constants.WIDGET_BUNDLE, intentBundle);

        int[] ids = AppWidgetManager.getInstance(getContext()).getAppWidgetIds(new ComponentName(getContext(), WorkoutProvider.class));
        if(ids != null && ids.length > 0) {
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            getContext().sendBroadcast(intent);
        }
    }
}

