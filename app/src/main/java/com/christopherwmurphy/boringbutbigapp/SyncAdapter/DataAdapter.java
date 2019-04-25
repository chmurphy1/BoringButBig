package com.christopherwmurphy.boringbutbigapp.SyncAdapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.Exercise;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.ExerciseSteps;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.ExerciseVideos;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.SetScheme;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.Workout;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.WorkoutPlan;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.keys.ExerciseStepsPk;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.keys.WorkoutPlanPk;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.RetrofitUtil;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Services.WorkoutService;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseStepsEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseVideosEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.SetSchemeEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.WorkoutDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DataAdapter extends AbstractThreadedSyncAdapter {

    private ContentResolver contentResolver;

    private Retrofit retrofitInstance;
    private WorkoutService service;
    private WorkoutDB db;

    public DataAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        contentResolver = context.getContentResolver();

        retrofitInstance = RetrofitUtil.getRetrofitInstance();
        service = retrofitInstance.create(WorkoutService.class);
        db = WorkoutDB.getInstance(context);
    }

    public DataAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {

        super(context, autoInitialize, allowParallelSyncs);
        contentResolver = context.getContentResolver();

        retrofitInstance = RetrofitUtil.getRetrofitInstance();
        service = retrofitInstance.create(WorkoutService.class);
        db = WorkoutDB.getInstance(context);
    }

    @Override
    public void onPerformSync(Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              SyncResult syncResult) {

        List<WorkoutEntity> workouts = db.workoutDao().geListOfWorkouts();
        Log.d("workout_size","<<<<<" + workouts.size());
        if(workouts == null || workouts.isEmpty()){
            getAllWorkoutResults();
        }
        else{
            getWorkoutResultsNotInList(workouts);
        }

        List<SetSchemeEntity> schemes = db.setSchemeDao().getAllSchemes();
        Log.d("scheme_size","<<<<<" + schemes.size());
        if(schemes == null || schemes.isEmpty()){
            getAllSetSchemesResults();
        }
        else{
            getSetSchemeResultsNotInList(schemes);
        }

        List<ExerciseVideosEntity> videos = db.exerciseVideosDao().getAllVideos();
        Log.d("videos_size","<<<<<" + videos.size());
        if(videos == null || videos.isEmpty()){
            getAllExerciseVideoResults();
        }
        else{
            getExerciseVideoResultsNotInList(videos);
        }

        List<ExerciseStepsEntity> steps = db.exerciseStepsDao().getAllSteps();
        Log.d("steps_size","<<<<<" + steps.size());
        if(steps == null || steps.isEmpty()){
            getAllExerciseStepResults();
        }
        else{
            getExerciseStepResultsNotInList(steps);
        }

        List<ExerciseEntity> exercises = db.exerciseDao().getListOfExercises();
        Log.d("exercises_size","<<<<<" + exercises.size());
        if(exercises == null || exercises.isEmpty()){
            getAllExerciseResults();
        }
        else{
            getExerciseResultsNotInList(exercises);
        }

        List<WorkoutPlanEntity> plan = db.workoutPlanDao().getAllWorkoutPlans();
        Log.d("plan_size","<<<<<" + plan.size());
        if(plan == null || plan.isEmpty()){
            getAllWorkoutPlanResults();
        }
        else{
            getWorkoutPlanResultsNotInList(plan);
        }
    }

    private void getWorkoutResultsNotInList(List<WorkoutEntity> workouts){
        Call<List<Workout>> workout_call = null;
        Response<List<Workout>> workout_response = null;
        List<Workout> workoutList = null;

        workout_call = service.getAllWorkouts();

        try {
            workout_response = workout_call.execute();
            workoutList = workout_response.body();
            Log.d("WorkoutList","<<<<< returned: " + workoutList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<WorkoutEntity> updateList = new ArrayList<>();

        List<WorkoutEntity> serverEntities = new ArrayList<>();
        for(Workout w :  workoutList){
            WorkoutEntity entity = new WorkoutEntity(w.getWorkoutId(), w.getName(), w.getLanguage(), w.getSeqNum(),w.getLifts(),w.getIncrements(),w.getPeriod());
            serverEntities.add(entity);
        }

        //finds new entries
        for(WorkoutEntity s : serverEntities){
            if(! workouts.contains(s)){
                Log.d("WorkoutList","<<<< client doesn't have the following record "+ s.toString()+ " client will be updated");
                updateList.add(s);
            }
        }

        for(WorkoutEntity s :  workouts){
            if(!serverEntities.contains(s)){
                Log.d("WorkoutList","<<<< server doesn't have the following record "+ s.toString()+ " the record will be deleted");
                db.workoutDao().delete(s.getWorkoutId());
            }
            else{
                //if the entity does exist check if they are equal
                int index = serverEntities.indexOf(s);

                if(index >= 0){
                    if(!s.equals(serverEntities.get(index))){
                        Log.d("WorkoutList", "<<<< the following entries do not match");
                        Log.d("WorkoutList", s.toString());
                        Log.d("WorkoutList", serverEntities.get(index).toString());
                        Log.d("WorkoutList", "updating the record with the server's data");
                        updateList.add(serverEntities.get(index));
                    }
                }
            }
        }

        db.workoutDao().insertAll(updateList);
    }

    private void getAllWorkoutResults(){
        Call<List<Workout>> workout_call = service.getAllWorkouts();
        Response<List<Workout>> workout_response = null;
        List<Workout> workoutList = null;

        try {
            workout_response = workout_call.execute();
            workoutList = workout_response.body();
            Log.d("WorkoutList","<<<<<" + workoutList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        insertAllIntoWorkoutTable(workoutList);
    }

    private void insertAllIntoWorkoutTable(List<Workout> workoutList){
        if((workoutList != null) && (!workoutList.isEmpty())){
            List<WorkoutEntity> workoutEntities = new ArrayList<>();

            for(Workout w : workoutList){
                WorkoutEntity entity = new WorkoutEntity(w.getWorkoutId(), w.getName(), w.getLanguage(), w.getSeqNum(),w.getLifts(),w.getIncrements(),w.getPeriod());
                workoutEntities.add(entity);
            }
            Log.d("WorkoutList","<<<<<before db insert");
            db.workoutDao().insertAll(workoutEntities);
        }
    }

    private void getAllSetSchemesResults(){
        Call<List<SetScheme>> scheme_call = service.getAllWorkoutSchemes();
        Response<List<SetScheme>> scheme_response = null;
        List<SetScheme> schemeList = null;

        try {
            scheme_response = scheme_call.execute();
            schemeList = scheme_response.body();
            Log.d("schemeList","<<<<<" +  schemeList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        insertAllIntoSetSchemeTable(schemeList);
    }

    private void getSetSchemeResultsNotInList(List<SetSchemeEntity> schemes){
        Call<List<SetScheme>> scheme_call = service.getAllWorkoutSchemes();
        Response<List<SetScheme>> scheme_response = null;
        List<SetScheme> schemeList = null;

        try {
            scheme_response = scheme_call.execute();
            schemeList = scheme_response.body();
            Log.d("schemeList","<<<<<" +  schemeList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SetSchemeEntity> updateList = new ArrayList<>();

        List<SetSchemeEntity> serverEntities = new ArrayList<>();
        for(SetScheme s :   schemeList){
            SetSchemeEntity entity = new SetSchemeEntity(s.getSetId(),s.getSet(), s.getReps(), s.getPercentage());
            serverEntities.add(entity);
        }

        //finds new entries
        for(SetSchemeEntity s : serverEntities){
            if(! schemes.contains(s)){
                Log.d("schemeList","<<<< client doesn't have the following record "+ s.toString()+ " client will be updated");
                updateList.add(s);
            }
        }

        for(SetSchemeEntity s :  schemes){
            if(!serverEntities.contains(s)){
                Log.d("schemeList","<<<< server doesn't have the following record "+ s.toString()+ " the record will be deleted");
                db.setSchemeDao().delete(s.getSetId());
            }
            else{
                //if the entity does exist check if they are equal
                int index = serverEntities.indexOf(s);

                if(index >= 0){
                    if(!s.equals(serverEntities.get(index))){
                        Log.d("schemeList", "<<<< the following entries do not match");
                        Log.d("schemeList", s.toString());
                        Log.d("schemeList", serverEntities.get(index).toString());
                        Log.d("schemeList", "updating the record with the server's data");
                        updateList.add(serverEntities.get(index));
                    }
                }
            }
        }

        db.setSchemeDao().insertAll(updateList);
    }

    private void insertAllIntoSetSchemeTable(List<SetScheme> schemeList){
        if((schemeList != null) && (!schemeList.isEmpty())){
            List<SetSchemeEntity> entities = new ArrayList<>();

            for(SetScheme s : schemeList){
                SetSchemeEntity entity = new SetSchemeEntity(s.getSetId(),s.getSet(), s.getReps(), s.getPercentage());
                entities.add(entity);
            }
            Log.d("schemeList","<<<<<before db insert");
            db.setSchemeDao().insertAll(entities);
        }
    }

    private void getAllExerciseVideoResults() {
        Call<List<ExerciseVideos>> video_call = service.getAllExerciseVideos();
        Response<List<ExerciseVideos>> video_response = null;
        List<ExerciseVideos> videosList = null;

        try {
            video_response = video_call.execute();
            videosList =  video_response.body();
            Log.d("videosList","<<<<<" +  videosList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        insertAllIntoExerciseVideoTable(videosList);
    }

    private void getExerciseVideoResultsNotInList(List<ExerciseVideosEntity> videos){
        Call<List<ExerciseVideos>> video_call = null;
        Response<List<ExerciseVideos>> video_response = null;
        List<ExerciseVideos> videosList = null;

        video_call = service.getAllExerciseVideos();

        try {
            video_response = video_call.execute();
            videosList =  video_response.body();
            Log.d("videosList","<<<<<" +  videosList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<ExerciseVideosEntity> updateList = new ArrayList<>();

        List<ExerciseVideosEntity> serverEntities = new ArrayList<>();
        for(ExerciseVideos v :   videosList){
            ExerciseVideosEntity entity = new ExerciseVideosEntity(v.getId(),v.getVideo_url());
            serverEntities.add(entity);
        }

        //finds new entries
        for(ExerciseVideosEntity s : serverEntities){
            if(! videos.contains(s)){
                Log.d("videosList","<<<< client doesn't have the following record "+ s.toString()+ " client will be updated");
                updateList.add(s);
            }
        }

        for(ExerciseVideosEntity s :  videos){
            if(!serverEntities.contains(s)){
                Log.d("videosList","<<<< server doesn't have the following record "+ s.toString()+ " the record will be deleted");
                db.exerciseVideosDao().delete(s.getId());
            }
            else{
                //if the entity does exist check if they are equal
                int index = serverEntities.indexOf(s);

                if(index >= 0){
                    if(!s.equals(serverEntities.get(index))){
                        Log.d("videosList", "<<<< the following entries do not match");
                        Log.d("videosList", s.toString());
                        Log.d("videosList", serverEntities.get(index).toString());
                        Log.d("videosList", "updating the record with the server's data");
                        updateList.add(serverEntities.get(index));
                    }
                }
            }
        }

        db.exerciseVideosDao().insertAll(updateList);
    }

    private void insertAllIntoExerciseVideoTable(List<ExerciseVideos> videosList){
        if((videosList != null) && (!videosList.isEmpty())){
            List<ExerciseVideosEntity> entities = new ArrayList<>();

            for(ExerciseVideos v : videosList){
                ExerciseVideosEntity entity = new ExerciseVideosEntity(v.getId(),v.getVideo_url());
                entities.add(entity);
            }
            Log.d("videosList","<<<<<before db insert");
            db.exerciseVideosDao().insertAll(entities);
        }
    }

    private void getExerciseStepResultsNotInList(List<ExerciseStepsEntity> steps){
        Call<List<ExerciseSteps>> steps_call = null;
        Response<List<ExerciseSteps>> steps_response = null;
        List<ExerciseSteps> stepList = null;

        steps_call = service.getAllExerciseSteps();

        try {
            steps_response = steps_call.execute();
            stepList = steps_response.body();
            Log.d("stepList","<<<<<" +  stepList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<ExerciseStepsEntity> updateList = new ArrayList<>();

        List<ExerciseStepsEntity> serverEntities = new ArrayList<>();
        for(ExerciseSteps p :  stepList){
            ExerciseStepsEntity entity = new ExerciseStepsEntity(p.getId().getExerciseId(), p.getId().getStepSeq(), p.getStepText(), p.getLanguage());
            serverEntities.add(entity);
        }

        //finds new entries
        for(ExerciseStepsEntity s : serverEntities){
            if(!steps.contains(s)){
                Log.d("stepList","<<<< client doesn't have the following record "+ s.toString()+ " client will be updated");
                updateList.add(s);
            }
        }

        for(ExerciseStepsEntity s : steps){
            if(!serverEntities.contains(s)){
                Log.d("stepList","<<<< server doesn't have the following record "+ s.toString()+ " the record will be deleted");
                db.exerciseStepsDao().delete(s.getExerciseId(), s.getStepSeq());
            }
            else{
                //if the entity does exist check if they are equal
                int index = serverEntities.indexOf(s);

                if(index >= 0){
                    if(!s.equals(serverEntities.get(index))){
                        Log.d("stepList", "<<<< the following entries do not match");
                        Log.d("stepList", s.toString());
                        Log.d("stepList", serverEntities.get(index).toString());
                        Log.d("stepList", "updating the record with the server's data");
                        updateList.add(serverEntities.get(index));
                    }
                }
            }
        }

        db.exerciseStepsDao().insertAll(updateList);
    }

    private void getAllExerciseStepResults() {
        Call<List<ExerciseSteps>> steps_call = service.getAllExerciseSteps();
        Response<List<ExerciseSteps>> steps_response = null;
        List<ExerciseSteps> stepList = null;

        try {
            steps_response = steps_call.execute();
            stepList = steps_response.body();
            Log.d("stepList","<<<<<" +  stepList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        insertAllIntoExerciseStepsTable(stepList);
    }

    private void insertAllIntoExerciseStepsTable(List<ExerciseSteps> stepList){
        if((stepList != null) && (!stepList.isEmpty())){
            List<ExerciseStepsEntity> entities = new ArrayList<>();

            for(ExerciseSteps s : stepList){
                ExerciseStepsEntity entity = new ExerciseStepsEntity(s.getId().getExerciseId(), s.getId().getStepSeq(), s.getStepText(), s.getLanguage());
                entities.add(entity);
            }
            Log.d("stepList","<<<<<before db insert");
            db.exerciseStepsDao().insertAll(entities);
        }
    }

    private void getAllExerciseResults(){
        Call<List<Exercise>> exercise_call = service.getAllExercises();
        Response<List<Exercise>> exercise_response = null;
        List<Exercise> exerciseList = null;

        try {
            exercise_response = exercise_call.execute();
            exerciseList = exercise_response.body();
            Log.d("exerciseList","<<<<<" +  exerciseList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        insertAllIntoExerciseTable(exerciseList);
    }
    private void getExerciseResultsNotInList(List<ExerciseEntity> exercise){
        Call<List<Exercise>> exercise_call = null;
        Response<List<Exercise>> exercise_response = null;
        List<Exercise> exerciseList = null;

        exercise_call = service.getAllExercises();

        try {
            exercise_response = exercise_call.execute();
            exerciseList = exercise_response.body();
            Log.d("exerciseList","<<<<<returned: " +  exerciseList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<ExerciseEntity> updateList = new ArrayList<>();

        List<ExerciseEntity> serverEntities = new ArrayList<>();
        for(Exercise e :  exerciseList){
            ExerciseEntity entity = new ExerciseEntity(e.getId(),e.getName(),e.getLanguage(), e.getVideo().getId());
            serverEntities.add(entity);
        }

        //finds new entries
        for(ExerciseEntity s : serverEntities){
            if(!exercise.contains(s)){
                Log.d("exerciseList","<<<< client doesn't have the following record "+ s.toString()+ " client will be updated");
                updateList.add(s);
            }
        }

        for(ExerciseEntity s : exercise){
            if(!serverEntities.contains(s)){
                Log.d("exerciseList","<<<< server doesn't have the following record "+ s.toString()+ " the record will be deleted");
                db.exerciseDao().delete(s.getId());
            }
            else{
                //if the entity does exist check if they are equal
                int index = serverEntities.indexOf(s);

                if(index >= 0){
                    if(!s.equals(serverEntities.get(index))){
                        Log.d("exerciseList", "<<<< the following entries do not match");
                        Log.d("exerciseList", s.toString());
                        Log.d("exerciseList", serverEntities.get(index).toString());
                        Log.d("exerciseList", "updating the record with the server's data");
                        updateList.add(serverEntities.get(index));
                    }
                }
            }
        }

        db.exerciseDao().insertAll(updateList);
    }

    private void insertAllIntoExerciseTable(List<Exercise> exerciseList){
        if((exerciseList != null) && (!exerciseList.isEmpty())){
            List<ExerciseEntity> entities = new ArrayList<>();

            for(Exercise e : exerciseList){
                ExerciseEntity entity = new ExerciseEntity(e.getId(),e.getName(),e.getLanguage(), e.getVideo().getId());
                entities.add(entity);
            }
            Log.d("exerciseList","<<<<<before db insert");
            db.exerciseDao().insertAll(entities);
        }
    }

    private void getAllWorkoutPlanResults(){
        Call<List<WorkoutPlan>> plan_call = service.getAllWorkoutPlans();
        Response<List<WorkoutPlan>> plan_response = null;
        List<WorkoutPlan> planList = null;

        try {
            plan_response = plan_call.execute();
            planList =  plan_response.body();
            Log.d("planList","<<<<<" +  planList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        insertAllIntoWorkoutPlanTable(planList);
    }

    private void getWorkoutPlanResultsNotInList( List<WorkoutPlanEntity> plan ){
        Call<List<WorkoutPlan>> plan_call = null;
        Response<List<WorkoutPlan>> plan_response = null;
        List<WorkoutPlan> planList = null;

        plan_call = service.getAllWorkoutPlans();

        try {
            plan_response = plan_call.execute();
            planList =  plan_response.body();
            Log.d("planList","<<<<<" +  planList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<WorkoutPlanEntity> updateList = new ArrayList<>();

        List<WorkoutPlanEntity> serverEntities = new ArrayList<>();
        for(WorkoutPlan p : planList){
            WorkoutPlanEntity entity = new WorkoutPlanEntity(p.getId().getWeek(),p.getId().getPlanId(),
                                                             p.getId().getSeqNum(),p.getExercise().getId(),
                                                             p.getSetScheme().getSetId(),p.getWorkout().getWorkoutId(),p.getOptional());
            serverEntities.add(entity);
        }

        //finds new entries
        for(WorkoutPlanEntity s : serverEntities){
            if(!plan.contains(s)){
                Log.d("planList","<<<< client doesn't have the following record "+ s.toString()+ " client will be updated");
                updateList.add(s);
            }
        }

        for(WorkoutPlanEntity s : plan){
            if(!serverEntities.contains(s)){
                Log.d("planList","<<<< server doesn't have the following record "+ s.toString()+ " the record will be deleted");
                db.workoutPlanDao().delete(s.getWeek(), s.getPlanId(), s.getSeqNum(), s.getWorkoutId());
            }
            else{
                //if the entity does exist check if they are equal
                int index = serverEntities.indexOf(s);

                if(index >= 0){
                    if(!s.equals(serverEntities.get(index))){
                        Log.d("planList", "<<<< the following entries do not match");
                        Log.d("planList", s.toString());
                        Log.d("planList", serverEntities.get(index).toString());
                        Log.d("planList", "updating the record with the server's data");
                        updateList.add(serverEntities.get(index));
                    }
                }
            }
        }

        db.workoutPlanDao().insertAll(updateList);
    }

    private void insertAllIntoWorkoutPlanTable(List<WorkoutPlan> planList){
        if((planList != null) && (!planList.isEmpty())){
            List<WorkoutPlanEntity> entities = new ArrayList<>();

            for(WorkoutPlan p : planList){
                WorkoutPlanEntity entity = new WorkoutPlanEntity(p.getId().getWeek(),p.getId().getPlanId(),
                                                                 p.getId().getSeqNum(),p.getExercise().getId(),
                                                                 p.getSetScheme().getSetId(),p.getWorkout().getWorkoutId(),p.getOptional());
                entities.add(entity);
            }
            Log.d("planList","<<<<<before db insert");
            db.workoutPlanDao().insertAll(entities);
        }
    }
}