package com.christopherwmurphy.boringbutbigapp.Util.Task;

import android.content.Context;
import android.os.AsyncTask;

import com.christopherwmurphy.boringbutbigapp.Callbacks.isDefinedCallback;
import com.christopherwmurphy.boringbutbigapp.database.Entity.CurrentWorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.WorkoutDB;

import java.util.List;

public class IsWorkoutDefined extends AsyncTask<Void,Boolean,Boolean> {

    private Context context;
    private isDefinedCallback callback;
    public IsWorkoutDefined(Context context, isDefinedCallback callback){
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        WorkoutDB db = WorkoutDB.getInstance(context);
        List<CurrentWorkoutPlanEntity> plan = db.currentWorkoutPlanDao().getCurrentWorkoutPlan();
        boolean returnVal = false;

        if(!plan.isEmpty()){
            returnVal = true;
        }
        return returnVal;
    }

    @Override
    protected void onPostExecute(Boolean s) {
        super.onPostExecute(s);
        callback.callback(s);
    }
}
