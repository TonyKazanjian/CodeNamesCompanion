package com.tonykazanjian.codenamescompanion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import org.askerov.dynamicgrid.DynamicGridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityView, AdapterView.OnItemLongClickListener{

    private MainActivityPresenter mMainActivityPresenter;
    private GridViewAdapter mGridViewAdapter;
    private DynamicGridView mDynamicGridView;
    private MenuItem mStopEditBtn;


    /** Data vars **/
    private String[] words = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mMainActivityPresenter = new MainActivityPresenter(this);
        mDynamicGridView = (DynamicGridView) findViewById(R.id.card_grid);
        mMainActivityPresenter.showCards(new ArrayList<WordCard>());
        mDynamicGridView.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        mStopEditBtn = menu.findItem(R.id.stop_edit_btn);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stop_edit_btn:
                mMainActivityPresenter.turnOffEditMode();
        }
        return true;
    }

    @Override
    public void onCardsDisplayed(List<?> cards) {
        cards = new ArrayList<>(Arrays.asList(words));
        mGridViewAdapter = new GridViewAdapter(this, cards, 3);
        mDynamicGridView.setAdapter(mGridViewAdapter);
    }

    @Override
    public void onEditModeInit(int item) {
        mDynamicGridView.startEditMode(item);
        mStopEditBtn.setVisible(true);
    }

    @Override
    public void onEditStopItemClicked() {
        mDynamicGridView.stopEditMode();
        mStopEditBtn.setVisible(false);
    }

    @Override
    public void onRemoveBtnClicked() {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        mMainActivityPresenter.editCards(i);
        return true;
    }
}
