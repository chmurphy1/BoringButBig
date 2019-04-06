package com.christopherwmurphy.boringbutbigapp.JsonUtil.Services;

import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.Exercise;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.ExerciseSteps;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.ExerciseVideos;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.SetScheme;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.Workout;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.WorkoutPlan;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WorkoutService {

    @GET("/WorkoutAll")
    Call<List<Workout>> getAllWorkouts();

    @GET("/SetSchemeAll")
    Call<List<SetScheme>> getAllWorkoutSchemes();

    @GET("/ExerciseAll")
    Call<List<Exercise>> getAllExercises();

    @GET("/ExerciseStepsAll")
    Call<List<ExerciseSteps>> getAllExerciseSteps();

    @GET("/WorkoutPlansAll")
    Call<List<WorkoutPlan>> getAllWorkoutPlans();

    @GET("/ExerciseVideosAll")
    Call<List<ExerciseVideos>> getAllExerciseVideos();
}
