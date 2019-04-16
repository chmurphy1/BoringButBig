package com.christopherwmurphy.boringbutbigapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;

import com.christopherwmurphy.boringbutbigapp.Adapters.ExerciseMaxAdapter;
import com.christopherwmurphy.boringbutbigapp.Adapters.WorkoutPlanAdapter;
import com.christopherwmurphy.boringbutbigapp.Callbacks.ExerciseMaxTaskDelegate;
import com.christopherwmurphy.boringbutbigapp.MainActivity;
import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.Util.Task.ExerciseMaxTask;
import com.christopherwmurphy.boringbutbigapp.Util.Task.TaskResults.ExerciseMaxResults;
import com.christopherwmurphy.boringbutbigapp.ViewHolder.ExerciseMaxViewHolder;
import com.christopherwmurphy.boringbutbigapp.ViewModels.Factory.CustomViewModelFactory;
import com.christopherwmurphy.boringbutbigapp.ViewModels.WorkoutPlanViewModel;
import com.christopherwmurphy.boringbutbigapp.database.Entity.CurrentWorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseMaxEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.Utility.DbExecutor;
import com.christopherwmurphy.boringbutbigapp.database.WorkoutDB;

import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkoutPlanFragment extends Fragment {

    private Bundle parameters;
    private WorkoutPlanAdapter adapter;

    @BindView(R.id.workoutPlanList)
    ExpandableListView workoutPlanDetail;

    @BindView(R.id.createWorkoutButton)
    Button workoutButton;
    View workoutPlanView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        workoutPlanView = inflater.inflate(R.layout.workout_plan_detail_fragment, container,false);
        ButterKnife.bind(this, workoutPlanView);

        return workoutPlanView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null){
            parameters = savedInstanceState.getBundle(Constants.PARAMETERS);
        }
        else{
            parameters = this.getArguments();
        }
        setupExpandableListView();
    }

    @OnClick(R.id.createWorkoutButton)
    public void showPopUp(){
        ExerciseMaxTask task = new ExerciseMaxTask(getContext(), new ExerciseMaxTaskDelegate() {
            @Override
            public void callback(final ExerciseMaxResults exerciseMaxResults) {

                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.exercise_max_popup_layout,null);

                RecyclerView maxRecyclerView = (RecyclerView) customView.findViewById(R.id.maxList);
                Button cancel = (Button) customView.findViewById(R.id.cancel_button);
                Button save = (Button) customView.findViewById(R.id.save_button);

                final ExerciseMaxAdapter adapter = new ExerciseMaxAdapter(exerciseMaxResults);
                LinearLayoutManager layoutMgr = new LinearLayoutManager(customView.getContext());

                maxRecyclerView.setLayoutManager(layoutMgr);
                maxRecyclerView.setHasFixedSize(true);
                maxRecyclerView.setAdapter(adapter);

                final PopupWindow popup = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                popup.setFocusable(true);
                popup.update();
                popup.showAtLocation(workoutPlanView, Gravity.CENTER, 0, 0);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<ExerciseMaxViewHolder> viewHolders = adapter.getItems();
                        final List<ExerciseMaxEntity> newMaxes = new ArrayList<>();
                        boolean errorFlag = false;

                        for(ExerciseMaxViewHolder view : viewHolders){
                            Object obj = view.getExercise();
                            if(view.getNewMax() > 0){

                                if (obj instanceof ExerciseMaxEntity){

                                    ExerciseMaxEntity e = (ExerciseMaxEntity) obj;
                                    if(e.getMax().intValue() != view.getNewMax().intValue()){
                                        newMaxes.add(new ExerciseMaxEntity(e.getMaxId(),view.getNewMax(), new Timestamp(System.currentTimeMillis())));
                                    }

                                }else if(obj instanceof ExerciseEntity){
                                    ExerciseEntity e = (ExerciseEntity) obj;
                                    newMaxes.add(new ExerciseMaxEntity(e.getId(),view.getNewMax(),new Timestamp(System.currentTimeMillis())));
                                }
                            }
                            else{
                                errorFlag = true;

                                String lift = "";
                                if (obj instanceof ExerciseMaxEntity){
                                    ExerciseMaxEntity e = (ExerciseMaxEntity) obj;
                                    lift = ((ExerciseMaxEntity) obj).getExercise().getName();
                                }else if(obj instanceof ExerciseEntity){
                                    ExerciseEntity e = (ExerciseEntity) obj;
                                    lift = ((ExerciseEntity) obj).getName();
                                }

                                Toast.makeText(getContext(),getContext().getResources().getString(R.string.max_error_message_part_1)+Constants.SPACE+
                                        lift+Constants.SPACE+getContext().getResources().getString(R.string.max_error_message_part_2),Toast.LENGTH_LONG).show();

                                break;
                            }
                        }
                        if(!errorFlag){
                            DbExecutor.getInstance().getDbThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    WorkoutDB db = WorkoutDB.getInstance(getContext());

                                    if(!newMaxes.isEmpty()){
                                        db.exerciseMaxDao().insertAll(newMaxes);
                                    }

                                    List<WorkoutPlanEntity> plans = db.workoutPlanDao().getAllWorkoutPlanById(parameters.getInt(Constants.WORKOUT_ID));
                                    List<CurrentWorkoutPlanEntity> currentPlans = new ArrayList<>();

                                    for(WorkoutPlanEntity p : plans){
                                        CurrentWorkoutPlanEntity current = new CurrentWorkoutPlanEntity(p.getWeek(),
                                                                                                        p.getPlanId(),
                                                                                                        p.getSeqNum(),
                                                                                                        p.getExerciseId(),
                                                                                                        p.getSetId(),
                                                                                                        p.getWorkoutId(),
                                                                                                        p.getOptional());
                                        currentPlans.add(current);
                                    }

                                    //delete old plan
                                    db.currentWorkoutPlanDao().deleteAll();
                                    db.currentWorkoutPlanDao().insertAll(currentPlans);
                                }
                            });
                            Intent intent = new Intent(getContext(),MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup.dismiss();
                    }
                });

            }
        });

        ArrayList<Integer> keys = parameters.getIntegerArrayList(Constants.LIST_ID);
        Integer[] listOfKeys = new Integer[keys.size()];

        for(int i = 0; i < listOfKeys.length; i++){
            listOfKeys[i] = keys.get(i);
        }

        task.execute(listOfKeys);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(Constants.PARAMETERS, parameters);
    }

    public void  setupExpandableListView(){
        WorkoutPlanViewModel wpvm = ViewModelProviders.of(this, new CustomViewModelFactory(this.getActivity().getApplication(), parameters))
                .get(WorkoutPlanViewModel.class);

        wpvm.getPlans().observe(this, new Observer<List<WorkoutPlanEntity>>() {
            @Override
            public void onChanged(@Nullable List<WorkoutPlanEntity> workoutPlanEntities) {

                int week = 1;
                int index = 0;
                int planId = 1;
                ArrayList<String> header = new ArrayList<>();
                HashMap<Integer, List<String>> rows = new HashMap<>();
                ArrayList<String> list = new ArrayList<>();

                for(WorkoutPlanEntity entity : workoutPlanEntities){
                    String row = "";

                    if(week != entity.getWeek()){
                        header.add(getContext().getResources().getString(R.string.week)+ Constants.SPACE + week);

                        if(!list.isEmpty()) {
                            rows.put(index++, list);
                            list = new ArrayList<>();
                        }
                        week = entity.getWeek();
                        planId = entity.getPlanId();
                    }

                    if(planId != entity.getPlanId()){
                        planId = entity.getPlanId();
                        list.add(Constants.NEW_LINE);
                    }

                    if(entity.getOptional()){
                        row = getContext().getResources().getString(R.string.optional);
                    }

                    if(entity.getScheme().getPercentage() == 0.0){
                        row += Constants.SPACE + entity.getExercise().getName() + Constants.SPACE +
                                entity.getScheme().getSet() + Constants.SPACE +
                                Constants.X + Constants.SPACE +
                                entity.getScheme().getReps();
                    }
                    else {
                        row += entity.getExercise().getName() + Constants.SPACE +
                                entity.getScheme().getSet() + Constants.SPACE +
                                Constants.X + Constants.SPACE +
                                entity.getScheme().getReps() + Constants.SPACE +
                                Constants.AT + Constants.SPACE +
                                (entity.getScheme().getPercentage() * 100) + Constants.PERCENT_SIGN;
                    }

                    list.add(row);
                }
                header.add(getContext().getResources().getString(R.string.week)+ Constants.SPACE + week);
                rows.put(index, list);

                adapter = new WorkoutPlanAdapter(header, rows, getContext());
                workoutPlanDetail.setAdapter(adapter);
            }
        });
    }
}
