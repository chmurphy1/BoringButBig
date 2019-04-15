package com.christopherwmurphy.boringbutbigapp.Util.Task.TaskResults;

import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseMaxEntity;

import java.util.ArrayList;
import java.util.List;

public class ExerciseMaxResults {

    private ArrayList list;

    public ExerciseMaxResults(List<ExerciseMaxEntity> exerciseMaxes, List<ExerciseEntity> exercises) {

        list = new ArrayList();

        for(ExerciseMaxEntity e : exerciseMaxes){
            list.add(e);
        }

        for(ExerciseEntity e : exercises){
            list.add(e);
        }
    }
    public ArrayList getList() {
        return list;
    }

    public void setList(ArrayList list) {
        this.list = list;
    }
}
