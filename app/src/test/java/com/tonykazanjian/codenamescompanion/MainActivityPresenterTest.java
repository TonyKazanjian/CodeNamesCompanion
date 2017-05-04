package com.tonykazanjian.codenamescompanion;

import com.tonykazanjian.codenamescompanion.main.MainActivityPresenter;
import com.tonykazanjian.codenamescompanion.main.MainActivityView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

/**
 * @author Tony Kazanjian
 */
@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {


    private MainActivityPresenter mainActivityPresenter;

    @Mock
    private MainActivityView mMainActivityView;


    @Before
    public void setUp() throws Exception {
        mMainActivityView = mock(MainActivityView.class);
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
        mainActivityPresenter.turnOffEditMode();
        Mockito.verify(mMainActivityView).onEditStopItemClicked();
    }

    @Test
    public void checkEditModeStarted(){
        mainActivityPresenter.editCards(1);
        Mockito.verify(mMainActivityView).onEditModeInit(1);
    }
}