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

import com.christopherwmurphy.boringbutbigapp.Adapters.ExerciseAdapater;
import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.ViewModels.ExerciseViewModel;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseFragment extends Fragment {

    @BindView(R.id.exerciseList)
    RecyclerView exerciseRecyclerView;

    private LinearLayoutManager layoutMgr;
    private int scrollPos;
    private ExerciseAdapater adapter;

    public ExerciseFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View exerciseListView = inflater.inflate(R.layout.exercise_fragment_layout, container,false);
        ButterKnife.bind(this, exerciseListView);

        return exerciseListView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null ){
            scrollPos = savedInstanceState.getInt(Constants.VISIBLE_ITEM_KEY);
        }

        layoutMgr = new LinearLayoutManager(getContext());
        exerciseRecyclerView.setLayoutManager(layoutMgr);
        exerciseRecyclerView.setHasFixedSize(true);
        setupObserver();
    }

    private void setupObserver(){
        ExerciseViewModel evm = ViewModelProviders.of(this).get(ExerciseViewModel.class);

        evm.getExercises().observe(this, new Observer<List<ExerciseEntity>>() {
            @Override
            public void onChanged(@Nullable List<ExerciseEntity> exerciseEntities) {
                adapter = new ExerciseAdapater(exerciseEntities);
                exerciseRecyclerView.setAdapter(adapter);
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