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
            //getExerciseStepResultsNotInList(steps);
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
            //getWorkoutPlanResultsNotInList(plan);
        }
    }

    private void getWorkoutResultsNotInList(List<WorkoutEntity> workouts){
        Call<List<Workout>> workout_call = null;
        Response<List<Workout>> workout_response = null;
        List<Workout> workoutList = null;

        Set<Integer> id = new HashSet();
        for(WorkoutEntity we : workouts){
            id.add(we.getWorkoutId());
        }

        workout_call = service.getNotInWorkoutList(id);

        try {
            workout_response = workout_call.execute();
            workoutList = workout_response.body();
            Log.d("WorkoutList","<<<<< returned: " + workoutList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        insertAllIntoWorkoutTable(workoutList);
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
                WorkoutEntity entity = new WorkoutEntity(w.getWorkoutId(), w.getName(), w.getLanguage(), w.getSeqNum());
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

        Set<Integer> id = new HashSet();
        for(SetSchemeEntity ss : schemes){
            id.add(ss.getSetId());
        }

        scheme_call = service.getNotInSetSchemeList(id);

        try {
            scheme_response = scheme_call.execute();
            schemeList = scheme_response.body();
            Log.d("schemeList","<<<<<" +  schemeList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        insertAllIntoSetSchemeTable(schemeList);
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

        Set<Integer> id = new HashSet();
        for(ExerciseVideosEntity vids : videos){
            id.add(vids.getId());
        }

        video_call = service.getNotInExerciseVideoList(id);

        try {
            video_response = video_call.execute();
            videosList =  video_response.body();
            Log.d("videosList","<<<<<" +  videosList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        insertAllIntoExerciseVideoTable(videosList);
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

        Set<ExerciseStepsPk> id = new HashSet();
        for(ExerciseStepsEntity s : steps){
            id.add(new ExerciseStepsPk(s.getExerciseId(), s.getStepSeq()));
        }

        steps_call = service.getNotInExerciseStepsList(id);
        Log.d("stepList","<<<<<" +  stepList.toString());

        try {
            steps_response = steps_call.execute();
            stepList = steps_response.body();
            Log.d("stepList","<<<<<" +  stepList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        insertAllIntoExerciseStepsTable(stepList);
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

        Set<Integer> id = new HashSet();
        for(ExerciseEntity ex : exercise){
            id.add(ex.getId());
        }

        exercise_call = service.getNotInExerciseList(id);

        try {
            exercise_response = exercise_call.execute();
            exerciseList = exercise_response.body();
            Log.d("exerciseList","<<<<<returned: " +  exerciseList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        insertAllIntoExerciseTable(exerciseList);
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

        Set<WorkoutPlanPk> id = new HashSet();
        for(WorkoutPlanEntity p : plan){
            id.add(new WorkoutPlanPk(p.getWeek(), p.getPlanId(), p.getSeqNum(), p.getWorkoutId()));
        }

        plan_call = service.getNotInWorkoutPlanList(id);

        try {
            plan_response = plan_call.execute();
            planList =  plan_response.body();
            Log.d("planList","<<<<<" +  planList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        insertAllIntoWorkoutPlanTable(planList);
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