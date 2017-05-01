package com.tonykazanjian.codenamescompanion;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import org.askerov.dynamicgrid.DynamicGridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private MainActivityPresenter mMainActivityPresenter;
    private GridViewAdapter mGridViewAdapter;
    private DynamicGridView mDynamicGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        init();

//        //Active dragging mode when long click at each Grid view item
//        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
//                gridView.startEditMode(position);
//
//                return true;
//            }
//        });

//        //Handling click event of each Grid view item
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View view, int position, long id) {
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Item information")
//                        .setMessage("You clicked at position: " + position +"\n"
//                                + "The letter is: " + parent.getItemAtPosition(position).toString())
//                        .setPositiveButton(android.R.string.yes, null)
//
//                        .setIcon(android.R.drawable.ic_dialog_info)
//                        .show();
//            }
//        });
    }

//    @Override
//    public void onBackPressed() {
//        if (gridView.isEditMode()) {
//            gridView.stopEditMode();
//        } else {
//            super.onBackPressed();
//        }
//    }

    private void init() {
        mMainActivityPresenter = new MainActivityPresenter(this);
        mDynamicGridView = (DynamicGridView) findViewById(R.id.card_grid);
        mMainActivityPresenter.showCards(new ArrayList<WordCard>());
    }

    @Override
    public void displayCards(List<WordCard> cards) {
        mGridViewAdapter = new GridViewAdapter(this, cards, 2);
        mDynamicGridView.setAdapter(mGridViewAdapter);
    }

    @Override
    public void saveTextOnCard(String text) {

    }

    @Override
    public void removeCard() {

    }
}
