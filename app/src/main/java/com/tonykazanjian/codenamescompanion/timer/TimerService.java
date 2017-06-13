package com.tonykazanjian.codenamescompanion.timer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @author Tony Kazanjian
 */

public class TimerService extends Service {

    public static final String MINUTES_EXTRA = "minutes_extra";
    public static final String SECONDS_EXTRA = "seconds_extra";
    public static final String TIME_LEFT_EXTRA = "time_left_extra";
    public static final String BROADCAST_MSG_EXTRA = "broadcast_msg_extra";
    public static final String BROADCAST_MSG_INTENT_FILTER = "broadcast_message_intent_filter";

    public static final String ACTION_START = "com.tonykazanjian.codenamescompanion.action.ACTION_START";
    public static final String ACTION_PAUSE = "com.tonykazanjian.codenamescompanion.action.ACTION_PAUSE";
    public static final String ACTION_RESUME = "com.tonykazanjian.codenamescompanion.action.ACTION_RESUME";
    public static final String ACTION_RESET = "com.tonykazanjian.codenamescompanion.action.ACTION_RESET";

    private final IBinder mTimerBinder = new TimerBinder();

    MyCountDownTimer mMyCountDownTimer;

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO - get start time from shared prefs
        mMyCountDownTimer = new MyCountDownTimer(10000, 1000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long minutes = intent.getIntExtra(MINUTES_EXTRA, 0);
        long seconds = intent.getIntExtra(SECONDS_EXTRA, 0);

        switch (intent.getAction()) {
            case ACTION_START:
                mMyCountDownTimer.start();
                break;
            case ACTION_PAUSE:
                mMyCountDownTimer.cancel();
                break;
            case ACTION_RESUME:
                mMyCountDownTimer.start();
                break;
            case ACTION_RESET:
                mMyCountDownTimer.cancel();
                break;
        }

        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mTimerBinder;
    }

    class TimerBinder extends Binder {
        TimerService getService() {
            return TimerService.this;
        }
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private class MyCountDownTimer extends android.os.CountDownTimer {

        MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Intent intent = new Intent(BROADCAST_MSG_INTENT_FILTER);
            intent.putExtra(TIME_LEFT_EXTRA, millisUntilFinished);
            sendBroadcast(intent);
        }

        @Override
        public void onFinish() {
//            mTimerPresenter.resetTimer();
            //TODO - notify user
        }
    }
}
