package com.tonykazanjian.codenamescompanion.main;

import android.content.SharedPreferences;
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
import com.tonykazanjian.codenamescompanion.adapter.ItemListAdapter;
import com.tonykazanjian.codenamescompanion.listeners.GridItemLongClickListener;
import com.tonykazanjian.codenamescompanion.listeners.ListItemLongClickListener;
import com.tonykazanjian.codenamescompanion.listeners.ViewDragListener;
import com.tonykazanjian.codenamescompanion.start.WordInputDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class GameFragment extends Fragment implements GameView, WordInputDialog.WordInputListener{

    public static String CODE_WORD_KEY_1 = "code_word_key_1";
    public static String CODE_WORD_KEY_2 = "code_word_key_2";
    public static String CODE_WORD_KEY_3 = "code_word_key_3";
    public static String CODE_WORD_KEY_4 = "code_word_key_4";

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
    TextInputLayout mCodeInputLayout1;
    TextInputLayout mCodeInputLayout2;
    LinearLayout mGridEmptyStateLl;

    MenuItem mNewGameItem;

    Bundle mBundle = new Bundle();

    List<WordCard> mWordCards = new ArrayList<>();

    public static GameFragment newInstance(){
//        GameFragment gameFragment = new GameFragment();
//        Bundle args = new Bundle();
//        outState.putString(CODE_WORD_KEY_1, mCodeInput1.getText().toString());
//        outState.putString(CODE_WORD_KEY_2, mCodeInput2.getText().toString());
//        outState.putString(CODE_WORD_KEY_3, mCodeInput3.getText().toString());
//        outState.putString(CODE_WORD_KEY_4, mCodeInput4.getText().toString());
        return new GameFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mBundle = savedInstanceState;
        } else {
            mBundle = new Bundle();
        }
//        if (savedInstanceState == null || savedInstanceState.isEmpty()) {
//            initDialog();
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        init(rootView);

        if (UserPreferences.getGridWordList(mBundle) == null){
            initDialog();
        } else {
            onWordListComplete(UserPreferences.getGridWordList(mBundle));
        }

        return rootView;
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
////        mBundle.putParcelableArrayList(UserPreferences.SRC_WORD_CARDS_KEY, (ArrayList<? extends Parcelable>) mWordCards);
//    }


    @Override
    public void onResume() {
        super.onResume();
        setRetainInstance(true);
    }

    private void init(View rootView) {
        mGamePresenter = new GamePresenter(this);

        mListView1 = (ListView) rootView.findViewById(R.id.listview1);
        mListView2 = (ListView) rootView.findViewById(R.id.listview2);
        mListView3 = (ListView) rootView.findViewById(R.id.listview3);
        mListView4 = (ListView) rootView.findViewById(R.id.listview4);
        mGridView = (GridView) rootView.findViewById(R.id.card_grid);

        mGridEmptyStateLl = (LinearLayout) rootView.findViewById(R.id.grid_empty_state_ll);

        mCodePanel1 = (LinearLayoutAbsListView) rootView.findViewById(R.id.code_panel1);
        mCodePanel1.setOnDragListener(new ViewDragListener(mGamePresenter));
        mCodePanel1.setAbsListView(mListView1);
        mCodePanel2 = (LinearLayoutAbsListView) rootView.findViewById(R.id.code_panel2);
        mCodePanel2.setOnDragListener(new ViewDragListener(mGamePresenter));
        mCodePanel2.setAbsListView(mListView2);
        mCodePanel3 = (LinearLayoutAbsListView) rootView.findViewById(R.id.code_panel3);
        mCodePanel3.setOnDragListener(new ViewDragListener(mGamePresenter));
        mCodePanel3.setAbsListView(mListView3);
        mCodePanel4 = (LinearLayoutAbsListView) rootView.findViewById(R.id.code_panel4);
        mCodePanel4.setOnDragListener(new ViewDragListener(mGamePresenter));
        mCodePanel4.setAbsListView(mListView4);
        mGridPanel = (LinearLayoutAbsListView) rootView.findViewById(R.id.grid_panel);
        mGridPanel.setOnDragListener(new ViewDragListener(mGamePresenter));
        mGridPanel.setAbsListView(mGridView);

        mGridView.setOnItemLongClickListener(new GridItemLongClickListener(mGamePresenter));

//        onWordListComplete(new ArrayList<WordCard>());

        mItemListAdapter1 = new ItemListAdapter(getContext(), new ArrayList<WordCard>());
        mItemListAdapter2 = new ItemListAdapter(getContext(), new ArrayList<WordCard>());
        mListView1.setAdapter(mItemListAdapter1);
        mListView1.setOnItemLongClickListener(new ListItemLongClickListener());
        mListView2.setAdapter(mItemListAdapter2);
        mListView2.setOnItemLongClickListener(new ListItemLongClickListener());
        mItemListAdapter3 = new ItemListAdapter(getContext(), new ArrayList<WordCard>());
        mItemListAdapter4 = new ItemListAdapter(getContext(), new ArrayList<WordCard>());
        mListView3.setAdapter(mItemListAdapter3);
        mListView3.setOnItemLongClickListener(new ListItemLongClickListener());
        mListView4.setAdapter(mItemListAdapter4);
        mListView4.setOnItemLongClickListener(new ListItemLongClickListener());

        mCodeInput1 = (TextInputEditText) rootView.findViewById(R.id.code_input_1);
        mCodeInput2 = (TextInputEditText) rootView.findViewById(R.id.code_input_2);
        mCodeInput3 = (TextInputEditText) rootView.findViewById(R.id.code_input_3);
        mCodeInput4 = (TextInputEditText) rootView.findViewById(R.id.code_input_4);

        mCodeInput1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setFocusableInTouchMode(true);
            }
        });

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's state here
//            mCodeInput1.setText(savedInstanceState.getString(CODE_WORD_KEY_1));
//            mCodeInput2.setText(savedInstanceState.getString(CODE_WORD_KEY_2));
//            mCodeInput3.setText(savedInstanceState.getString(CODE_WORD_KEY_3));
//            mCodeInput4.setText(savedInstanceState.getString(CODE_WORD_KEY_4));
        }
    }

