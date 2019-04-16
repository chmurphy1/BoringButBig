package com.christopherwmurphy.boringbutbigapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.christopherwmurphy.boringbutbigapp.Callbacks.HomeCallback;
import com.christopherwmurphy.boringbutbigapp.Callbacks.isDefinedCallback;
import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.Util.Task.GenerateCurrentWorkoutTask;
import com.christopherwmurphy.boringbutbigapp.Util.Task.IsWorkoutDefined;
import com.christopherwmurphy.boringbutbigapp.database.Entity.CurrentWorkoutPlanEntity;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    private View currentWorkoutPlan;

    private HomeCallback callback = new HomeCallback() {
        @Override
        public void callback(List<CurrentWorkoutPlanEntity> todaysPlan, HashMap<Integer, Long> calculatedWeight) {

        }
    };

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
}

