package com.tonykazanjian.codenamescompanion.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.start.StartActivityView;

/**
 * @author Tony Kazanjian
 */

public class WordInputDialog extends DialogFragment implements StartActivityView{
    
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

        AlertDialog dialog = createDialog();

        return dialog;
    }

    private AlertDialog createDialog() {
        return new AlertDialog.Builder(this.getActivity())
                .setView(mRootView)
                .setTitle("Put your words in!")
                .setPositiveButton("Start game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onStartBtnPressed();
                    }
                })
                .create();
    }

    @Override
    public void onStartBtnPressed() {
        
    }

    @Override
    public boolean isGameReady() {
        return false;
    }
}
