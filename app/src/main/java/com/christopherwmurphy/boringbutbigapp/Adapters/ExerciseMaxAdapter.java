package com.christopherwmurphy.boringbutbigapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.Util.Task.TaskResults.ExerciseMaxResults;
import com.christopherwmurphy.boringbutbigapp.ViewHolder.ExerciseMaxViewHolder;
import java.util.ArrayList;
import java.util.List;

public class ExerciseMaxAdapter extends RecyclerView.Adapter<ExerciseMaxViewHolder>  {

    private ArrayList list;
    private ArrayList<ExerciseMaxViewHolder> viewHolder;

    public ExerciseMaxAdapter(ExerciseMaxResults exerciseMaxResults){
        list = exerciseMaxResults.getList();
        viewHolder = new ArrayList<>();
    }

    @NonNull
    @Override
    public ExerciseMaxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(context);
        View v = mInflater.inflate(R.layout.exercise_max_recyclerview, parent, false);

        ExerciseMaxViewHolder holder = new ExerciseMaxViewHolder(v);
        viewHolder.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseMaxViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<ExerciseMaxViewHolder> getItems(){
        return viewHolder;
    }
}
