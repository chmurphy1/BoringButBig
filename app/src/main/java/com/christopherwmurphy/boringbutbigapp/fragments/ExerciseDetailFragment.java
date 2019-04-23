package com.christopherwmurphy.boringbutbigapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.christopherwmurphy.boringbutbigapp.BuildConfig;
import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.ViewModels.ExerciseStepsViewModel;
import com.christopherwmurphy.boringbutbigapp.ViewModels.ExerciseVideoViewModel;
import com.christopherwmurphy.boringbutbigapp.ViewModels.Factory.CustomViewModelFactory;
import com.christopherwmurphy.boringbutbigapp.analytics.AnalyticsApplication;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseStepsEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseVideosEntity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseDetailFragment extends Fragment{

    @BindView(R.id.tada)
    TextView exerciseSteps;

    private Bundle parameters;
    YouTubePlayerSupportFragment youTubePlayerFragment;

    private Tracker mTracker;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View exerciseDetailView = inflater.inflate(R.layout.exercise_detail_fragment, container,false);

        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        this.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.videoPlayer,youTubePlayerFragment).commit();

        ButterKnife.bind(this, exerciseDetailView);

        return exerciseDetailView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

        if(savedInstanceState != null){
            parameters = savedInstanceState.getBundle(Constants.PARAMETERS);
        }
        else{
            parameters = this.getArguments();
        }

        mTracker.setScreenName("Exercise Detail Fragment");
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Exercise Detail Fragment")
                .setAction("OnActivityCreated")
                .build());

        startExerciseStepsObserver();
        startExerciseVideoObserver();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(Constants.PARAMETERS, parameters);
    }

    public void startExerciseStepsObserver(){
        ExerciseStepsViewModel esvm = ViewModelProviders.of(this, new CustomViewModelFactory(this.getActivity().getApplication(), parameters))
                                                        .get(ExerciseStepsViewModel.class);
        esvm.getSteps().observe(this, new Observer<List<ExerciseStepsEntity>>() {

            @Override
            public void onChanged(@Nullable List<ExerciseStepsEntity> exerciseStepsEntities) {
                StringBuilder sb = new StringBuilder();
                int line_counter = 1;
                for(ExerciseStepsEntity es : exerciseStepsEntities){
                    sb.append(line_counter++ + Constants.PERIOD + Constants.SPACE + es.getStepText() + Constants.NEW_LINE);
                }
                exerciseSteps.setText(sb);
            }
        });
    }

    public void startExerciseVideoObserver(){
        ExerciseVideoViewModel evvm = ViewModelProviders.of(this, new CustomViewModelFactory(this.getActivity().getApplication(), parameters))
                                                        .get(ExerciseVideoViewModel.class);

        evvm.getVideo().observe(this, new Observer<ExerciseVideosEntity>() {
            @Override
            public void onChanged(@Nullable ExerciseVideosEntity exerciseVideosEntity) {
               final String url = exerciseVideosEntity.getVideo_url();

                youTubePlayerFragment.initialize(BuildConfig.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        if (!b) {
                            youTubePlayer.setFullscreen(false);
                            youTubePlayer.cueVideo(url);
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        youTubePlayerFragment.onDestroy();
    }
}
