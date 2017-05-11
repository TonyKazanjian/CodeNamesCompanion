package com.tonykazanjian.codenamescompanion.start;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.WordCard;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class StartActivity extends AppCompatActivity implements StartActivityView {

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
    Button mStartBtn;

    StartActivityPresenter mStartActivityPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mWordInputLayout1 = (TextInputLayout)findViewById(R.id.word_input_layout_1);
        mWordInputEditText1 = (TextInputEditText)findViewById(R.id.word_input_1);
        mWordInputLayout2 = (TextInputLayout)findViewById(R.id.word_input_layout_2);
        mWordInputEditText2 = (TextInputEditText)findViewById(R.id.word_input_2);
        mWordInputLayout3 = (TextInputLayout)findViewById(R.id.word_input_layout_3);
        mWordInputEditText3 = (TextInputEditText)findViewById(R.id.word_input_3);
        mWordInputLayout4= (TextInputLayout)findViewById(R.id.word_input_layout_4);
        mWordInputEditText4 = (TextInputEditText)findViewById(R.id.word_input_4);
        mWordInputLayout5 = (TextInputLayout)findViewById(R.id.word_input_layout_5);
        mWordInputEditText5 = (TextInputEditText)findViewById(R.id.word_input_5);
        mWordInputLayout6 = (TextInputLayout)findViewById(R.id.word_input_layout_6);
        mWordInputEditText6 = (TextInputEditText)findViewById(R.id.word_input_6);
        mWordInputLayout7 = (TextInputLayout)findViewById(R.id.word_input_layout_7);
        mWordInputEditText7 = (TextInputEditText)findViewById(R.id.word_input_7);
        mWordInputLayout8 = (TextInputLayout)findViewById(R.id.word_input_layout_8);
        mWordInputEditText8 = (TextInputEditText)findViewById(R.id.word_input_8);
        mWordInputLayout9 = (TextInputLayout)findViewById(R.id.word_input_layout_9);
        mWordInputEditText9 = (TextInputEditText)findViewById(R.id.word_input_9);
        mStartBtn = (Button) findViewById(R.id.startBtn);

        mStartActivityPresenter = new StartActivityPresenter(new ArrayList<WordCard>(), this);

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartActivityPresenter.startGame();
            }
        });

    }

    @Override
    public void onStartBtnPressed() {

        List<String> strings = new ArrayList<>();
        strings.add(mWordInputEditText1.getText().toString());
        strings.add(mWordInputEditText2.getText().toString());
        strings.add(mWordInputEditText3.getText().toString());
        strings.add(mWordInputEditText4.getText().toString());
        strings.add(mWordInputEditText5.getText().toString());
        strings.add(mWordInputEditText6.getText().toString());
        strings.add(mWordInputEditText7.getText().toString());
        strings.add(mWordInputEditText8.getText().toString());
        strings.add(mWordInputEditText9.getText().toString());
        mStartActivityPresenter.setWordText(strings);
    }

    @Override
    public boolean isGameReady() {
        return false;
    }
}