//    @Override
//    public void onDestroy() {
//
//        super.onDestroy();
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList(UserPreferences.SRC_WORD_CARDS_KEY, (ArrayList<? extends Parcelable>) mWordCards);
//        outState.putString(CODE_WORD_KEY_1, mCodeInput1.getText().toString());
//        outState.putString(CODE_WORD_KEY_2, mCodeInput2.getText().toString());
//        outState.putString(CODE_WORD_KEY_3, mCodeInput3.getText().toString());
//        outState.putString(CODE_WORD_KEY_4, mCodeInput4.getText().toString());

        //Save the fragment's state here
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
    public void onCardsDisplayed(List<WordCard> cards) {
        cards = UserPreferences.getGridWordList(mBundle);
        mGridViewAdapter = new GridViewAdapter(getContext(), cards);
        mGridView.setAdapter(mGridViewAdapter);
    }

    @Override
    public void onWordListComplete(List<WordCard> wordCards) {
        UserPreferences.setGridWordList(mBundle, wordCards);
        mGamePresenter.showCards(wordCards); //creates and sets GridViewAdapter
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
        clearEditTexts();
        removeAllCards();
        WordInputDialog wordInputDialog =  WordInputDialog.newInstance();
        wordInputDialog.setCancelable(false);
        wordInputDialog.show(getChildFragmentManager(), "TAG");
    }

    public void removeAllCards(){
        mGridEmptyStateLl.setVisibility(View.GONE);
        mGridViewAdapter.clearWordCards();
        mItemListAdapter1.clearWordCards();
        mItemListAdapter2.clearWordCards();
        mItemListAdapter3.clearWordCards();
        mItemListAdapter4.clearWordCards();
    }

    public void clearEditTexts(){
        mCodeInput1.getText().clear();
        mCodeInput2.getText().clear();
        mCodeInput3.getText().clear();
        mCodeInput4.getText().clear();
    }

    private class CodeInputListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean b) {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
        }
    }
}
