package com.tonykazanjian.codenamescompanion.main;

import android.content.Context;
import android.content.SharedPreferences;

import com.tonykazanjian.codenamescompanion.UserPreferences;
import com.tonykazanjian.codenamescompanion.Utils;
import com.tonykazanjian.codenamescompanion.start.WordInputPresenter;
import com.tonykazanjian.codenamescompanion.start.WordInputView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

/**
 * @author Tony Kazanjian
 */
@RunWith(MockitoJUnitRunner.class)
public class SettingsPresenterTest {

    @Mock
    private Context mContext;

    @Mock
    private SharedPreferences mSharedPreferences;

    @Mock
    private SettingsView mSettingsView;

    @Before
    public void setUp() throws Exception {
        mContext = Mockito.mock(Context.class);
        mSharedPreferences = Mockito.mock(SharedPreferences.class);
        mSettingsView = Mockito.mock(SettingsView.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void whenTimeIsPicked() throws Exception {
        Mockito.when(mSharedPreferences.getLong(anyString(), anyLong())).thenReturn(Utils.TimeUtil.getFiftySeconds());
        long timeAmount =  mSharedPreferences.getLong(UserPreferences.BASE_TIME_KEY, 0);
        Assert.assertEquals(timeAmount, Utils.TimeUtil.getFiftySeconds());
    }

    @Test
    public void whenCardNumberIsPicked() throws Exception {
        Mockito.when(mSharedPreferences.getInt(anyString(), anyInt())).thenReturn(8);
        int wordAmount =  mSharedPreferences.getInt(UserPreferences.CARD_NUMBER, 0);
        Assert.assertEquals(wordAmount, 8);
    }

}