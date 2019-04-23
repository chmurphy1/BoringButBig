package com.christopherwmurphy.boringbutbigapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WorkoutProviderService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new DataListProvider(this.getApplicationContext(), intent));
    }
}
