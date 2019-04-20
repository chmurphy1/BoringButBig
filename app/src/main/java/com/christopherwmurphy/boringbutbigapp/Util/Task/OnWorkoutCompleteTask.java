package com.christopherwmurphy.boringbutbigapp.Util.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.christopherwmurphy.boringbutbigapp.Callbacks.CompleteCallback;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.Workout;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.database.Entity.CurrentWorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseMaxEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.TodaysWorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutHistoryEntity;
import com.christopherwmurphy.boringbutbigapp.database.WorkoutDB;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OnWorkoutCompleteTask extends AsyncTask <Void, Void, Void>{

    private List<CurrentWorkoutPlanEntity> workout;
    private TableLayout workoutTable;
    private Context context;
    private CompleteCallback completed;

    public OnWorkoutCompleteTask(List<CurrentWorkoutPlanEntity> workout, TableLayout workoutTable,
                                 Context context, CompleteCallback completed) {
        this.workout = workout;
        this.workoutTable = workoutTable;
        this.context = context;
        this.completed = completed;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        boolean endOfPlan = false;
        Timestamp wComplete = new Timestamp(System.currentTimeMillis());
        WorkoutDB db = WorkoutDB.getInstance(context);
        ArrayList<Integer> list = new ArrayList<>();
        Integer maxWeek = db.currentWorkoutPlanDao().getMaxWeek();
        Integer maxPlanId = db.currentWorkoutPlanDao().getMaxPlanIdByWeek(workout.get(0).getWeek());

        //delete the rows for the current workout
        db.currentWorkoutPlanDao().deleteFromTableByWeekAndPlanId(workout.get(0).getWeek(), workout.get(0).getPlanId());

        TodaysWorkoutPlanEntity nextWorkoutKey = db.todaysWorkoutPlanDao().getTodaysWorkout();

        //up the training max after the last plan of the period
        WorkoutEntity wp = db.workoutDao().getWorkouById(workout.get(0).getWorkoutId());
        if((nextWorkoutKey.getWeek().intValue() % wp.getPeriod().intValue() == 0) &&
                (nextWorkoutKey.getPlanId().intValue() == maxPlanId.intValue())){

            String[] increase = wp.getIncrements().split(Constants.COMMA);
            String[] lifts = wp.getLifts().split(Constants.COMMA);

            Set<Integer> liftIds = new HashSet<>();
            ArrayList<Integer> wIncrease = new ArrayList<>();

            for(int i = 0; i < increase.length; i++){
                wIncrease.add(new Integer(Integer.parseInt(increase[i])));
                liftIds.add(new Integer(Integer.parseInt(lifts[i])));
            }

            List<ExerciseMaxEntity> maxes = db.exerciseMaxDao().getExerciseMaxByIdLimitSize(liftIds);

            //because the maxes for the lifts can be in any order
            //I have to make sure to match the increment to the lift

            HashMap<Integer, Integer> idForLifts = new HashMap<>();
            for(Integer j : liftIds){
                idForLifts.put(j,j);
            }

            for(int k = 0; k < maxes.size(); k++){
                ExerciseMaxEntity tmp = maxes.get(k);
                if(tmp.getExercise().getId().intValue() == idForLifts.get(new Integer(tmp.getExercise().getId().intValue())).intValue()){
                    tmp.setMax( new Integer(tmp.getMax().intValue() + wIncrease.get(k).intValue()));
                    tmp.setDate(wComplete);
                }
            }
            db.exerciseMaxDao().insertAll(maxes);
        }

        if(nextWorkoutKey.getPlanId().intValue() < maxPlanId){
            nextWorkoutKey.setPlanId(nextWorkoutKey.getPlanId()+1);
        }
        else if(nextWorkoutKey.getPlanId().intValue() == maxPlanId.intValue()){

            if((nextWorkoutKey.getWeek().intValue()+1 < maxWeek.intValue()) ||
                    ((nextWorkoutKey.getWeek().intValue()+1 == maxWeek.intValue()))){
                nextWorkoutKey.setWeek(nextWorkoutKey.getWeek().intValue()+1);
                nextWorkoutKey.setPlanId(1);
            }
            else{
                //The user completed the workout do something here
                endOfPlan = true;
            }
        }

        db.todaysWorkoutPlanDao().deleteAll();
        if(!endOfPlan){
             db.todaysWorkoutPlanDao().insert(nextWorkoutKey);
        }

        //Gets the weight entered by user
        int size = workoutTable.getChildCount();
        for(int r = 0; r < size; r++){
            Object oRow = workoutTable.getChildAt(r);

            if(oRow instanceof TableRow){
                TableRow row = (TableRow) oRow;
                int rowSize = row.getChildCount();

                for(int c = 0; c < rowSize; c++){
                    Object cell = row.getChildAt(c);
                    if(cell instanceof EditText){
                        EditText weight = (EditText) cell;
                        if(weight.getText().toString() != null && !Constants.EMPTY.equals(weight.getText().toString())) {
                            list.add(Integer.parseInt(weight.getText().toString()));
                        }
                    }
                }
            }
        }

        List<WorkoutHistoryEntity> history = new ArrayList<>();

        for(int i = 0; i < workout.size(); i++){
            if(i < list.size()){
                history.add(new WorkoutHistoryEntity(workout.get(i).getWeek(),
                                                     workout.get(i).getPlanId(),
                                                     workout.get(i).getSeqNum(),
                                                     workout.get(i).getExerciseId(),
                                                     workout.get(i).getSetId(),
                                                     workout.get(i).getWorkoutId(),
                                                     workout.get(i).getOptional(),
                                                     list.get(i),
                                                     wComplete));
            }
            else{
                history.add(new WorkoutHistoryEntity(workout.get(i).getWeek(),
                                                     workout.get(i).getPlanId(),
                                                     workout.get(i).getSeqNum(),
                                                     workout.get(i).getExerciseId(),
                                                     workout.get(i).getSetId(),
                                                     workout.get(i).getWorkoutId(),
                                                     workout.get(i).getOptional(),
                                              null,
                                                     wComplete));
            }
        }

        db.workoutHistoryDao().insertAll(history);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        completed.completeCallback();
    }
}
