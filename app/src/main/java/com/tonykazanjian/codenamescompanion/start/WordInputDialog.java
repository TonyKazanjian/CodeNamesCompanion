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

        AlertDialog dialog = createDialog();
        setupDialog(dialog);

        return dialog;
    }

    private AlertDialog createDialog() {
        return new AlertDialog.Builder(this.getActivity())
                .setView(mRootView)
                .setTitle("Put your words in!")
                .setPositiveButton("Start game", null)
                .create();
    }

    private void setupDialog(final AlertDialog dialog){
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mWordInputPresenter.startGame();
                    }
                });
            }
        });
    }

    @Override
    public void onStartBtnPressed() {

        List<String> strings = new ArrayList<>();
        TextInputEditText[] textInputEditTexts = {mWordInputEditText1, mWordInputEditText2,
                mWordInputEditText3, mWordInputEditText4, mWordInputEditText5, mWordInputEditText6,
                mWordInputEditText7, mWordInputEditText8, mWordInputEditText9};
        checkForText(textInputEditTexts, strings);

        mWordInputPresenter.setWordText(strings);
        if (mWordInputPresenter.isGameReady()) {
            mWordInputListener = (WordInputListener) getActivity();
            mWordInputListener.onWordListComplete(mWordInputPresenter.getWordCards());
            dismiss();

        } else {
            Toast.makeText(getContext(), "You must have at least 8 words to start the game.", Toast.LENGTH_SHORT).show();
            mWordInputPresenter.getWordCards().clear();
        }
    }

    private void checkForText(TextInputEditText[] textInputEditTexts, List<String> strings) {

        int i = 0;
        while (i <=8) {
            if (!TextUtils.isEmpty(textInputEditTexts[i].getText().toString())) {
                strings.add(textInputEditTexts[i].getText().toString());
            }
            i++;
        }
    }

    public interface WordInputListener {
        void onWordListComplete(List<WordCard> wordCards);
    }
}
