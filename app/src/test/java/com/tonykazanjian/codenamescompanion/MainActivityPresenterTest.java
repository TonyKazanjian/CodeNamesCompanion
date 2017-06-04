package com.tonykazanjian.codenamescompanion;

import com.tonykazanjian.codenamescompanion.main.MainActivityPresenter;
import com.tonykazanjian.codenamescompanion.main.MainActivityView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
        Assert.assertEquals(3, mainActivityPresenter.getCardCount());
    }

    @Test
    public void checkCardAdded(){
        checkIfCardsAreDisplayed();
        Assert.assertTrue(mainActivityPresenter.addItemToList(mainActivityPresenter.getWordCards(), new WordCard()));
        Assert.assertEquals(10, mainActivityPresenter.getCardCount());
    }

    @Test
    public void checkCardRemoved() {
        checkIfCardsAreDisplayed();
        Assert.assertTrue(mainActivityPresenter.removeItemFromList(mainActivityPresenter.getWordCards(),
                mainActivityPresenter.getWordCards().get(0)));
        Assert.assertEquals(8, mainActivityPresenter.getCardCount());
    }

    @Test
    public void checkGridIsEmpty() {
        checkIfCardsAreDisplayed();
        int i = 2;
        while (mainActivityPresenter.getCardCount() > 0) {
            mainActivityPresenter.removeItemFromList(mainActivityPresenter.getWordCards(),
                    mainActivityPresenter.getWordCards().get(i));
            i--;
        }
        Assert.assertTrue(mainActivityPresenter.getCardCount() == 0);
    }
}