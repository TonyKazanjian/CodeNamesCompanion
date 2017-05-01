package com.tonykazanjian.codenamescompanion;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

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
        Assert.assertEquals(1, mainActivityPresenter.getCardCount());
    }
}