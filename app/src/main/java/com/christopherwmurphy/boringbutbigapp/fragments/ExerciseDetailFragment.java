package com.christopherwmurphy.boringbutbigapp.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.christopherwmurphy.boringbutbigapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseDetailFragment extends Fragment {

    @BindView(R.id.tada)
    TextView text;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View exerciseDetailView = inflater.inflate(R.layout.exercise_detail_fragment, container,false);
        ButterKnife.bind(this, exerciseDetailView);

        return exerciseDetailView;
    }

}
