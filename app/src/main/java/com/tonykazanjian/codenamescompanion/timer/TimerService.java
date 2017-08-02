package com.tonykazanjian.codenamescompanion.timer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
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
    boolean mIsCountdownFinished;

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

        switch (intent.getAction()) {
            case ACTION_START:
                mMyCountDownTimer.start();
                startForeground(TIMER_NOTIFICATION_ID, getNotificationBuilder().build());
                mNotificationManager.notify(TIMER_NOTIFICATION_ID, mNotificationBuilder.build());

                break;
            case ACTION_PAUSE:
                mMyCountDownTimer.cancel();
                updateNotificationAction(false);
                sendNotificationPausedBroadcast();
                mNotificationManager.notify(TIMER_NOTIFICATION_ID, mNotificationBuilder.build());

                break;
            case ACTION_RESUME:
                mMyCountDownTimer = new MyCountDownTimer(mTimeLeft, 1000);
                mMyCountDownTimer.start();
                updateNotificationAction(true);
                sendNotificationStartBroadcast();
                mNotificationManager.notify(TIMER_NOTIFICATION_ID, mNotificationBuilder.build());

                break;
            case ACTION_RESET:
                mMyCountDownTimer.cancel();
                mMyCountDownTimer = new MyCountDownTimer(UserPreferences.getBaseTime(getApplicationContext()), 1000);
                mTimeLeft = UserPreferences.getBaseTime(this);
                updateNotificationAction(false);
                resetTimerInNotification();
                sendNotificationResetBroadcast();
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

        mNotificationBuilder =  new NotificationCompat.Builder(this)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentText(getTimerTextFormat())
                .setContentIntent(getRegularUIPendingIntent())
                .setAutoCancel(true)
                .addAction(getPauseAction(new Intent(getApplicationContext(), TimerService.class)))
                .addAction(getResetAction(new Intent(getApplicationContext(), TimerService.class)));

        return mNotificationBuilder;
    }

    private NotificationCompat.Action getPauseAction(Intent intent) {
        intent.setAction(ACTION_PAUSE);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), (int) System.currentTimeMillis(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action.Builder(0, "Pause", pendingIntent).build();
    }

    private NotificationCompat.Action getResumeAction(Intent intent) {
        intent.setAction(ACTION_RESUME);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), (int) System.currentTimeMillis(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action.Builder(0, "Resume", pendingIntent).build();
    }

    private NotificationCompat.Action getResetAction(Intent intent) {
        intent.setAction(ACTION_RESET);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), (int) System.currentTimeMillis(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action.Builder(0, "Reset", pendingIntent).build();
    }

    private String getTimerTextFormat(){
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(mTimeLeft) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(mTimeLeft)),
                TimeUnit.MILLISECONDS.toSeconds(mTimeLeft) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mTimeLeft)));
    }

    private void updateNotificationText(){
        getNotificationBuilder().setContentText(getTimerTextFormat());
    }

    //TODO - create paused and playing pending intents with extras for pause and play state

    private void updateNotificationAction(boolean isTicking) {
        NotificationCompat.Builder builder = (mNotificationBuilder == null ?
                getNotificationBuilder() : mNotificationBuilder);
        builder.mActions.remove(0);
        if (isTicking) {
            builder.mActions.add(0, getPauseAction(new Intent(getApplicationContext(), TimerService.class)));
        } else {
            builder.mActions.add(0, getResumeAction(new Intent(getApplicationContext(), TimerService.class)));
        }
    }

    private PendingIntent getRegularUIPendingIntent(){
        Intent regularUIIntent = new Intent(getApplicationContext(), TimerActivity.class);
        regularUIIntent.putExtra(EXTRA_IS_UI_PAUSED, false);
        regularUIIntent.putExtra(TimerActivity.EXTRA_REBIND_SERVICE, true);
//        regularUIIntent.addFlags(Intent.SI | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        return PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(),
                regularUIIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

//    private PendingIntent getPausedUIPendingIntent() {
//        Intent pausedUIIntent = new Intent(getApplicationContext(), TimerActivity.class);
//        pausedUIIntent.putExtra(EXTRA_IS_UI_PAUSED, true);
//        pausedUIIntent.putExtra(TimerActivity.EXTRA_REBIND_SERVICE, true);
////        pausedUIIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        return PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(),
//                pausedUIIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//    }
//
//    private PendingIntent getResumePendingIntent(){
//        if(mResumePendingIntent != null) return mResumePendingIntent;
//        else {
//            Intent timerStartIntent = new Intent(getApplicationContext(), TimerActivity.class);
//            timerStartIntent.setAction(ACTION_RESUME);
//
//            mResumePendingIntent = PendingIntent.getService(getApplicationContext(), (int) System.currentTimeMillis(),
//                    timerStartIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        }
//
//        return mResumePendingIntent;
//    }
//
//    private PendingIntent getPausePendingIntent(){
//        if(mPausePendingIntent != null) return mPausePendingIntent;
//        else {
//            Intent timerPauseIntent = new Intent(getApplicationContext(), TimerActivity.class);
//            timerPauseIntent.setAction(ACTION_PAUSE);
//
//            mPausePendingIntent = PendingIntent.getService(getApplicationContext(), (int) System.currentTimeMillis(),
//                    timerPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//            return mPausePendingIntent;
//        }
//    }
//
//    private PendingIntent getRestartPendingIntent() {
//
//        Intent timerRestartIntent = new Intent(getApplicationContext(), TimerActivity.class);
//        timerRestartIntent.setAction(ACTION_RESET);
//
//        return PendingIntent.getService(getApplicationContext(), (int) System.currentTimeMillis(),
//                timerRestartIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//    }

    private void resetTimerInNotification() {
        updateNotificationText();
        if (!mIsCountdownFinished){
            mNotificationManager.notify(TIMER_NOTIFICATION_ID, mNotificationBuilder.build());
        } else {
            mNotificationBuilder = null;
            mNotificationBuilder = getNotificationBuilder();
        }
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
            mNotificationManager.notify(TIMER_NOTIFICATION_ID, mNotificationBuilder.build());
        }

        @Override
        public void onFinish() {
            mIsCountdownFinished = true;

            sendBroadcast(new Intent(TIMER_FINISHED_INTENT_FILTER));
            mNotificationBuilder.setContentTitle("Time's up!");
            mNotificationBuilder.setContentText("Your turn is over!");
            mNotificationBuilder.setOngoing(false);
            mNotificationBuilder.mActions.clear();
            mNotificationBuilder.addAction(getResetAction(new Intent(getApplicationContext(), TimerService.class)));
            mNotificationBuilder.setDefaults(Notification.VISIBILITY_PUBLIC);
            mNotificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
            mNotificationBuilder.setPriority(Notification.PRIORITY_HIGH);
            stopForeground(false);
            mNotificationManager.notify(TIMER_NOTIFICATION_ID, mNotificationBuilder.build());
        }
    }
}
