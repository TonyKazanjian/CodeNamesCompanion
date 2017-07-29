package com.tonykazanjian.codenamescompanion.timer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.tonykazanjian.codenamescompanion.UserPreferences;

import java.util.concurrent.TimeUnit;

/**
 * @author Tony Kazanjian
 */

public class TimerService extends Service {

    public static final String TIME_LEFT_EXTRA = "time_left_extra";
    public static final String TIMER_FINISHED_INTENT_FILTER = "timer_finished_intent_filter";

    public static final String ACTION_START = "com.tonykazanjian.codenamescompanion.action.ACTION_START";
    public static final String ACTION_PAUSE = "com.tonykazanjian.codenamescompanion.action.ACTION_PAUSE";
    public static final String ACTION_RESUME = "com.tonykazanjian.codenamescompanion.action.ACTION_RESUME";
    public static final String ACTION_RESET = "com.tonykazanjian.codenamescompanion.action.ACTION_RESET";
    private static final String EXTRA_IS_UI_PAUSED = "EXTRA_IS_UI_PAUSED";
    private static final int TIMER_NOTIFICATION_ID = 1;

    /**
     * Broadcast messages
     */
    public static final String TIMER_BROADCAST_EVENT = "TIMER_BROADCAST_EVENT";
    public static final String EXTRA_TIMER_BROADCAST_MSG = "EXTRA_TIMER_BROADCAST_MSG";
    public static final String NOTIFICATION_PAUSE_MSG = "NOTIFICATION_PAUSE_MSG";
    public static final String NOTIFICATION_PLAY_MSG = "NOTIFICATION_PLAY_MSG";
    public static final String NOTIFICATION_RESET_MSG = "NOTIFICATION_RESET_MSG";

    private final IBinder mTimerBinder = new TimerBinder();

    long mTimeLeft;

    MyCountDownTimer mMyCountDownTimer;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mNotificationBuilder;
    private PendingIntent mPausePendingIntent;
    private PendingIntent mResumePendingIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        mMyCountDownTimer = new MyCountDownTimer(UserPreferences.getBaseTime(getApplicationContext()), 1000);
        mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startForeground(TIMER_NOTIFICATION_ID, getNotificationBuilder().build());
        switch (intent.getAction()) {
            case ACTION_START:
                mMyCountDownTimer.start();
                updateNotificationAction(true);
                break;
            case ACTION_PAUSE:
                mMyCountDownTimer.cancel();
                updateNotificationAction(false);
                sendNotificationPausedBroadcast();
                break;
            case ACTION_RESUME:
                mMyCountDownTimer = new MyCountDownTimer(mTimeLeft, 1000);
                mMyCountDownTimer.start();
                sendNotificationStartBroadcast();
                break;
            case ACTION_RESET:
                mMyCountDownTimer.cancel();
                mMyCountDownTimer = new MyCountDownTimer(UserPreferences.getBaseTime(getApplicationContext()), 1000);
                mTimeLeft = UserPreferences.getBaseTime(this);
                updateNotificationText();
                break;
        }

