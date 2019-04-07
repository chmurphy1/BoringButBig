package com.christopherwmurphy.boringbutbigapp.JsonUtil.Services;

import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.Exercise;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.ExerciseSteps;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.ExerciseVideos;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.SetScheme;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.Workout;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.WorkoutPlan;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.keys.ExerciseStepsPk;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.keys.WorkoutPlanPk;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WorkoutService {

    @GET("/WorkoutAll")
    Call<List<Workout>> getAllWorkouts();

    @GET("/WorkoutByIdNotInList")
    Call<List<Workout>> getNotInWorkoutList(@Query("id") Set<Integer> id);

    @GET("/SetSchemeAll")
    Call<List<SetScheme>> getAllWorkoutSchemes();

    @GET("/SetSchemeByIdNotInList")
    Call<List<SetScheme>> getNotInSetSchemeList(@Query("id") Set<Integer> id);

    @GET("/ExerciseAll")
    Call<List<Exercise>> getAllExercises();

    @GET("/ExerciseByIdNotInList")
    Call<List<Exercise>> getNotInExerciseList(@Query("id") Set<Integer> id);

    @GET("/ExerciseStepsAll")
    Call<List<ExerciseSteps>> getAllExerciseSteps();

    @GET("/ExerciseStepsByIdNotInList")
    Call<List<ExerciseSteps>> getNotInExerciseStepsList(@Query("id") Set<ExerciseStepsPk> id);

    @GET("/WorkoutPlansAll")
    Call<List<WorkoutPlan>> getAllWorkoutPlans();

    @GET("/WorkoutPlansByIdNotInList")
    Call<List<WorkoutPlan>> getNotInWorkoutPlanList(@Query("id") Set<WorkoutPlanPk> id);

    @GET("/ExerciseVideosAll")
    Call<List<ExerciseVideos>> getAllExerciseVideos();

    @GET("/ExerciseVideosByIdNotInList")
    Call<List<ExerciseVideos>> getNotInExerciseVideoList(@Query("id") Set<Integer> id);
}
