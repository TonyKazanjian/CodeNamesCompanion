package com.tonykazanjian.codenamescompanion.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tonykazanjian.codenamescompanion.R;

/**
 * @author Tony Kazanjian
 */

public class ScoreboardFragment extends Fragment {

    public static ScoreboardFragment newInstance() {
        return new ScoreboardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(false);

        View rootView = inflater.inflate(R.layout.fragment_scoreboard, container, false);
        return rootView;
    }
}
