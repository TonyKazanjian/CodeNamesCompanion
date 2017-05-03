package com.tonykazanjian.codenamescompanion;

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


    @Before
    public void setUp() throws Exception {
        MainActivityView mainActivityView = mock(MainActivityView.class);
        mainActivityPresenter = new MainActivityPresenter(mainActivityView);

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

    }
}