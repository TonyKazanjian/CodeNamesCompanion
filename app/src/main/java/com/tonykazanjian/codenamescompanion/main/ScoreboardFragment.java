package com.tonykazanjian.codenamescompanion.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.UserPreferences;

/**
 * @author Tony Kazanjian
 */

public class ScoreboardFragment extends Fragment {

    private NumberPicker mBlueTeamPicker;
    private NumberPicker mRedTeamPicker;

    private int bluePoints;
    private int redPoints;

    public static ScoreboardFragment newInstance() {
        return new ScoreboardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(false);

        View rootView = inflater.inflate(R.layout.fragment_scoreboard, container, false);
        mBlueTeamPicker = (NumberPicker)rootView.findViewById(R.id.blue_score);
        mRedTeamPicker = (NumberPicker)rootView.findViewById(R.id.red_score);
        Button clearScoresBtn = (Button) rootView.findViewById(R.id.clear_btn);

        initPickers();
        setPickerValue();

        mBlueTeamPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                bluePoints = i1;
                UserPreferences.setBlueScore(getContext(), bluePoints);
            }
        });
        mRedTeamPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                redPoints = i1;
                UserPreferences.setRedScore(getContext(), redPoints);
            }
        });

        clearScoresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBlueTeamPicker.setValue(0);
                UserPreferences.setBlueScore(getContext(), 0);
                mRedTeamPicker.setValue(0);
                UserPreferences.setRedScore(getContext(), 0);
            }
        });
        return rootView;
    }

    private void initPickers() {
        mBlueTeamPicker.setMinValue(0);
        mBlueTeamPicker.setMaxValue(100);
        mRedTeamPicker.setMinValue(0);
        mRedTeamPicker.setMaxValue(100);

        mBlueTeamPicker.setValue(UserPreferences.getBlueScore(getContext()));
        mRedTeamPicker.setValue(UserPreferences.getRedScore(getContext()));

        mBlueTeamPicker.setWrapSelectorWheel(true);
        mRedTeamPicker.setWrapSelectorWheel(true);
    }

    private void setPickerValue(){
        mBlueTeamPicker.setValue(UserPreferences.getBlueScore(getContext()));
        mRedTeamPicker.setValue(UserPreferences.getRedScore(getContext()));
    }
}
