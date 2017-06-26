package com.tonykazanjian.codenamescompanion.start;

import com.tonykazanjian.codenamescompanion.WordCard;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * @author Tony Kazanjian
 */
@RunWith(MockitoJUnitRunner.class)
public class WordInputPresenterTest {

    private WordInputPresenter mWordInputPresenter;

    @Mock
    private WordInputView mWordInputView;

    List<WordCard> mWordCardList;

    @Before
    public void setUp() throws Exception {
        mWordInputView = mock(WordInputView.class);
        mWordCardList = new ArrayList<>();
        mWordInputPresenter = new WordInputPresenter(mWordCardList, mWordInputView);
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
        mWordInputPresenter.setWordText(wordStrings);
        Assert.assertTrue(mWordCardList.get(0).getWord().equals("Cat"));
        Assert.assertTrue(mWordCardList.get(1).getWord().equals("Dog"));
        Assert.assertTrue(mWordCardList.get(2).getWord().equals("Fish"));

    }

    @Test
    public void checkReadiness(){
        mWordCardList.add(new WordCard());
        mWordCardList.add(new WordCard());
        mWordCardList.add(new WordCard());
        mWordCardList.add(new WordCard());
        mWordCardList.add(new WordCard());
        mWordCardList.add(new WordCard());
        mWordCardList.add(new WordCard());
        mWordCardList.add(new WordCard());
        mWordCardList.add(new WordCard());

        List<String> wordStrings = new ArrayList<>();
        wordStrings.add("Cat");
        wordStrings.add("Dog");
        wordStrings.add("Fish");
        wordStrings.add("Cat");
        wordStrings.add("Dog");
        wordStrings.add("Fish");
        wordStrings.add("Cat");
        wordStrings.add("Dog");
        wordStrings.add("Fish");
        mWordInputPresenter.setWordText(wordStrings);

        Assert.assertTrue(mWordInputPresenter.isGameReady(mWordCardList.size()));
    }
}