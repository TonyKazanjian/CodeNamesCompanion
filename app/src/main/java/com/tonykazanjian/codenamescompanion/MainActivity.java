package com.tonykazanjian.codenamescompanion;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    public void displayCards(List<?> cards) {
        cards = new ArrayList<>(Arrays.asList(words));
        mGridViewAdapter = new GridViewAdapter(this, cards, 3);
        mDynamicGridView.setAdapter(mGridViewAdapter);
    }

    @Override
    public void removeCard() {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        mDynamicGridView.startEditMode(i);

        return true;
    }
}
