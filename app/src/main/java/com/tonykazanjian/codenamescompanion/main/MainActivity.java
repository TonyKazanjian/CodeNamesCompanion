package com.tonykazanjian.codenamescompanion.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.tonykazanjian.codenamescompanion.LinearLayoutAbsListView;
import com.tonykazanjian.codenamescompanion.adapter.GridViewAdapter;
import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.adapter.ItemListAdapter;
import com.tonykazanjian.codenamescompanion.listeners.GridItemLongClickListener;
import com.tonykazanjian.codenamescompanion.listeners.ListItemLongClickListener;
import com.tonykazanjian.codenamescompanion.listeners.ViewDragListener;

import org.askerov.dynamicgrid.DynamicGridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private MainActivityPresenter mMainActivityPresenter;
    private GridViewAdapter mGridViewAdapter;
    private DynamicGridView mDynamicGridView;
    private MenuItem mStopEditBtn;

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


    public static Intent newIntent(Context context, ArrayList<WordCard> wordCards) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putParcelableArrayListExtra(WordCard.EXTRA_WORD_CARD_LIST, wordCards);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mMainActivityPresenter = new MainActivityPresenter(this);

        mListView1 = (ListView)findViewById(R.id.listview1);
        mListView2 = (ListView)findViewById(R.id.listview2);
        mListView3 = (ListView)findViewById(R.id.listview3);
        mListView4 = (ListView)findViewById(R.id.listview4);
        mDynamicGridView = (DynamicGridView) findViewById(R.id.card_grid);

        mCodePanel1 = (LinearLayoutAbsListView) findViewById(R.id.code_panel1);
        mCodePanel1.setOnDragListener(new ViewDragListener());
        mCodePanel1.setAbsListView(mListView1);
        mCodePanel2 = (LinearLayoutAbsListView) findViewById(R.id.code_panel2);
        mCodePanel2.setOnDragListener(new ViewDragListener());
        mCodePanel2.setAbsListView(mListView2);
        mCodePanel3 = (LinearLayoutAbsListView) findViewById(R.id.code_panel3);
        mCodePanel3.setOnDragListener(new ViewDragListener());
        mCodePanel3.setAbsListView(mListView3);
        mCodePanel4 = (LinearLayoutAbsListView) findViewById(R.id.code_panel4);
        mCodePanel4.setOnDragListener(new ViewDragListener());
        mCodePanel4.setAbsListView(mListView4);
        mGridPanel = (LinearLayoutAbsListView) findViewById(R.id.grid_panel);
        mGridPanel.setOnDragListener(new ViewDragListener());
        mGridPanel.setAbsListView(mDynamicGridView);

        mMainActivityPresenter.showCards(new ArrayList<WordCard>()); //creates and sets GridViewAdapter
        mDynamicGridView.setOnItemLongClickListener(new GridItemLongClickListener(mMainActivityPresenter));

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
    public void onCardsDisplayed(List<WordCard> cards) {
        cards = getIntent().getParcelableArrayListExtra(WordCard.EXTRA_WORD_CARD_LIST);
//        WordCard card1 = new WordCard();
//        card1.setWord("Dog");
//        cards.add(card1);
//        WordCard card2 = new WordCard();
//        card2.setWord("Cat");
//        cards.add(card2);
//        WordCard card3 = new WordCard();
//        card3.setWord("Monkey");
//        cards.add(card3);
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
}
