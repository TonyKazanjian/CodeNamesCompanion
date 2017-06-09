package com.tonykazanjian.codenamescompanion;

import com.tonykazanjian.codenamescompanion.main.GamePresenter;
import com.tonykazanjian.codenamescompanion.main.GameView;

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
public class GamePresenterTest {


    private GamePresenter mGamePresenter;

    @Mock
    private GameView mGameView;


    @Before
    public void setUp() throws Exception {
        mGameView = mock(GameView.class);
        mGamePresenter = new GamePresenter(mGameView);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void checkIfCardsAreDisplayed() {
        mGamePresenter.showGridCards(new ArrayList<WordCard>());
        Assert.assertEquals(3, mGamePresenter.getCardCount());
    }

    @Test
    public void checkCardAdded(){
        checkIfCardsAreDisplayed();
        Assert.assertTrue(mGamePresenter.addItemToList(mGamePresenter.getWordCards(), new WordCard()));
        Assert.assertEquals(10, mGamePresenter.getCardCount());
    }

    @Test
    public void checkCardRemoved() {
        checkIfCardsAreDisplayed();
        Assert.assertTrue(mGamePresenter.removeItemFromList(mGamePresenter.getWordCards(),
                mGamePresenter.getWordCards().get(0)));
        Assert.assertEquals(8, mGamePresenter.getCardCount());
    }

    @Test
    public void checkGridIsEmpty() {
        checkIfCardsAreDisplayed();
        int i = 2;
        while (mGamePresenter.getCardCount() > 0) {
            mGamePresenter.removeItemFromList(mGamePresenter.getWordCards(),
                    mGamePresenter.getWordCards().get(i));
            i--;
        }
        Assert.assertTrue(mGamePresenter.getCardCount() == 0);
    }
}