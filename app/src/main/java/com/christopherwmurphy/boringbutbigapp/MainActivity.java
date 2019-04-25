package com.christopherwmurphy.boringbutbigapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.christopherwmurphy.boringbutbigapp.Callbacks.CallbackFromWorkout;
import com.christopherwmurphy.boringbutbigapp.fragments.ExerciseFragment;
import com.christopherwmurphy.boringbutbigapp.fragments.HomeFragment;
import com.christopherwmurphy.boringbutbigapp.fragments.WorkoutFragment;
import com.christopherwmurphy.boringbutbigapp.fragments.WorkoutHistoryFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 *  I referenced the android documentation found at
 *  https://developer.android.com/training/implementing-navigation/nav-drawer
 *  for the navigation drawer.
 *
 */
public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentCallback {

    Account mAccount;
    ContentResolver mResolver;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav)
    NavigationView navigationView;

    private Fragment fragment = null;

    private CallbackFromWorkout wCallback = new CallbackFromWorkout() {
        @Override
        public void callback() {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();

            fragment = new HomeFragment();
            setGone();
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if(isTablet){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        mResolver = getContentResolver();
        mAccount = CreateSyncAccount(this);
        startDataAdapter();
        startNavView();
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        setupHome(false);
        setGone();
    }

    public void setGone(){
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if(isTablet){
            FrameLayout divider = (FrameLayout) findViewById(R.id.divider);
            FrameLayout detail = (FrameLayout) findViewById(R.id.detail);

            divider.setVisibility(View.GONE);
            detail.setVisibility(View.GONE);
        }
    }

    public void setVisible(){
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if(isTablet){
            FrameLayout divider = (FrameLayout) findViewById(R.id.divider);
            FrameLayout detail = (FrameLayout) findViewById(R.id.detail);

            divider.setVisibility(View.INVISIBLE);
            detail.setVisibility(View.VISIBLE);
        }
    }

    public void setupHome( boolean replace){

        FragmentManager mgr = getSupportFragmentManager();
        Fragment fgr = mgr.findFragmentById(R.id.content);

        if((fgr == null) || (replace)){
            mgr.beginTransaction()
                    .replace(R.id.content, new HomeFragment()).commit();
        }
    }

    public void startNavView(){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        onSelectDrawerItem(menuItem);
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    public void onSelectDrawerItem(MenuItem menuItem) {

        FragmentManager mgr = getSupportFragmentManager();

        switch (menuItem.getItemId()) {
            case R.id.nav_home :
                setGone();
                fragment = new HomeFragment();
                break;
            case R.id.nav_plans:
                setVisible();
                fragment = new WorkoutFragment();
                ((WorkoutFragment) fragment).setwCallback(wCallback);
                break;
            case R.id.nav_history:
                setVisible();
                fragment = new WorkoutHistoryFragment();
                break;
            case R.id.nav_movements:
                setVisible();
                fragment = new ExerciseFragment();
                break;
            default:
                setGone();
                fragment = new HomeFragment();
        }
        mgr.beginTransaction().replace(R.id.content, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startDataAdapter(){
        ContentResolver.setSyncAutomatically(mAccount, this.getResources().getString(R.string.authority), true);
        ContentResolver.requestSync(mAccount,this.getResources().getString(R.string.authority),Bundle.EMPTY);
        ContentResolver.addPeriodicSync(mAccount, this.getResources().getString(R.string.authority),Bundle.EMPTY, 3600);
    }

    public static Account CreateSyncAccount(Context context){

        Account newAccount = new Account(context.getResources().getString(R.string.dummy_account),
                                         context.getResources().getString(R.string.domain_name));

        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
        Account[] accounts = accountManager.getAccounts();

        for(Account ac : accounts){
            if(ac.equals(newAccount)){
                return newAccount;
            }
        }

        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            return newAccount;
        } else {
           // throw new Exception("An error occurred adding the account to the account manager.");
            return null;
        }
    }

    @Override
    public void callback() {
        setupHome(true);
    }
}
