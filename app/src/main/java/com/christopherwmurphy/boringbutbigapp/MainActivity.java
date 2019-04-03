package com.christopherwmurphy.boringbutbigapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            mAccount = CreateSyncAccount(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Account CreateSyncAccount(Context context) throws Exception {

        Account newAccount = new Account(context.getResources().getString(R.string.dummy_account),
                                         context.getResources().getString(R.string.domain_name));

        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            return newAccount;
        } else {
            throw new Exception("An error occurred adding the account to the account manager.");
        }
    }
}
