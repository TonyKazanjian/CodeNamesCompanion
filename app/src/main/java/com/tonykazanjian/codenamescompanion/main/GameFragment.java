package com.tonykazanjian.codenamescompanion.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tonykazanjian.codenamescompanion.LinearLayoutAbsListView;
import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.UserPreferences;
import com.tonykazanjian.codenamescompanion.Utils;
import com.tonykazanjian.codenamescompanion.WordCard;
import com.tonykazanjian.codenamescompanion.adapter.GridViewAdapter;
import com.tonykazanjian.codenamescompanion.adapter.ItemBaseAdapter;
import com.tonykazanjian.codenamescompanion.adapter.ItemListAdapter;
import com.tonykazanjian.codenamescompanion.listeners.GridItemLongClickListener;
import com.tonykazanjian.codenamescompanion.listeners.ListItemLongClickListener;
import com.tonykazanjian.codenamescompanion.listeners.ViewDragListener;
import com.tonykazanjian.codenamescompanion.start.WordInputDialog;
import com.tonykazanjian.codenamescompanion.timer.TimerActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class GameFragment extends Fragment implements GameView, WordInputDialog.WordInputListener {

    public static final String TAG = GameFragment.class.getName();

    private GamePresenter mGamePresenter;
    private GridViewAdapter mGridViewAdapter;
    private GridView mGridView;

    ListView mListView1;
    ListView mListView2;
    ListView mListView3;
    ListView mListView4;
    ListView mEmptyView;
    ItemListAdapter mItemListAdapter1;
    ItemListAdapter mItemListAdapter2;
    ItemListAdapter mItemListAdapter3;
    ItemListAdapter mItemListAdapter4;
    LinearLayoutAbsListView mCodePanel1;
    LinearLayoutAbsListView mCodePanel2;
    LinearLayoutAbsListView mCodePanel3;
    LinearLayoutAbsListView mCodePanel4;
    LinearLayoutAbsListView mGridPanel;
    TextInputEditText mCodeInput1;
    TextInputEditText mCodeInput2;
    TextInputEditText mCodeInput3;
    TextInputEditText mCodeInput4;
    TextView mEmptyTextView;

    TextInputEditText[] mTextInputEditTexts;

    MenuItem mNewGameItem;

    List<WordCard> mGridList;
    List<WordCard> mPanel1List;
    List<WordCard> mPanel2List;
    List<WordCard> mPanel3List;
    List<WordCard> mPanel4List;

    public static GameFragment newInstance(){
        return new GameFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's state here
            mGridList = (List<WordCard>) savedInstanceState.getSerializable(UserPreferences.GRID_WORDS_KEY);
        }

        if (mGridList == null) {
            initDialog();
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        init(rootView);

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        setRetainInstance(true);
        if (mGridViewAdapter != null && mGridViewAdapter.getWordCards().size() == 0) {
            showEmptyState();
        }
    }

    private void init(View rootView) {
        mGamePresenter = new GamePresenter(this);

        mListView1 = (ListView) rootView.findViewById(R.id.listview1);
        mListView2 = (ListView) rootView.findViewById(R.id.listview2);
        mListView3 = (ListView) rootView.findViewById(R.id.listview3);
        mListView4 = (ListView) rootView.findViewById(R.id.listview4);
        mGridView = (GridView) rootView.findViewById(R.id.card_grid);

//        mGridEmptyStateLl = (LinearLayoutAbsListView) rootView.findViewById(R.id.grid_empty_state_ll);
        mEmptyTextView = (TextView) rootView.findViewById(R.id.empty_textview);

        mCodePanel1 = (LinearLayoutAbsListView) rootView.findViewById(R.id.code_panel1);
        mCodePanel2 = (LinearLayoutAbsListView) rootView.findViewById(R.id.code_panel2);
        mCodePanel3 = (LinearLayoutAbsListView) rootView.findViewById(R.id.code_panel3);
        mCodePanel4 = (LinearLayoutAbsListView) rootView.findViewById(R.id.code_panel4);
        mGridPanel = (LinearLayoutAbsListView) rootView.findViewById(R.id.grid_panel);

//        setupGridView(mGridPanel, mGridEmptyStateLl, mGamePresenter, mGridView);
        setupGridView(mGridPanel, mGamePresenter, mGridView);

        setListenersAndListViews(new LinearLayoutAbsListView[]{mCodePanel1, mCodePanel2, mCodePanel3, mCodePanel4},
                mGamePresenter, new ListView[]{mListView1, mListView2, mListView3, mListView4});

        if (mGridList != null) {
            mGamePresenter.showGridCards(mGridList);
        }

        if (mPanel1List == null) {
            mPanel1List = new ArrayList<>();
        }

        if (mPanel2List == null) {
            mPanel2List = new ArrayList<>();
        }

        if (mPanel3List == null) {
            mPanel3List = new ArrayList<>();
        }

        if (mPanel4List == null) {
            mPanel4List = new ArrayList<>();
        }

        mItemListAdapter1 = new ItemListAdapter(getContext(), mPanel1List, this);
        mItemListAdapter2 = new ItemListAdapter(getContext(), mPanel2List, this);
        mItemListAdapter3 = new ItemListAdapter(getContext(), mPanel3List, this);
        mItemListAdapter4 = new ItemListAdapter(getContext(), mPanel4List, this);

        setListAdaptersAndListeners(new ListView[]{mListView1, mListView2, mListView3, mListView4},
                new ItemListAdapter[]{mItemListAdapter1, mItemListAdapter2, mItemListAdapter3, mItemListAdapter4});

        mCodeInput1 = (TextInputEditText) rootView.findViewById(R.id.code_input_1);
        mCodeInput2 = (TextInputEditText) rootView.findViewById(R.id.code_input_2);
        mCodeInput3 = (TextInputEditText) rootView.findViewById(R.id.code_input_3);
        mCodeInput4 = (TextInputEditText) rootView.findViewById(R.id.code_input_4);

        setTextInputAndKeyboardInteraction(mTextInputEditTexts = new TextInputEditText[]
                {mCodeInput1, mCodeInput2, mCodeInput3, mCodeInput4});
    }

    private void setListAdaptersAndListeners(ListView[] listViews, ItemListAdapter[] listAdapters){
        for (int i = 0; i < listViews.length; i++) {
            listViews[i].setAdapter(listAdapters[i]);
            listViews[i].setOnItemLongClickListener(new ListItemLongClickListener());
        }
    }

    private void setListenersAndListViews(LinearLayoutAbsListView[] linearLayoutAbsListViews, GamePresenter gamePresenter,
                                          ListView[] listViews) {
        for (int i = 0; i < linearLayoutAbsListViews.length; i++) {
            linearLayoutAbsListViews[i].setOnDragListener(new ViewDragListener(gamePresenter, this));
            linearLayoutAbsListViews[i].setAbsListView(listViews[i]);
        }
    }

    private void setupGridView(LinearLayoutAbsListView gridPanel, GamePresenter gamePresenter, GridView gridView){
        gridPanel.setOnDragListener(new ViewDragListener(gamePresenter, this));
        gridPanel.setAbsListView(gridView);
        gridView.setOnItemLongClickListener(new GridItemLongClickListener(gamePresenter));
    }

    private void setTextInputAndKeyboardInteraction(TextInputEditText[] textInputEditTexts) {

        for (final TextInputEditText editText : textInputEditTexts){
            editText.setFocusable(false);
            editText.setOnFocusChangeListener(new CodeInputListener());
            Utils.setKeyboardDoneAction(editText, new Utils.KeyboardInterface() {
                @Override
                public void keyboardDoneAction() {
                    editText.setFocusable(false);
                }
            }, getContext());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(UserPreferences.GRID_WORDS_KEY, (Serializable) mGridList);
        outState.putSerializable(UserPreferences.CODE_PANEL_1_WORDS_KEY, (Serializable) mPanel1List);
        outState.putSerializable(UserPreferences.CODE_PANEL_2_WORDS_KEY, (Serializable) mPanel2List);
        outState.putSerializable(UserPreferences.CODE_PANEL_3_WORDS_KEY, (Serializable) mPanel3List);
        outState.putSerializable(UserPreferences.CODE_PANEL_4_WORDS_KEY, (Serializable) mPanel4List);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        mNewGameItem = menu.findItem(R.id.action_new_game);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_game:
                initDialog();
                break;
            case R.id.action_timer:
                startActivity(new Intent(getContext(), TimerActivity.class));
        }
        return true;
    }

    @Override
    public void onGridCardsDisplayed(List<WordCard> cards) {
        mGridList = cards;
        mGridViewAdapter = new GridViewAdapter(getContext(), cards, this);
        mGridView.setAdapter(mGridViewAdapter);
    }


    /*** WordInputDialogListener callback ***/
    @Override
    public void onWordListComplete(List<WordCard> wordCards) {
        mGridList = wordCards;
        clearEditTexts(mTextInputEditTexts);
        removeAllCards(new ItemBaseAdapter[]{mGridViewAdapter, mItemListAdapter1, mItemListAdapter2,
                mItemListAdapter3, mItemListAdapter4});
        mGamePresenter.showGridCards(wordCards); //creates and sets GridViewAdapter
    }

    @Override
    public void showEmptyState() {
        if (mEmptyTextView.getVisibility() == View.INVISIBLE) {
            mEmptyTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void removeEmptyState() {
        if (mEmptyTextView.getVisibility() == View.VISIBLE) {
            mEmptyTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onViewBGChanged(AbsListView newParent, boolean isEntered) {
        if (isEntered) {
            newParent.setBackgroundColor(getResources().getColor(R.color.activated_grey));
        } else {
            newParent.setBackgroundColor(getResources().getColor(android.R.color.background_light));
        }
    }

    @Override
    public void onDragStarted(boolean textInputHasFocus) {
        for (TextInputEditText editText : mTextInputEditTexts) {
            editText.setFocusable(textInputHasFocus);
            editText.setFocusableInTouchMode(textInputHasFocus);
        }
    }

    public void initDialog() {
        WordInputDialog wordInputDialog =  WordInputDialog.newInstance();
        wordInputDialog.setCancelable(false);
        wordInputDialog.show(getChildFragmentManager(), "TAG");
    }

    public void removeAllCards(ItemBaseAdapter[] adapters){

        for (int i = 0; i < adapters.length; i++) {
            if (adapters[i] != null) {
                adapters[i].clearWordCards();
                adapters[i].notifyDataSetChanged();
            }
        }
    }

    public void clearEditTexts(TextInputEditText[] textInputEditTexts){
        for (int i = 0; i < textInputEditTexts.length; i++){
            if (textInputEditTexts[i] != null){
                textInputEditTexts[i].getText().clear();
            }
        }
    }

    private class CodeInputListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean b) {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
        }
    }
}
