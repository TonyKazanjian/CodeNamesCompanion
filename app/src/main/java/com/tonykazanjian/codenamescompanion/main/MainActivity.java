package com.tonykazanjian.codenamescompanion.main;

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

    ListView mListView;
    ItemListAdapter mItemListAdapter;
    LinearLayoutAbsListView mCodePanel;
    LinearLayoutAbsListView mGridPanel;


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

        mListView = (ListView)findViewById(R.id.listview1);
        mDynamicGridView = (DynamicGridView) findViewById(R.id.card_grid);

        mCodePanel = (LinearLayoutAbsListView) findViewById(R.id.code_panel);
        mCodePanel.setOnDragListener(new ViewDragListener());
        mCodePanel.setAbsListView(mListView);
        mGridPanel = (LinearLayoutAbsListView) findViewById(R.id.grid_panel);
        mGridPanel.setOnDragListener(new ViewDragListener());
        mGridPanel.setAbsListView(mDynamicGridView);

        mMainActivityPresenter.showCards(new ArrayList<WordCard>()); //creates and sets GridViewAdapter
        mDynamicGridView.setOnItemLongClickListener(new GridItemLongClickListener(mMainActivityPresenter));

        mItemListAdapter = new ItemListAdapter(this, new ArrayList<WordCard>());
        mListView.setAdapter(mItemListAdapter);
        mListView.setOnItemLongClickListener(new ListItemLongClickListener());
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
        cards = new ArrayList<>();
        WordCard card1 = new WordCard();
        card1.setWord("Dog");
        cards.add(card1);
        WordCard card2 = new WordCard();
        card2.setWord("Cat");
        cards.add(card2);
        WordCard card3 = new WordCard();
        card3.setWord("Monkey");
        cards.add(card3);
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
