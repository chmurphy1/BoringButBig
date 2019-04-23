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

import com.christopherwmurphy.boringbutbigapp.Adapters.WorkoutHistoryAdapter;
import com.christopherwmurphy.boringbutbigapp.Callbacks.WorkoutHistoryCallback;
import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.ViewModels.WorkoutHistoryViewModel;
import com.christopherwmurphy.boringbutbigapp.WorkoutHistoryDetailActivity;
import com.christopherwmurphy.boringbutbigapp.analytics.AnalyticsApplication;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutHistoryEntity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.sql.Timestamp;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutHistoryFragment extends Fragment {

    @BindView(R.id.workoutHistoryList)
    RecyclerView workoutHistoryRecyclerView;

    private WorkoutHistoryDetailFragment detail;
    private LinearLayoutManager layoutMgr;
    private int scrollPos;
    private WorkoutHistoryAdapter adapter;
    private Tracker mTracker;
    private WorkoutHistoryCallback callback = new WorkoutHistoryCallback() {
        @Override
        public void callback(Timestamp date) {
            launchNextScreen(date);
        }
    } ;

    public WorkoutHistoryFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View exerciseListView = inflater.inflate(R.layout.workout_history_fragment, container,false);
        ButterKnife.bind(this, exerciseListView);

        return exerciseListView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

        if(savedInstanceState != null ){
            scrollPos = savedInstanceState.getInt(Constants.VISIBLE_ITEM_KEY);

            if(this.getResources().getBoolean(R.bool.isTablet)) {
                detail = (WorkoutHistoryDetailFragment)this.getActivity().getSupportFragmentManager().findFragmentById(R.id.detail);
            }
        }

        mTracker.setScreenName("Workout History Fragment");
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Workout History Fragment")
                .setAction("OnActivityCreated")
                .build());

        layoutMgr = new LinearLayoutManager(getContext());
        workoutHistoryRecyclerView.setLayoutManager(layoutMgr);
        workoutHistoryRecyclerView.setHasFixedSize(true);
        setupObserver();
    }

    private void setupObserver(){
        WorkoutHistoryViewModel evm = ViewModelProviders.of(this).get(WorkoutHistoryViewModel.class);

        evm.getHistory().observe(this, new Observer<List<WorkoutHistoryEntity>>() {
            @Override
            public void onChanged(@Nullable List<WorkoutHistoryEntity> workoutHistoryEntities) {
                adapter = new WorkoutHistoryAdapter(workoutHistoryEntities, callback);
                workoutHistoryRecyclerView.setAdapter(adapter);
                layoutMgr.scrollToPosition(scrollPos);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.VISIBLE_ITEM_KEY, layoutMgr.findFirstCompletelyVisibleItemPosition());
    }

    public void launchNextScreen(Timestamp tm){
        Bundle parms = new Bundle();
        parms.putLong(Constants.DATE, tm.getTime());

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        if(isTablet){
            FrameLayout divider = (FrameLayout) getActivity().findViewById(R.id.divider);
            divider.setVisibility(View.VISIBLE);

            detail = new WorkoutHistoryDetailFragment();

            detail.setArguments(parms);

            this.getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail, detail).commit();
        }
        else{
            Intent intent = new Intent(this.getContext(), WorkoutHistoryDetailActivity.class);
            intent.putExtras(parms);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(this.getResources().getBoolean(R.bool.isTablet)) {
            if(detail != null) {
                this.getActivity().getSupportFragmentManager().beginTransaction().remove(detail).commitAllowingStateLoss();

                FrameLayout divider = (FrameLayout) getActivity().findViewById(R.id.divider);
                divider.setVisibility(View.INVISIBLE);
            }
        }
    }
}
