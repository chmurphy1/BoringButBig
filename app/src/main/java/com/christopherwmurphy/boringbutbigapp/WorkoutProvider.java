package com.christopherwmurphy.boringbutbigapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.widget.ParcelableData;
import com.christopherwmurphy.boringbutbigapp.widget.WorkoutProviderService;

import org.parceler.Parcels;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class WorkoutProvider extends AppWidgetProvider {

    private List<ParcelableData> wData;

    /*
      https://stackoverflow.com/questions/3140072/android-keeps-caching-my-intents-extras-how-to-declare-a-pending-intent-that-ke

      The intents are being cached and I used the method being suggested in the link above.
     */
    private static int  randomNumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle widgetBundle = intent.getBundleExtra(Constants.WIDGET_BUNDLE);
        if(widgetBundle != null){
            wData = Parcels.unwrap(widgetBundle.getParcelable(Constants.DATA));
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            RemoteViews remoteViews = updateWidgetListView(context, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    private RemoteViews updateWidgetListView(Context context, int appWidgetId){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.workout_provider);

        Bundle intentBundle = new Bundle();
        intentBundle.putParcelable(Constants.DATA, Parcels.wrap(wData));

        Intent service = new Intent(context, WorkoutProviderService.class);
        service.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        service.putExtra(Constants.WIDGET_BUNDLE, intentBundle);
        service.putExtra("random", randomNumber);
        randomNumber++;

        service.setData(Uri.parse(service.toUri(Intent.URI_INTENT_SCHEME)));

        views.setRemoteAdapter(R.id.workoutWidgetList,service);
        return views;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

