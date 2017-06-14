package com.tonykazanjian.codenamescompanion.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.tonykazanjian.codenamescompanion.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class SettingsFragment extends Fragment implements SettingsView {

    Spinner mBaseTimeSpinner;
    CheckBox mEightCheckBox;
    CheckBox mNineCheckBox;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        init(rootView);

        return rootView;
    }

    private void init(View rootview) {
        mBaseTimeSpinner = (Spinner) rootview.findViewById(R.id.time_setting_spinner);
        mEightCheckBox = (CheckBox) rootview.findViewById(R.id.checkbox_eight);
        mNineCheckBox = (CheckBox) rootview.findViewById(R.id.checkbox_nine);
        setupTimeSpinner(mBaseTimeSpinner);
    }

    private void setupTimeSpinner(Spinner timeSpinner) {
        final List<Integer> baseTimeOptions = new ArrayList<>();
        int timeOptionsAmount = 5;
        for (int i = 0; i <= timeOptionsAmount; i++){
            baseTimeOptions.add(i);
        }
        ArrayAdapter timeSpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.base_time_array));
        timeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeSpinnerAdapter);
    }

    @Override
    public void onBaseTimePicked(List<Integer> timeList) {

    }

    @Override
    public void onCardNumberPicked() {

    }
}
