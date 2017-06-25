package com.tonykazanjian.codenamescompanion.start;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.UserPreferences;
import com.tonykazanjian.codenamescompanion.WordCard;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class WordInputDialog extends DialogFragment implements WordInputView {
    
    private View mRootView;
    TextInputLayout mWordInputLayout1;
    TextInputEditText mWordInputEditText1;
    TextInputLayout mWordInputLayout2;
    TextInputEditText mWordInputEditText2;
    TextInputLayout mWordInputLayout3;
    TextInputEditText mWordInputEditText3;
    TextInputLayout mWordInputLayout4;
    TextInputEditText mWordInputEditText4;
    TextInputLayout mWordInputLayout5;
    TextInputEditText mWordInputEditText5;
    TextInputLayout mWordInputLayout6;
    TextInputEditText mWordInputEditText6;
    TextInputLayout mWordInputLayout7;
    TextInputEditText mWordInputEditText7;
    TextInputLayout mWordInputLayout8;
    TextInputEditText mWordInputEditText8;
    TextInputLayout mWordInputLayout9;
    TextInputEditText mWordInputEditText9;

    WordInputPresenter mWordInputPresenter;
    WordInputListener mWordInputListener;

    public WordInputDialog() {
    }

    public static WordInputDialog newInstance() {
        return new WordInputDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(this.getContext()).inflate(R.layout.dialog_word_input, null);
        mWordInputLayout1 = (TextInputLayout)mRootView.findViewById(R.id.word_input_layout_1);
        mWordInputEditText1 = (TextInputEditText)mRootView.findViewById(R.id.word_input_1);
        mWordInputLayout2 = (TextInputLayout)mRootView.findViewById(R.id.word_input_layout_2);
        mWordInputEditText2 = (TextInputEditText)mRootView.findViewById(R.id.word_input_2);
        mWordInputLayout3 = (TextInputLayout)mRootView.findViewById(R.id.word_input_layout_3);
        mWordInputEditText3 = (TextInputEditText)mRootView.findViewById(R.id.word_input_3);
        mWordInputLayout4= (TextInputLayout)mRootView.findViewById(R.id.word_input_layout_4);
        mWordInputEditText4 = (TextInputEditText)mRootView.findViewById(R.id.word_input_4);
        mWordInputLayout5 = (TextInputLayout)mRootView.findViewById(R.id.word_input_layout_5);
        mWordInputEditText5 = (TextInputEditText)mRootView.findViewById(R.id.word_input_5);
        mWordInputLayout6 = (TextInputLayout)mRootView.findViewById(R.id.word_input_layout_6);
        mWordInputEditText6 = (TextInputEditText)mRootView.findViewById(R.id.word_input_6);
        mWordInputLayout7 = (TextInputLayout)mRootView.findViewById(R.id.word_input_layout_7);
        mWordInputEditText7 = (TextInputEditText)mRootView.findViewById(R.id.word_input_7);
        mWordInputLayout8 = (TextInputLayout)mRootView.findViewById(R.id.word_input_layout_8);
        mWordInputEditText8 = (TextInputEditText)mRootView.findViewById(R.id.word_input_8);
        mWordInputLayout9 = (TextInputLayout)mRootView.findViewById(R.id.word_input_layout_9);
        mWordInputEditText9 = (TextInputEditText)mRootView.findViewById(R.id.word_input_9);

        mWordInputPresenter = new WordInputPresenter(new ArrayList<WordCard>(), this);
        mWordInputPresenter.getWordAmountPrefs();

        AlertDialog dialog = createDialog();
        setupDialog(dialog);

        return dialog;
    }

    private AlertDialog createDialog() {
        return new AlertDialog.Builder(this.getActivity())
                .setView(mRootView)
                .setTitle("Put your words in!")
                .setPositiveButton("Start game", null)
                .setNegativeButton("Cancel", null)
                .setCancelable(true)
                .create();
    }

    private void setupDialog(final AlertDialog dialog){
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button start = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button cancel = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mWordInputPresenter.startGame();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });
    }

    @Override
    public void onStartBtnPressed() {
        List<String> strings = new ArrayList<>();
        addTextToWordList(getTextInputEditTextList(), strings);

        mWordInputPresenter.setWordText(strings);
        int cardNumber = UserPreferences.getCardNumber(getContext());
        if (mWordInputPresenter.isGameReady(cardNumber)) {
            mWordInputListener = (WordInputListener) getParentFragment();
            mWordInputListener.onWordListComplete(mWordInputPresenter.getWordCards());
            dismiss();
        } else {
            String errorString = getString(R.string.word_input_error, UserPreferences.getCardNumber(getContext()));
            Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
            mWordInputPresenter.getWordCards().clear();
        }
    }

    @Override
    public int onWordAmountSet() {
        int wordAmount = UserPreferences.getCardNumber(getContext());
        if (wordAmount < 9) {
            mWordInputLayout5.setVisibility(View.GONE);
        }
        return wordAmount;
    }

    private List<TextInputEditText> getTextInputEditTextList() {
        List<TextInputEditText> textInputEditTextList = new ArrayList<>();
        textInputEditTextList.add(mWordInputEditText1);
        textInputEditTextList.add(mWordInputEditText2);
        textInputEditTextList.add(mWordInputEditText3);
        textInputEditTextList.add(mWordInputEditText4);
        textInputEditTextList.add(mWordInputEditText5);
        textInputEditTextList.add(mWordInputEditText6);
        textInputEditTextList.add(mWordInputEditText7);
        textInputEditTextList.add(mWordInputEditText8);
        textInputEditTextList.add(mWordInputEditText9);
        return textInputEditTextList;
    }

    private void addTextToWordList(List<TextInputEditText> textInputEditTexts, List<String> strings) {
        int wordAmount = UserPreferences.getCardNumber(getContext());
        if (wordAmount == 8) {
            textInputEditTexts.remove(mWordInputEditText5);
        }

        for (int i = 0; i < textInputEditTexts.size(); i++){
            if (!TextUtils.isEmpty(textInputEditTexts.get(i).getText().toString())) {
                strings.add(textInputEditTexts.get(i).getText().toString());
            }
        }
    }

    public interface WordInputListener {
        void onWordListComplete(List<WordCard> wordCards);
    }
}
