package com.christopherwmurphy.boringbutbigapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.fragments.WorkoutHistoryDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutHistoryDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Bundle parameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        if( savedInstanceState != null) {
            parameters = savedInstanceState.getBundle(Constants.PARAMETERS);
        }
        else{
            Intent intent = getIntent();
            parameters = intent.getExtras();
        }
        attachFragment();
    }

    public void attachFragment(){
        FragmentManager mgr = getSupportFragmentManager();
        Fragment fgr = mgr.findFragmentById(R.id.content);

        if(fgr == null){
            WorkoutHistoryDetailFragment detail = new WorkoutHistoryDetailFragment();
            detail.setArguments(parameters);

            mgr.beginTransaction()
                    .replace(R.id.content, detail)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(Constants.PARAMETERS, parameters);
    }
}
