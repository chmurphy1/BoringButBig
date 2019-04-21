package com.christopherwmurphy.boringbutbigapp.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.christopherwmurphy.boringbutbigapp.Callbacks.WorkoutHistoryCallback;
import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutHistoryEntity;

import java.sql.Date;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkoutHistoryViewHolder  extends RecyclerView.ViewHolder {

    private WorkoutHistoryEntity workout;
    private Context context;
    private WorkoutHistoryCallback wListener;

    @BindView(R.id.date)
    TextView workoutDate;

    public WorkoutHistoryViewHolder(View itemView, WorkoutHistoryCallback callback) {
        super(itemView);
        context = itemView.getContext();
        this.wListener = callback;
        ButterKnife.bind(this, itemView);
    }

    public void bind(WorkoutHistoryEntity workout){
        this.workout = workout;
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        workoutDate.setText(sdf.format(workout.getDate()));
    }

    @OnClick(R.id.WorkoutHistoryCard)
    public void onClick(){
        wListener.callback(workout.getDate());
    }
}
