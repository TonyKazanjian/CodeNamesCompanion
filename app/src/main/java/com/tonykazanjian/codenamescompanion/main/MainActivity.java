package com.tonykazanjian.codenamescompanion.main;

import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tonykazanjian.codenamescompanion.R;

public class MainActivity extends AppCompatActivity {
    public static final int GAME_POSITION = 0;
    public static final int SCOREBOARD_POSITION = 1;
    public static final int SETTINGS_POSITION = 2;

    private Fragment mSelectedFragment;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private ListView mDrawerList;
    private String[] mFragmentTitles;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            mSelectedFragment = getSupportFragmentManager().getFragment(savedInstanceState, GameFragment.TAG);
        }

        mTitle = mDrawerTitle = getTitle();
        mFragmentTitles = getResources().getStringArray(R.array.drawer_title_array);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, mFragmentTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
        
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
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        MenuItem newGameItem = menu.findItem(R.id.action_new_game);
        MenuItem timerItem = menu.findItem(R.id.action_timer);
        if (newGameItem != null && timerItem != null) {
            menu.findItem(R.id.action_new_game).setVisible(!drawerOpen);
            menu.findItem(R.id.action_timer).setVisible(!drawerOpen);
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            selectItem(i);
            mDrawerLayout.closeDrawer(Gravity.START);
        }
    }

    private void selectItem(int i) {
        switch (i) {
            case GAME_POSITION:
                setGameFragment();
                break;
            case SCOREBOARD_POSITION:
                setScoreboardFragment();
                break;
            case SETTINGS_POSITION:
                setSettingsFragment();
                break;
        }

        mDrawerList.setItemChecked(i, true);
        setTitle(mFragmentTitles[i]);
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

    private void setSettingsFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, SettingsFragment.newInstance(), null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }


}
