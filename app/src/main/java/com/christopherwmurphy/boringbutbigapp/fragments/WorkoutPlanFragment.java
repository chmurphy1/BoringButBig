package com.christopherwmurphy.boringbutbigapp.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.christopherwmurphy.boringbutbigapp.Adapters.WorkoutPlanAdapter;
import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.ViewModels.Factory.CustomViewModelFactory;
import com.christopherwmurphy.boringbutbigapp.ViewModels.WorkoutPlanViewModel;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutPlanEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutPlanFragment extends Fragment {

    private Bundle parameters;
    private WorkoutPlanAdapter adapter;

    @BindView(R.id.workoutPlanList)
    ExpandableListView workoutPlanDetail;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View workoutPlanView = inflater.inflate(R.layout.workout_plan_detail_fragment, container,false);
        ButterKnife.bind(this, workoutPlanView);

        return workoutPlanView;
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
        setupExpandableListView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(Constants.PARAMETERS, parameters);
    }

    public void  setupExpandableListView(){
        WorkoutPlanViewModel wpvm = ViewModelProviders.of(this, new CustomViewModelFactory(this.getActivity().getApplication(), parameters))
                .get(WorkoutPlanViewModel.class);

        wpvm.getPlans().observe(this, new Observer<List<WorkoutPlanEntity>>() {
            @Override
            public void onChanged(@Nullable List<WorkoutPlanEntity> workoutPlanEntities) {

                int week = 1;
                int index = 0;
                int planId = 1;
                ArrayList<String> header = new ArrayList<>();
                HashMap<Integer, List<String>> rows = new HashMap<>();
                ArrayList<String> list = new ArrayList<>();

                for(WorkoutPlanEntity entity : workoutPlanEntities){
                    String row = "";

                    if(week != entity.getWeek()){
                        header.add(getContext().getResources().getString(R.string.week)+ Constants.SPACE + week);

                        if(!list.isEmpty()) {
                            rows.put(index++, list);
                            list = new ArrayList<>();
                        }
                        week = entity.getWeek();
                        planId = entity.getPlanId();
                    }

                    if(planId != entity.getPlanId()){
                        planId = entity.getPlanId();
                        list.add(Constants.NEW_LINE);
                    }

                    if(entity.getOptional()){
                        row = getContext().getResources().getString(R.string.optional);
                    }

                    if(entity.getScheme().getPercentage() == 0.0){
                        row += Constants.SPACE + entity.getExercise().getName() + Constants.SPACE +
                                entity.getScheme().getSet() + Constants.SPACE +
                                Constants.X + Constants.SPACE +
                                entity.getScheme().getReps();
                    }
                    else {
                        row += entity.getExercise().getName() + Constants.SPACE +
                                entity.getScheme().getSet() + Constants.SPACE +
                                Constants.X + Constants.SPACE +
                                entity.getScheme().getReps() + Constants.SPACE +
                                Constants.AT + Constants.SPACE +
                                (entity.getScheme().getPercentage() * 100) + Constants.PERCENT_SIGN;
                    }

                    list.add(row);
                }
                header.add(getContext().getResources().getString(R.string.week)+ Constants.SPACE + week);
                rows.put(index, list);

                adapter = new WorkoutPlanAdapter(header, rows, getContext());
                workoutPlanDetail.setAdapter(adapter);
            }
        });
    }
}
