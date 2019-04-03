package com.christopherwmurphy.boringbutbigapp.SyncAdapter.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.christopherwmurphy.boringbutbigapp.SyncAdapter.DataAdapter;

public class DataAdapterService extends Service {
    private static DataAdapter dAdapter;
    private static final Object dDataAdapterLock = new Object();

    public void onCreate(){
        synchronized (dDataAdapterLock) {
            if (dAdapter == null) {
                dAdapter = new DataAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return dAdapter.getSyncAdapterBinder();
    }
}
