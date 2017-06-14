package com.tonykazanjian.codenamescompanion.timer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * @author Tony Kazanjian
 */

public class TimerService extends Service {

    public static final String TIME_LEFT_EXTRA = "time_left_extra";
    public static final String TIMER_TICK_INTENT_FILTER = "timer_tick_intent_filter";
    public static final String TIMER_FINISHED_INTENT_FILTER = "timer_finished_intent_filter";

    public static final String ACTION_START = "com.tonykazanjian.codenamescompanion.action.ACTION_START";
    public static final String ACTION_PAUSE = "com.tonykazanjian.codenamescompanion.action.ACTION_PAUSE";
    public static final String ACTION_RESUME = "com.tonykazanjian.codenamescompanion.action.ACTION_RESUME";
    public static final String ACTION_RESET = "com.tonykazanjian.codenamescompanion.action.ACTION_RESET";

    private final IBinder mTimerBinder = new TimerBinder();

    long mTimeLeft;

    MyCountDownTimer mMyCountDownTimer;
    NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO - get start time from shared prefs
        mMyCountDownTimer = new MyCountDownTimer(1000 * 5, 1000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        switch (intent.getAction()) {
            case ACTION_START:
                mMyCountDownTimer.start();
                break;
            case ACTION_PAUSE:
                mMyCountDownTimer.cancel();
                break;
            case ACTION_RESUME:
                mMyCountDownTimer = new MyCountDownTimer(mTimeLeft, 1000);
                mMyCountDownTimer.start();
                break;
            case ACTION_RESET:
                mMyCountDownTimer.cancel();
                mMyCountDownTimer = new MyCountDownTimer(1000 * 5, 1000);
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

    private NotificationCompat.Builder getNotificationBuilder() {
        return new NotificationCompat.Builder(this)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle("Time's up")
                .setContentText("Your turn is over!")
                .setAutoCancel(true)
                .setDefaults(Notification.VISIBILITY_PUBLIC)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setPriority(Notification.PRIORITY_HIGH);
    }

    private class MyCountDownTimer extends android.os.CountDownTimer {

        MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTimeLeft = millisUntilFinished;
            Intent intent = new Intent(TIMER_TICK_INTENT_FILTER);
            intent.putExtra(TIME_LEFT_EXTRA, millisUntilFinished);
            sendBroadcast(intent);
        }

        @Override
        public void onFinish() {
            sendBroadcast(new Intent(TIMER_FINISHED_INTENT_FILTER));
            mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
            mNotificationManager.notify(0, getNotificationBuilder().build());
        }
    }
}
