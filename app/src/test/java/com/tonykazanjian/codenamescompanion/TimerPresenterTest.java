package com.tonykazanjian.codenamescompanion;

import com.tonykazanjian.codenamescompanion.timer.TimerPresenter;
import com.tonykazanjian.codenamescompanion.timer.TimerView;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

/**
 * @author Tony Kazanjian
 */

@RunWith(MockitoJUnitRunner.class)
public class TimerPresenterTest {

    private TimerPresenter mTimerPresenter;

    @Mock
    private TimerView mTimerView;

    @Before
    public void setUp() throws Exception {
        mTimerView = mock(TimerView.class);
        mTimerPresenter = new TimerPresenter(mTimerView);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void isTimerStarted() {
        mTimerPresenter.startTimer();
        Assert.assertTrue(mTimerPresenter.startTimer());
    }
}
