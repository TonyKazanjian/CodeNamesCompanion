package com.tonykazanjian.codenamescompanion.main;

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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class GameFragment extends Fragment implements GameView, WordInputDialog.WordInputListener{

    public static final String TAG = GameFragment.class.getName();

    private GamePresenter mGamePresenter;
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
    TextInputEditText mCodeInput1;
    TextInputEditText mCodeInput2;
    TextInputEditText mCodeInput3;
    TextInputEditText mCodeInput4;
    LinearLayout mGridEmptyStateLl;

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
            mPanel1List = (List<WordCard>) savedInstanceState.getSerializable(UserPreferences.CODE_PANEL_1_WORDS_KEY);
            mPanel2List = (List<WordCard>) savedInstanceState.getSerializable(UserPreferences.CODE_PANEL_2_WORDS_KEY);
            mPanel3List = (List<WordCard>) savedInstanceState.getSerializable(UserPreferences.CODE_PANEL_3_WORDS_KEY);
            mPanel4List = (List<WordCard>) savedInstanceState.getSerializable(UserPreferences.CODE_PANEL_4_WORDS_KEY);
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

    private void setListenersAndListViews() {
        LinearLayoutAbsListView[] linearLayoutAbsListViews = {mCodePanel1, mCodePanel2, mCodePanel3, mCodePanel4};
        ListView[] listViews = {mListView1, mListView2, mListView3, mListView4};
        for (int i = 0; i < linearLayoutAbsListViews.length; i++) {
            linearLayoutAbsListViews[i].setOnDragListener(new ViewDragListener(mGamePresenter));
            linearLayoutAbsListViews[i].setAbsListView(listViews[i]);
        }
    }

    private void setupGridView(){
        mGridPanel.setOnDragListener(new ViewDragListener(mGamePresenter));
        mGridPanel.setAbsListView(mGridView);
        mGridView.setOnItemLongClickListener(new GridItemLongClickListener(mGamePresenter));
    }

    private void init(View rootView) {
        mGamePresenter = new GamePresenter(this);

        mGridEmptyStateLl = (LinearLayout) rootView.findViewById(R.id.grid_empty_state_ll);

        mListView1 = (ListView) rootView.findViewById(R.id.listview1);
        mListView2 = (ListView) rootView.findViewById(R.id.listview2);
        mListView3 = (ListView) rootView.findViewById(R.id.listview3);
        mListView4 = (ListView) rootView.findViewById(R.id.listview4);
        mGridView = (GridView) rootView.findViewById(R.id.card_grid);

        mCodePanel1 = (LinearLayoutAbsListView) rootView.findViewById(R.id.code_panel1);
        mCodePanel2 = (LinearLayoutAbsListView) rootView.findViewById(R.id.code_panel2);
        mCodePanel3 = (LinearLayoutAbsListView) rootView.findViewById(R.id.code_panel3);
        mCodePanel4 = (LinearLayoutAbsListView) rootView.findViewById(R.id.code_panel4);
        mGridPanel = (LinearLayoutAbsListView) rootView.findViewById(R.id.grid_panel);

        setupGridView();
        setListenersAndListViews();

        if (mGridList != null) {
            onWordListComplete(mGridList);
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

        mItemListAdapter1 = new ItemListAdapter(getContext(), mPanel1List);
        mItemListAdapter2 = new ItemListAdapter(getContext(), mPanel2List);
        mItemListAdapter3 = new ItemListAdapter(getContext(), mPanel3List);
        mItemListAdapter4 = new ItemListAdapter(getContext(), mPanel4List);

        mListView1.setAdapter(mItemListAdapter1);
        mListView1.setOnItemLongClickListener(new ListItemLongClickListener());
        mListView2.setAdapter(mItemListAdapter2);
        mListView2.setOnItemLongClickListener(new ListItemLongClickListener());
        mListView3.setAdapter(mItemListAdapter3);
        mListView3.setOnItemLongClickListener(new ListItemLongClickListener());
        mListView4.setAdapter(mItemListAdapter4);
        mListView4.setOnItemLongClickListener(new ListItemLongClickListener());

        mCodeInput1 = (TextInputEditText) rootView.findViewById(R.id.code_input_1);
        mCodeInput2 = (TextInputEditText) rootView.findViewById(R.id.code_input_2);
        mCodeInput3 = (TextInputEditText) rootView.findViewById(R.id.code_input_3);
        mCodeInput4 = (TextInputEditText) rootView.findViewById(R.id.code_input_4);

        setKeyboardAndClickActions(mCodeInput1);
        setKeyboardAndClickActions(mCodeInput2);
        setKeyboardAndClickActions(mCodeInput3);
        setKeyboardAndClickActions(mCodeInput4);
    }

    private void setKeyboardAndClickActions(final TextInputEditText editText) {
        editText.setOnFocusChangeListener(new CodeInputListener());
        Utils.setKeyboardDoneAction(editText, new Utils.KeyboardInterface() {
            @Override
            public void keyboardDoneAction() {
                editText.setFocusable(false);
            }
        }, getContext());
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
        }
        return true;
    }

    @Override
    public void onGridCardsDisplayed(List<WordCard> cards) {
        mGridList = cards;
        mGridViewAdapter = new GridViewAdapter(getContext(), cards);
        mGridView.setAdapter(mGridViewAdapter);
    }


    /*** WordInputDialogListener callback ***/
    @Override
    public void onWordListComplete(List<WordCard> wordCards) {
        mGridList = wordCards;
        mGamePresenter.showGridCards(wordCards); //creates and sets GridViewAdapter
    }

    @Override
    public void showEmptyState() {
        mGridEmptyStateLl.setVisibility(View.VISIBLE);
        mGridPanel.setMinimumHeight(Utils.dp2Pixel(82, getContext()));
    }

    @Override
    public void removeEmptyState() {
        if (mGridEmptyStateLl.getVisibility() == View.VISIBLE) {
            mGridEmptyStateLl.setVisibility(View.GONE);
        }
    }

    public void initDialog() {
        clearEditTexts(new TextInputEditText[]{mCodeInput1, mCodeInput2, mCodeInput3, mCodeInput4});
        removeAllCards(new ItemBaseAdapter[]{mGridViewAdapter, mItemListAdapter1, mItemListAdapter2,
        mItemListAdapter3, mItemListAdapter4});
        WordInputDialog wordInputDialog =  WordInputDialog.newInstance();
        wordInputDialog.setCancelable(false);
        wordInputDialog.show(getChildFragmentManager(), "TAG");
    }

    public void removeAllCards(ItemBaseAdapter[] adapters){
        mGridEmptyStateLl.setVisibility(View.GONE);
        for (int i = 0; i < adapters.length-1; i++) {
            if (adapters[i] != null) {
                adapters[i].clearWordCards();
            }
        }
    }

    public void clearEditTexts(TextInputEditText[] textInputEditTexts){
        for (int i = 0; i < textInputEditTexts.length -1; i++){
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