        return Service.START_NOT_STICKY;
    }
    private Intent getStandardBroadcastIntent(String broadcastMessage) {
        Intent intent = new Intent(TIMER_BROADCAST_EVENT);
        intent.putExtra(EXTRA_TIMER_BROADCAST_MSG, broadcastMessage);
        intent.putExtra(TIME_LEFT_EXTRA, mTimeLeft);

        return intent;
    }

    private void sendNotificationStartBroadcast() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(getStandardBroadcastIntent(NOTIFICATION_PLAY_MSG));
    }

    private void sendNotificationPausedBroadcast() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(getStandardBroadcastIntent(NOTIFICATION_PAUSE_MSG));
    }

    private void sendNotificationResetBroadcast() {

        LocalBroadcastManager.getInstance(this).sendBroadcast(getStandardBroadcastIntent(NOTIFICATION_RESET_MSG));
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

        if (mNotificationBuilder != null) {
            return mNotificationBuilder;
        }

        return mNotificationBuilder =  new NotificationCompat.Builder(this)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentText(getTimerTextFormat())
                .setAutoCancel(true)
                .setContentIntent(getRegularUIPendingIntent())
                .addAction(0,"Start", getResumePendingIntent())
                .addAction(0,"Restart", getRestartPendingIntent());
    }

    private String getTimerTextFormat(){
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(mTimeLeft) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(mTimeLeft)),
                TimeUnit.MILLISECONDS.toSeconds(mTimeLeft) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mTimeLeft)));
    }

    private void updateNotificationText(){
        mNotificationBuilder.setContentText(getTimerTextFormat());
        mNotificationManager.notify(TIMER_NOTIFICATION_ID, mNotificationBuilder.build());
    }

    //TODO - create paused and playing pending intents with extras for pause and play state

    private void updateNotificationAction(boolean isTicking) {
        NotificationCompat.Builder builder = (mNotificationBuilder == null ?
                getNotificationBuilder() : mNotificationBuilder);
        NotificationCompat.Action playAction = builder.mActions.get(0);
        NotificationCompat.Action restartAction = builder.mActions.get(
                (builder.mActions.size() == 2 ? 1 : 0));
        restartAction.actionIntent = getRestartPendingIntent();
        builder.setContentIntent(getRegularUIPendingIntent());
        mNotificationManager.notify(TIMER_NOTIFICATION_ID, builder.build());

        if (isTicking && playAction.actionIntent != mPausePendingIntent) {
            playAction.actionIntent = getPausePendingIntent();
            playAction.title = "Pause";
            builder.setContentIntent(getRegularUIPendingIntent());
            mNotificationManager.notify(TIMER_NOTIFICATION_ID, builder.build());
        } else if (!isTicking && playAction.actionIntent != mResumePendingIntent) {
            playAction.actionIntent = getResumePendingIntent();
            playAction.title = "Resume";
            builder.setContentIntent(getPausedUIPendingIntent());
            mNotificationManager.notify(TIMER_NOTIFICATION_ID, builder.build());
        }

    }

    private PendingIntent getRegularUIPendingIntent(){
        Intent regularUIIntent = new Intent(getApplicationContext(), TimerActivity.class);
        regularUIIntent.putExtra(EXTRA_IS_UI_PAUSED, false);
        regularUIIntent.putExtra(TimerActivity.EXTRA_REBIND_SERVICE, true);
        regularUIIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        return PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(),
                regularUIIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getPausedUIPendingIntent() {
        Intent pausedUIIntent = new Intent(getApplicationContext(), TimerActivity.class);
        pausedUIIntent.putExtra(EXTRA_IS_UI_PAUSED, true);
        pausedUIIntent.putExtra(TimerActivity.EXTRA_REBIND_SERVICE, true);
        pausedUIIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        return PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(),
                pausedUIIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getResumePendingIntent(){
        if(mResumePendingIntent != null) return mResumePendingIntent;
        else {
            Intent timerStartIntent = new Intent(getApplicationContext(), TimerActivity.class);
            timerStartIntent.setAction(ACTION_RESUME);

            mResumePendingIntent = PendingIntent.getService(getApplicationContext(), (int) System.currentTimeMillis(),
                    timerStartIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }


        return mResumePendingIntent;
    }

    private PendingIntent getPausePendingIntent(){
        if(mPausePendingIntent != null) return mPausePendingIntent;
        else {
            Intent timerPauseIntent = new Intent(getApplicationContext(), TimerActivity.class);
            timerPauseIntent.setAction(ACTION_PAUSE);

            mPausePendingIntent = PendingIntent.getService(getApplicationContext(), (int) System.currentTimeMillis(),
                    timerPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            return mPausePendingIntent;
        }
    }

    private PendingIntent getRestartPendingIntent() {

        Intent timerRestartIntent = new Intent(getApplicationContext(), TimerActivity.class);
        timerRestartIntent.setAction(ACTION_RESET);

        return PendingIntent.getService(getApplicationContext(), (int) System.currentTimeMillis(),
                timerRestartIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private class MyCountDownTimer extends android.os.CountDownTimer {

        MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTimeLeft = millisUntilFinished;
            sendNotificationStartBroadcast();
            updateNotificationText();
        }

        @Override
        public void onFinish() {
            sendBroadcast(new Intent(TIMER_FINISHED_INTENT_FILTER));
            mNotificationBuilder.setContentTitle("Time's up!");
            mNotificationBuilder.setContentText("Your turn is over!");
            mNotificationBuilder.mActions.clear();
            mNotificationBuilder.addAction(0,"Restart", getRestartPendingIntent());
            mNotificationBuilder.setDefaults(Notification.VISIBILITY_PUBLIC);
            mNotificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
            mNotificationBuilder.setPriority(Notification.PRIORITY_HIGH);
            mNotificationManager.notify(TIMER_NOTIFICATION_ID, mNotificationBuilder.build());
        }
    }
}
