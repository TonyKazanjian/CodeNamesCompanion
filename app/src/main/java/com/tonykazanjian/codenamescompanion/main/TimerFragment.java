package com.tonykazanjian.codenamescompanion.main;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.TimeUtils;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.tonykazanjian.codenamescompanion.R;
import com.tonykazanjian.codenamescompanion.UserPreferences;
import com.tonykazanjian.codenamescompanion.Utils;
import com.tonykazanjian.codenamescompanion.timer.TimerPresenter;
import com.tonykazanjian.codenamescompanion.timer.TimerService;
import com.tonykazanjian.codenamescompanion.timer.TimerView;

import java.util.concurrent.TimeUnit;

import static com.tonykazanjian.codenamescompanion.timer.TimerService.sIsFinished;
import static com.tonykazanjian.codenamescompanion.timer.TimerService.sIsStarted;
import static com.tonykazanjian.codenamescompanion.timer.TimerService.sIsTicking;

/**
 * @author Tony Kazanjian
 */

public class TimerFragment extends Fragment implements TimerView
{

    public static final String EXTRA_REBIND_SERVICE = "EXTRA_REBIND_SERVICE";
    private static final String EXTRA_TIME_LEFT = "EXTRA_TIME_LEFT";

    TextView mTimerText;
    TimerPresenter mTimerPresenter;
    ImageButton mStartPauseButton;
    ImageButton mResetButton;
    ProgressWheel mTimerProgress;

    TimerService mTimerService;
    TimerTickReceiver mTimerTickReceiver;
    TimerFinishedReceiver mTimerFinishedReceiver;

    long mTimeLeft;

    private boolean mIsTimeServiceBound = false;
    private boolean mRebindingService = false;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TimerService.TimerBinder binder = (TimerService.TimerBinder) iBinder;
            mTimerService = binder.getService();
            Log.i(getContext().getClass().getCanonicalName(), "service connected");
            mIsTimeServiceBound = true;

            setTimerInfo();

