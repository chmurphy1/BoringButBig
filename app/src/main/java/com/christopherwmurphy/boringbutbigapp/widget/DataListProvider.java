package com.christopherwmurphy.boringbutbigapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class DataListProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private int appWidgetId;
    private List<ParcelableData> data = new ArrayList<>();

    public DataListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        Bundle widgetBundle = intent.getBundleExtra(Constants.WIDGET_BUNDLE);

        List<ParcelableData> data1 = Parcels.unwrap(widgetBundle.getParcelable(Constants.DATA));
        if(data1 != null){
            this.data = data1;
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_row_layout);

        ParcelableData row = data.get(position);
        remoteView.setTextViewText(R.id.widget_lift, row.getLift());
        remoteView.setTextViewText(R.id.widget_reps, row.getReps());

        if((row.getWeight() != null) && (!Constants.EMPTY.equals(row.getWeight()))){
            remoteView.setTextViewText(R.id.widget_weight, row.getWeight() + Constants.SPACE + context.getResources().getString(R.string.lbs));
        }
        else{
            remoteView.setViewVisibility(R.id.widget_weight, View.INVISIBLE);
        }

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
