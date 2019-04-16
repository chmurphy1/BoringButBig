package com.christopherwmurphy.boringbutbigapp.Util.Task;

import android.content.Context;
import android.os.AsyncTask;

import com.christopherwmurphy.boringbutbigapp.Callbacks.ExerciseMaxTaskDelegate;
import com.christopherwmurphy.boringbutbigapp.Util.Task.TaskResults.ExerciseMaxResults;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseMaxEntity;
import com.christopherwmurphy.boringbutbigapp.database.WorkoutDB;

import java.util.ArrayList;
import java.util.List;

public class ExerciseMaxTask extends AsyncTask<Integer, Void, ExerciseMaxResults> {

    private Context context;
    private List<ExerciseMaxEntity> exerciseMaxes = new ArrayList<>();
    private List<ExerciseEntity> exercises = new ArrayList<>();
    private ExerciseMaxTaskDelegate delegate;

    public ExerciseMaxTask(Context context, ExerciseMaxTaskDelegate delegate) {
        this.context = context;
        this.delegate = delegate;
    }

    @Override
    protected ExerciseMaxResults doInBackground(Integer... keys) {
        WorkoutDB db = WorkoutDB.getInstance(context);

        List<Integer> listOfKeys = new ArrayList<>();
        for(Integer key : keys){
            listOfKeys.add(key);
        }

        List<ExerciseMaxEntity> maxes = db.exerciseMaxDao().getExerciseMaxById(listOfKeys);

        for(ExerciseMaxEntity max : maxes){
            if(listOfKeys.isEmpty()){
                break;
            }
            Integer id = max.getMaxId();
            if(listOfKeys.contains(id)){
                listOfKeys.remove(id);
                exerciseMaxes.add(max);
            }
        }

        if(!listOfKeys.isEmpty()){
            exercises = db.exerciseDao().getListOfExercisesInList(listOfKeys);
        }

        return new ExerciseMaxResults(exerciseMaxes, exercises);
    }

    @Override
    protected void onPostExecute(ExerciseMaxResults exerciseMaxResults) {
        delegate.callback(exerciseMaxResults);
    }
}
