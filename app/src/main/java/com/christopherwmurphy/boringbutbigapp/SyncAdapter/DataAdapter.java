package com.christopherwmurphy.boringbutbigapp.SyncAdapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

public class DataAdapter extends AbstractThreadedSyncAdapter {

    public DataAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public DataAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {

        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              SyncResult syncResult) {

        //add data transfer code


    }
}
