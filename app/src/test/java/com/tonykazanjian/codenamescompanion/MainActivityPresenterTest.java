package com.tonykazanjian.codenamescompanion;

import org.askerov.dynamicgrid.DynamicGridView;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * @author Tony Kazanjian
 */
@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {


    private MainActivityPresenter mainActivityPresenter;
    private MainActivityView mMainActivityView;
    private DynamicGridView mDynamicGridView;


    @Before
    public void setUp() throws Exception {
        mMainActivityView = mock(MainActivityView.class);
        mDynamicGridView = mock(DynamicGridView.class);
        mainActivityPresenter = new MainActivityPresenter(mMainActivityView);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void checkIfCardsAreDisplayed() {
        mainActivityPresenter.showCards(new ArrayList<WordCard>());
        Assert.assertEquals(9, mainActivityPresenter.getCardCount());
    }

    @Test
    public void checkEditModeStopped(){

    }

    @Test
    public void checkEditModeStarted(){
        mainActivityPresenter.editCards(1);
        Assert.assertTrue(mDynamicGridView.isEditMode());

    }
}