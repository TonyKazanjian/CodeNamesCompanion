package com.tonykazanjian.codenamescompanion.start;

import com.tonykazanjian.codenamescompanion.WordCard;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * @author Tony Kazanjian
 */
@RunWith(MockitoJUnitRunner.class)
public class StartActivityPresenterTest {

    private StartActivityPresenter mStartActivityPresenter;

    @Mock
    private StartActivityView mStartActivityView;

    List<WordCard> mWordCardList;

    @Before
    public void setUp() throws Exception {
        mStartActivityView = mock(StartActivityView.class);
        mWordCardList = new ArrayList<>();
        mStartActivityPresenter = new StartActivityPresenter(mWordCardList, mStartActivityView);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testWordList() {
        mWordCardList.add(new WordCard());
        mWordCardList.add(new WordCard());
        mWordCardList.add(new WordCard());

        List<String> wordStrings = new ArrayList<>();
        wordStrings.add("Cat");
        wordStrings.add("Dog");
        wordStrings.add("Fish");
        mStartActivityPresenter.setWordText(wordStrings);
        Assert.assertTrue(mWordCardList.get(0).getWord().equals("Cat"));
        Assert.assertTrue(mWordCardList.get(1).getWord().equals("Dog"));
        Assert.assertTrue(mWordCardList.get(2).getWord().equals("Fish"));

    }

}