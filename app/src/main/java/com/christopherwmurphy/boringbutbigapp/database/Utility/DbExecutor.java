package com.christopherwmurphy.boringbutbigapp.database.Utility;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DbExecutor {
    private static DbExecutor dbInstance;

    final private Executor dbThread;

    private DbExecutor(Executor db){
        this.dbThread = db;
    }

    public static  DbExecutor getInstance(){
        if(dbInstance == null){
            dbInstance = new DbExecutor(Executors.newSingleThreadExecutor());
        }
        return dbInstance;
    }

    public Executor getDbThread() {
        return dbThread;
    }
}
