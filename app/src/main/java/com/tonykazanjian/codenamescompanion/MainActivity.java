package com.tonykazanjian.codenamescompanion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void displayCards() {

    }

    @Override
    public void enterTextOnCard(String text) {

    }

    @Override
    public void saveTextOnCard() {

    }

    @Override
    public void removeCard() {

    }
}
