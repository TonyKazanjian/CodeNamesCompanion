package com.tonykazanjian.codenamescompanion.main;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.UserPreferences;

public class MainActivity extends AppCompatActivity {


    private Fragment mSelectedFragment;

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private NavigationView mNavigationView;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserPreferences.setCardNumber(this, 8);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            mSelectedFragment = getSupportFragmentManager().getFragment(savedInstanceState, GameFragment.TAG);
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mTitle = mDrawerTitle = getTitle();
        mToolbar.setTitle(mTitle);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.my_navigation_view);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItem(item.getItemId());
                setTitle(item.getTitle());
                return true;
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {

            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(R.id.nav_item_1);
            setTitle(getString(R.string.board));
        }
    }

    private void selectItem(int i) {
        switch (i) {
            case R.id.nav_item_1:
                setGameFragment();
                break;
            case R.id.nav_item_2:
                setScoreboardFragment();
                break;
            case R.id.nav_item_3:
                setTimerFragment();
                break;
        }
        mDrawerLayout.closeDrawer(Gravity.START);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        if (mSelectedFragment != null) {
            getSupportFragmentManager().putFragment(outState, GameFragment.TAG, mSelectedFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mNavigationView);
        MenuItem newGameItem = menu.findItem(R.id.action_new_game);
        MenuItem timerItem = menu.findItem(R.id.action_timer);
        if (newGameItem != null && timerItem != null) {
            menu.findItem(R.id.action_new_game).setVisible(!drawerOpen);
//            menu.findItem(R.id.action_timer).setVisible(!drawerOpen);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        return mDrawerToggle.onOptionsItemSelected(menuItem) || super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void setGameFragment() {
        GameFragment gameFragment = (GameFragment)getSupportFragmentManager().findFragmentByTag(GameFragment.TAG);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (gameFragment == null) {
            gameFragment = GameFragment.newInstance();
        }
        transaction.replace(R.id.content_frame, gameFragment, GameFragment.TAG);
        transaction.addToBackStack(GameFragment.TAG);
        transaction.commit();
    }

    private void setScoreboardFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, ScoreboardFragment.newInstance(), null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

//    private void setSettingsFragment() {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.content_frame, SettingsFragment.newInstance(), null);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }

    private void setTimerFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, TimerFragment.newInstance(), null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        mToolbar.setTitle(mTitle);
    }
}
