package com.christopherwmurphy.boringbutbigapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.christopherwmurphy.boringbutbigapp.Adapters.WorkoutAdapter;
import com.christopherwmurphy.boringbutbigapp.Callbacks.CallbackFromWorkout;
import com.christopherwmurphy.boringbutbigapp.Callbacks.CallbackFromWorkoutPlan;
import com.christopherwmurphy.boringbutbigapp.Callbacks.WorkoutCallback;
import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.ViewModels.WorkoutViewModel;
import com.christopherwmurphy.boringbutbigapp.WorkoutPlanDetailActivity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutFragment extends Fragment {


    @BindView(R.id.workoutList)
    RecyclerView workoutRecyclerView;

    private LinearLayoutManager layoutMgr;
    private int scrollPos;
    private WorkoutAdapter adapter;
    private WorkoutPlanFragment detail;
    private CallbackFromWorkout wCallback;

    private WorkoutCallback callback = new WorkoutCallback() {
        @Override
        public void callback(int workoutId, String[] lifts) {
            launchNextScreen(workoutId, lifts);
        }
    };

    private CallbackFromWorkoutPlan wpCallback = new CallbackFromWorkoutPlan() {
        @Override
        public void callback() {
            getActivity().getSupportFragmentManager().beginTransaction().remove(detail).commit();
            wCallback.callback();
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View workoutPlanListView = inflater.inflate(R.layout.workout_fragment_layout, container,false);
        ButterKnife.bind(this, workoutPlanListView);

        return workoutPlanListView;
    }

    public void setwCallback(CallbackFromWorkout wCallback) {
        this.wCallback = wCallback;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null ){
            scrollPos = savedInstanceState.getInt(Constants.VISIBLE_ITEM_KEY);

            if(this.getResources().getBoolean(R.bool.isTablet)) {
                detail = (WorkoutPlanFragment)this.getActivity().getSupportFragmentManager().findFragmentById(R.id.detail);
            }
        }

        layoutMgr = new LinearLayoutManager(getContext());
        workoutRecyclerView.setLayoutManager(layoutMgr);
        workoutRecyclerView.setHasFixedSize(true);
        setupObserver();
    }

    private void setupObserver(){
        WorkoutViewModel wm = ViewModelProviders.of(this).get(WorkoutViewModel.class);

        wm.getWorkouts().observe(this, new Observer<List<WorkoutEntity>>() {
            @Override
            public void onChanged(@Nullable List<WorkoutEntity> workouts) {
                adapter = new WorkoutAdapter(workouts, callback);
                workoutRecyclerView.setAdapter(adapter);
                layoutMgr.scrollToPosition(scrollPos);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.VISIBLE_ITEM_KEY, layoutMgr.findFirstCompletelyVisibleItemPosition());
    }

    public void launchNextScreen(int workoutId, String[] lifts){
        Bundle parms = new Bundle();
        parms.putInt(Constants.WORKOUT_ID, workoutId);

        ArrayList<Integer> list = new ArrayList<>();
        if(lifts != null){
            for(String s : lifts){
                try {
                    Integer exerciseId = Integer.parseInt(s);
                    list.add(exerciseId);
                }
                catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }
        parms.putIntegerArrayList("LIST_ID", list);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        if(isTablet){
            FrameLayout divider = (FrameLayout) getActivity().findViewById(R.id.divider);
            divider.setVisibility(View.VISIBLE);

            detail = new WorkoutPlanFragment();

            detail.setArguments(parms);
            detail.setWpCallback(wpCallback);

            this.getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail, detail).commit();
        }
        else{
            Intent intent = new Intent(this.getContext(),WorkoutPlanDetailActivity.class);
            intent.putExtras(parms);
            startActivity(intent);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();

        if(this.getResources().getBoolean(R.bool.isTablet)) {
            if (detail != null){
                this.getActivity().getSupportFragmentManager().beginTransaction().remove(detail).commitAllowingStateLoss();

                FrameLayout divider = (FrameLayout) getActivity().findViewById(R.id.divider);
                divider.setVisibility(View.INVISIBLE);
            }
        }
    }
}
