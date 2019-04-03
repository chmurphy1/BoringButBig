package com.christopherwmurphy.boringbutbigapp.SyncAdapter.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.christopherwmurphy.boringbutbigapp.SyncAdapter.Authenticator.AppAuthStub;

public class AppAuthStubService extends Service {
    private AppAuthStub aStub;

    public void onCreate(){
        aStub = new AppAuthStub(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return aStub.getIBinder();
    }
}
