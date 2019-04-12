package com.christopherwmurphy.boringbutbigapp.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.ViewModels.ExerciseStepsViewModel;
import com.christopherwmurphy.boringbutbigapp.ViewModels.Factory.ExerciseViewModelFactory;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseStepsEntity;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseDetailFragment extends Fragment {

    @BindView(R.id.tada)
    TextView exerciseSteps;

    @BindView(R.id.VideoPlayer)
    PlayerView videoPlayerView;

    private Bundle parameters;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View exerciseDetailView = inflater.inflate(R.layout.exercise_detail_fragment, container,false);
        ButterKnife.bind(this, exerciseDetailView);

        return exerciseDetailView;
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

        startExerciseStepsObserver();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(Constants.PARAMETERS, parameters);
    }

    public void startExerciseStepsObserver(){
        ExerciseStepsViewModel esvm = ViewModelProviders.of(this, new ExerciseViewModelFactory(this.getActivity().getApplication(), parameters))
                                                        .get(ExerciseStepsViewModel.class);

        esvm.getSteps().observe(this, new Observer<List<ExerciseStepsEntity>>() {

            @Override
            public void onChanged(@Nullable List<ExerciseStepsEntity> exerciseStepsEntities) {
                StringBuilder sb = new StringBuilder();

                for(ExerciseStepsEntity es : exerciseStepsEntities){
                    sb.append(es.getStepText() + Constants.NEW_LINE);
                }
                exerciseSteps.setText(sb);
            }
        });
    }
}
