package com.tonykazanjian.codenamescompanion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.askerov.dynamicgrid.DynamicGridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private MainActivityPresenter mMainActivityPresenter;
    private GridViewAdapter mGridViewAdapter;
    private DynamicGridView mDynamicGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mMainActivityPresenter = new MainActivityPresenter(this);
        mDynamicGridView = new DynamicGridView(this);
        mMainActivityPresenter.showCards(new ArrayList<WordCard>());
    }

    @Override
    public void displayCards(List<WordCard> cards) {
        mGridViewAdapter = new GridViewAdapter(this, cards, 3);
        mDynamicGridView.setAdapter(mGridViewAdapter);
    }

    @Override
    public void enterTextOnCard(String text) {

    }

    @Override
    public void saveTextOnCard() {

    }

    @Override
    public void removeCard() {

    }
}
