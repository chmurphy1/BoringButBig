package com.christopherwmurphy.boringbutbigapp.ViewModels.Factory;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.ViewModels.ExerciseStepsViewModel;

public class ExerciseViewModelFactory implements ViewModelProvider.Factory{

    private Application mApplication;
    private Bundle parameters;

    public ExerciseViewModelFactory(Application application, Bundle parameters) {
        mApplication = application;
        this.parameters = parameters;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == ExerciseStepsViewModel.class){
            return (T) new ExerciseStepsViewModel(mApplication, parameters.getInt(Constants.EXERCISE_ID));
        }
        else{
            return null;
        }
    }
}