            if (mRebindingService) {
                onRebindService();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mTimerService = null;
        }
    };

    private void doBindService(){
        Intent intent = new Intent(getContext(), TimerService.class);
        if (!mIsTimeServiceBound) {
            getActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }
        Log.i(getContext().getClass().getCanonicalName(), "Service is bound");
    }

    private void onRebindService() {
        if (mIsTimeServiceBound && mTimerService != null) {

            // getContext() check needs to be here to ensure the play/pause state is restored when you have
            // the app waiting in recents/multitasking, and you use the service to toggle the play/pause state
            // e.g. Start playing the class with the app open, then hit multitasking. While multitasking is still open,
            // pull down the notification shade and pause the class. Now touch the app in multitasking, and resume it.
            // The pause state should be showing in the app UI.
            if (!sIsTicking) {
                mTimerProgress.setInstantProgress(mTimeLeft/ (float) UserPreferences.getBaseTime(getContext()));
            }
        }
    }

    private void doUnbindService(){
        if(mIsTimeServiceBound){
            mIsTimeServiceBound = false;
            getContext().unbindService(mServiceConnection);
            mTimerService = null;
        }
    }

    public static TimerFragment newInstance(){
        return new TimerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timer, container, false);

        setHasOptionsMenu(true);

        mTimerText = (TextView)rootView.findViewById(R.id.timer_text);
        mStartPauseButton = (ImageButton) rootView.findViewById(R.id.start_pause_btn);
        mResetButton = (ImageButton) rootView.findViewById(R.id.reset_btn);
        mTimerProgress = (ProgressWheel) rootView.findViewById(R.id.timer_progress);
        Bundle extras = getActivity().getIntent().getExtras();

        if (extras != null) {

            mRebindingService = extras.getBoolean(EXTRA_REBIND_SERVICE, false);

            if (extras.getBoolean(TimerService.EXTRA_IS_UI_PAUSED, false)) {
                // need to set the progress wheel
                mTimeLeft = extras.getLong(TimerService.TIME_LEFT_EXTRA, 0);
                mTimerProgress.setInstantProgress(mTimeLeft / (float) UserPreferences.getBaseTime(getContext()));
                mTimerText.setText(TimerService.getTimerTextFormat(mTimeLeft));
                sIsTicking = false;

                setButtonDrawable();
            } else {
                sIsTicking = true;
                setButtonDrawable();
            }
        }

        mTimerPresenter = new TimerPresenter(this);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        doBindService();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimerPresenter.setTimer();
        setRetainInstance(true);
        mTimerTickReceiver = new TimerTickReceiver();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mTimerTickReceiver, new IntentFilter(TimerService.TIMER_BROADCAST_EVENT));
        mTimerFinishedReceiver = new TimerFinishedReceiver();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mTimerFinishedReceiver, new IntentFilter(TimerService.TIMER_FINISHED_INTENT_FILTER));
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mTimerTickReceiver != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mTimerTickReceiver);
        }
        if (mTimerFinishedReceiver != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mTimerFinishedReceiver);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(getContext().getClass().getSimpleName(), "Service is unbound");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        doUnbindService();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_timer, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.fifty_seconds:
                    setBaseTime(this.getContext(),Utils.TimeUtil.getFiftySeconds());
                    break;
                case R.id.seventyfive_seconds:
                    setBaseTime(this.getContext(),Utils.TimeUtil.getSeventyFiveSeconds());
                    break;
                case R.id.ninety_seconds:
                    setBaseTime(this.getContext(),Utils.TimeUtil.getNinetySeconds());
                    break;
                case R.id.two_minutes:
                    setBaseTime(this.getContext(),Utils.TimeUtil.getTwoMinutes());
                    break;
                case R.id.three_minutes:
                    setBaseTime(this.getContext(),Utils.TimeUtil.getThreeMinutes());
                    break;
            }

        return true;
    }

    private void setBaseTime(Context context, long baseTime){
        if (sIsFinished || !sIsTicking) {
            UserPreferences.setBaseTime(context, (int) baseTime);
            mTimerPresenter.resetTimer();
        } else {
            Toast.makeText(context, "Please pause the timer before setting a new time limit", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTimerSet() {
        setButtonDrawable();
        mTimerProgress.setLinearProgress(true);

        mStartPauseButton.setOnClickListener(new StartPauseClickListener());
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimerPresenter.resetTimer();
                mTimerProgress.setInstantProgress(1);
            }
        });
    }

    @Override
    public void onTimerStarted() {
        sIsTicking = true;
        sIsStarted = true;
        setButtonDrawable();
        Intent timerIntent = new Intent(getContext(), TimerService.class);
        timerIntent.setAction(TimerService.ACTION_START);
        getActivity().startService(timerIntent);
    }

    @Override
    public void onTimerResumed() {
        setButtonDrawable();
        Intent pauseIntent = new Intent(getContext(), TimerService.class);
        pauseIntent.setAction(TimerService.ACTION_RESUME);
        getActivity().startService(pauseIntent);
    }

    @Override
    public void onTimerPaused() {
        Intent pauseIntent = new Intent(getContext(), TimerService.class);
        pauseIntent.setAction(TimerService.ACTION_PAUSE);
        getActivity().startService(pauseIntent);
        sIsTicking = false;
        setButtonDrawable();
    }

    @Override
    public void onTimerReset() {
        sIsStarted = false;
        sIsTicking = false;
        mTimerProgress.setInstantProgress(1);
        setTimerText(UserPreferences.getBaseTime(getContext()));
        Intent resetIntent = new Intent(getContext(), TimerService.class);
        resetIntent.setAction(TimerService.ACTION_RESET);
        getActivity().startService(resetIntent);
        setButtonDrawable();
    }

    private void setButtonDrawable() {
        Drawable start = getContext().getDrawable(R.drawable.ic_start_timer);
        Drawable pause = getContext().getDrawable(R.drawable.ic_pause_24dp);
        if (sIsTicking) {
            mStartPauseButton.setImageDrawable(pause);
        } else {
            mStartPauseButton.setImageDrawable(start);
        }
    }


    private class StartPauseClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (!sIsFinished) {
                if (!sIsStarted) {
                    mTimerPresenter.startTimer();
                } else if (!sIsTicking) {
                    mTimerPresenter.resumeTimer();
                } else {
                    mTimerPresenter.pauseTimer();
                }
            } else {
                mTimerPresenter.resetTimer();
                mTimerPresenter.resumeTimer();
                sIsFinished = false;
            }

        }
    }

    private void setTimerInfo(){
        long timeLeft;
        if (!sIsStarted){
            timeLeft = UserPreferences.getBaseTime(getContext());
            mTimerProgress.setInstantProgress(1);
        } else {
            timeLeft = mTimerService.getTimeLeft();
            mTimerProgress.setInstantProgress(timeLeft / (float) UserPreferences.getBaseTime(getContext()));
        }
        setTimerText(timeLeft);
    }

    private void setTimerText(long timeLeft){
        mTimerText.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(timeLeft) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeLeft)),
                TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft))));
    }

    private class TimerTickReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(TimerService.EXTRA_TIMER_BROADCAST_MSG);

            switch (message) {
                case TimerService.TIMER_TICK_BROADCAST_MSG:
                    mTimeLeft = intent.getLongExtra(TimerService.EXTRA_TIME_UNTIL_FINISHED, 0);
                    mTimerProgress.setInstantProgress(mTimeLeft / (float) UserPreferences.getBaseTime(context));
                    setTimerText(mTimeLeft -1);
                case TimerService.NOTIFICATION_PLAY_MSG:
                    mStartPauseButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_pause_24dp));
                    break;
                case TimerService.NOTIFICATION_PAUSE_MSG:
                    mStartPauseButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_start_timer));
                    break;
                case TimerService.NOTIFICATION_RESET_MSG:
                    mTimerProgress.setInstantProgress(1);
                    setTimerText(UserPreferences.getBaseTime(getContext()));
                    mStartPauseButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_start_timer));
                    break;
            }
        }
    }

    private class TimerFinishedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            sIsFinished = true;
            sIsTicking = false;
            mTimerProgress.setProgress(0);
            setButtonDrawable();
//            mStartPauseButton.setVisibility(View.GONE);
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            lp.setMargins(0,0,0,0);
//            mResetButton.setLayoutParams(lp);
//            mStartPauseButton.setOnClickListener(null);
//            sIsStarted = false;
//            sIsTicking = false;
        }
    }
}
