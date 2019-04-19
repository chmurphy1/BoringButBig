package com.christopherwmurphy.boringbutbigapp.Util.Task;

import android.content.Context;
import android.os.AsyncTask;

import com.christopherwmurphy.boringbutbigapp.Callbacks.HistoryDetailCallback;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutHistoryEntity;
import com.christopherwmurphy.boringbutbigapp.database.WorkoutDB;

import java.util.List;

public class HistoryDetailsTask extends AsyncTask<Long, Void, Void> {

    private HistoryDetailCallback callback;
    private Context context;
    private List<WorkoutHistoryEntity> results;

    public HistoryDetailsTask(Context context, HistoryDetailCallback callback){
        this.callback = callback;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Long... timestamp) {
        WorkoutDB db = WorkoutDB.getInstance(context);

        results = db.workoutHistoryDao().getAllWorkoutsByTimestamp(timestamp[0].longValue());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        callback.callback(results);
    }
}
