package com.christopherwmurphy.boringbutbigapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.christopherwmurphy.boringbutbigapp.Adapters.WorkoutAdapter;
import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.ViewModels.WorkoutViewModel;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutFragment extends Fragment {


    @BindView(R.id.workoutList)
    RecyclerView workoutRecyclerView;

    private LinearLayoutManager layoutMgr;
    private int scrollPos;
    private WorkoutAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View workoutPlanListView = inflater.inflate(R.layout.workout_fragment_layout, container,false);
        ButterKnife.bind(this, workoutPlanListView);

        return workoutPlanListView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null ){
            scrollPos = savedInstanceState.getInt(Constants.VISIBLE_ITEM_KEY);
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
                adapter = new WorkoutAdapter(workouts);
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

}
