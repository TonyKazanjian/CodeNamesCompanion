package com.tonykazanjian.codenamescompanion.start;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.tonykazanjian.codenamescompanion.CodeNamesCompanionApplication;
import com.tonykazanjian.codenamescompanion.UserPreferences;
import com.tonykazanjian.codenamescompanion.WordCard;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

/**
 * @author Tony Kazanjian
 */
@RunWith(MockitoJUnitRunner.class)
public class WordInputPresenterTest {

    private WordInputPresenter mWordInputPresenter;

    @Mock
    private Context mContext;

    @Mock
    private SharedPreferences mSharedPreferences;

    @Mock
    private SharedPreferences.Editor mEditor;

    @Mock
    private WordInputView mWordInputView;

    List<WordCard> mWordCardList;

    @Before
    public void setUp() throws Exception {
        mWordInputView = mock(WordInputView.class);
        mWordCardList = new ArrayList<>();
        mWordInputPresenter = new WordInputPresenter(mWordCardList, mWordInputView);
        mContext = Mockito.mock(Context.class);
        mSharedPreferences = Mockito.mock(SharedPreferences.class);
        mEditor = Mockito.mock(SharedPreferences.Editor.class);
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

        Assert.assertTrue(mWordInputPresenter.getWordCards().size()>=7);
    }

    @Test
    public void givenPrefsIsEightWords() throws Exception{
        Mockito.when(mSharedPreferences.getInt(anyString(), anyInt())).thenReturn(8);
//        Mockito.when(mEditor.putInt(UserPreferences.CARD_NUMBER,8)).thenReturn(mEditor);
//        String cardNumber = UserPreferences.CARD_NUMBER;
//        mEditor.putInt(cardNumber,8).apply();
        int wordAmount =  mSharedPreferences.getInt(UserPreferences.CARD_NUMBER, 0);
//        Mockito.when(mContext.getSharedPreferences(anyString(), anyInt()).edit().putInt(UserPreferences.CARD_NUMBER, 8).commit());
//        int wordAmount = mSharedPreferences.getInt(anyString(), anyInt());
//        int wordAmount = mWordInputPresenter.getWordAmountPrefs();
        Assert.assertEquals(8, wordAmount);
    }

    @Test
    public void whenSharedPrefsForWordCountIsSet(){
        Mockito.when(mContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mSharedPreferences);
    }

    @Test
    public void givenPrefsIsNineWords(){
        Assert.assertTrue(mWordInputPresenter.getWordAmountPrefs() == 9);
    }
}