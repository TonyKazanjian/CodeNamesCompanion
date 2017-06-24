package com.tonykazanjian.codenamescompanion.main;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.UserPreferences;
import com.tonykazanjian.codenamescompanion.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tony Kazanjian
 */

public class SettingsFragment extends Fragment implements SettingsView {

    Spinner mBaseTimeSpinner;
    RadioGroup mRadioGroup;

    SettingsPresenter mSettingsPresenter;

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
        mSettingsPresenter = new SettingsPresenter(this);

        mBaseTimeSpinner = (Spinner) rootview.findViewById(R.id.time_setting_spinner);
        mRadioGroup = (RadioGroup) rootview.findViewById(R.id.radioButtonGroup);
        setupTimeSpinner(mBaseTimeSpinner);
        mBaseTimeSpinner.setSelection(UserPreferences.getSpinnerPosition(getContext()));
        setRadioGroup();
    }

    private void setupTimeSpinner(Spinner timeSpinner) {
        final TypedArray timeValues = getResources().obtainTypedArray(R.array.base_time_array);
        final long[] timeMap = new long[timeValues.length()];
        for (int i = 0; i < timeMap.length; i++){
            final int r = timeValues.getResourceId(i, 0);
            if (r == R.string.fifty_seconds){
                timeMap[i] = Utils.TimeUtil.getFiftySeconds();
            } else if (r == R.string.seventyfive_seconds){
                timeMap[i] = Utils.TimeUtil.getSeventyFiveSeconds();
            } else if (r == R.string.ninety_seconds){
                timeMap[i] = Utils.TimeUtil.getNinetySeconds();
            } else if (r == R.string.two_minutes){
                timeMap[i] = Utils.TimeUtil.getTwoMinutes();
            } else if (r == R.string.three_minutes){
                timeMap[i] = Utils.TimeUtil.getThreeMinutes();
            }
        }
        timeValues.recycle();

        ArrayAdapter timeSpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.base_time_array));
        timeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeSpinnerAdapter);
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.getItemAtPosition(i);
                mSettingsPresenter.pickTime((int) timeMap[i]);
                UserPreferences.setSpinnerPosition(getContext(), i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setRadioGroup(){
        mRadioGroup.check(UserPreferences.getCheckedButton(getContext()));
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                UserPreferences.setCheckedButton(getContext(), i);
                switch (i){
                    case R.id.radioButtonEight:
//                        UserPreferences.setCardNumber(getContext(), 8);
                        break;
                    case R.id.radioButtonNine:
//                        UserPreferences.setCardNumber(getContext(), 9);
                        break;
                }
            }
        });
    }

    @Override
    public void onBaseTimePicked(int pickedTime) {
        UserPreferences.setBaseTime(getContext(), pickedTime);
    }

    @Override
    public void onCardNumberPicked(int pickedNumber) {

    }
}
