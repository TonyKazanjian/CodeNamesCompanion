package com.tonykazanjian.codenamescompanion.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;

import com.tonykazanjian.codenamescompanion.LinearLayoutAbsListView;
import com.tonykazanjian.codenamescompanion.adapter.GridViewAdapter;
import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.adapter.ItemListAdapter;
import com.tonykazanjian.codenamescompanion.listeners.GridItemLongClickListener;
import com.tonykazanjian.codenamescompanion.listeners.ListItemLongClickListener;
import com.tonykazanjian.codenamescompanion.listeners.ListViewDragListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityView, WordInputDialog.WordInputListener {

    private MainActivityPresenter mMainActivityPresenter;
    private GridViewAdapter mGridViewAdapter;
    private GridView mGridView;

    ListView mListView1;
    ListView mListView2;
    ListView mListView3;
    ListView mListView4;
    ItemListAdapter mItemListAdapter1;
    ItemListAdapter mItemListAdapter2;
    ItemListAdapter mItemListAdapter3;
    ItemListAdapter mItemListAdapter4;
    LinearLayoutAbsListView mCodePanel1;
    LinearLayoutAbsListView mCodePanel2;
    LinearLayoutAbsListView mCodePanel3;
    LinearLayoutAbsListView mCodePanel4;
    LinearLayoutAbsListView mGridPanel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        init();
        WordInputDialog wordInputDialog =  WordInputDialog.newInstance();
        wordInputDialog.setCancelable(false);
        wordInputDialog.show(getSupportFragmentManager(), "TAG");
    }

    private void init() {
        mMainActivityPresenter = new MainActivityPresenter(this);

        mListView1 = (ListView)findViewById(R.id.listview1);
        mListView2 = (ListView)findViewById(R.id.listview2);
        mListView3 = (ListView)findViewById(R.id.listview3);
        mListView4 = (ListView)findViewById(R.id.listview4);
        mGridView = (GridView) findViewById(R.id.card_grid);

        mCodePanel1 = (LinearLayoutAbsListView) findViewById(R.id.code_panel1);
        mCodePanel1.setOnDragListener(new ListViewDragListener(mMainActivityPresenter));
        mCodePanel1.setAbsListView(mListView1);
        mCodePanel2 = (LinearLayoutAbsListView) findViewById(R.id.code_panel2);
        mCodePanel2.setOnDragListener(new ListViewDragListener(mMainActivityPresenter));
        mCodePanel2.setAbsListView(mListView2);
        mCodePanel3 = (LinearLayoutAbsListView) findViewById(R.id.code_panel3);
        mCodePanel3.setOnDragListener(new ListViewDragListener(mMainActivityPresenter));
        mCodePanel3.setAbsListView(mListView3);
        mCodePanel4 = (LinearLayoutAbsListView) findViewById(R.id.code_panel4);
        mCodePanel4.setOnDragListener(new ListViewDragListener(mMainActivityPresenter));
        mCodePanel4.setAbsListView(mListView4);
        mGridPanel = (LinearLayoutAbsListView) findViewById(R.id.grid_panel);
        mGridPanel.setOnDragListener(new ListViewDragListener(mMainActivityPresenter));
        mGridPanel.setAbsListView(mGridView);

        mGridView.setOnItemLongClickListener(new GridItemLongClickListener(mMainActivityPresenter));

        onWordListComplete(new ArrayList<WordCard>());

        mItemListAdapter1 = new ItemListAdapter(this, new ArrayList<WordCard>());
        mItemListAdapter2 = new ItemListAdapter(this, new ArrayList<WordCard>());
        mListView1.setAdapter(mItemListAdapter1);
        mListView1.setOnItemLongClickListener(new ListItemLongClickListener());
        mListView2.setAdapter(mItemListAdapter2);
        mListView2.setOnItemLongClickListener(new ListItemLongClickListener());
        mItemListAdapter3 = new ItemListAdapter(this, new ArrayList<WordCard>());
        mItemListAdapter4 = new ItemListAdapter(this, new ArrayList<WordCard>());
        mListView3.setAdapter(mItemListAdapter3);
        mListView3.setOnItemLongClickListener(new ListItemLongClickListener());
        mListView4.setAdapter(mItemListAdapter4);
        mListView4.setOnItemLongClickListener(new ListItemLongClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        mStopEditBtn = menu.findItem(R.id.stop_edit_btn);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return true;
    }

    @Override
    public void onCardsDisplayed(List<WordCard> cards) {
        mGridViewAdapter = new GridViewAdapter(this, cards);
        mGridView.setAdapter(mGridViewAdapter);
    }

    @Override
    public void onRemoveBtnClicked() {

    }

    @Override
    public void onWordListComplete(List<WordCard> wordCards) {
//        WordCard card1 = new WordCard();
//        card1.setWord("Dog");
//        wordCards.add(card1);
//        WordCard card2 = new WordCard();
//        card2.setWord("Cat");
//        wordCards.add(card2);
//        WordCard card3 = new WordCard();
//        card3.setWord("Monkey");
//        wordCards.add(card3);
        mMainActivityPresenter.showCards(wordCards); //creates and sets GridViewAdapter
    }
}
