/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 12:36
 */

package buying.tickets.choosemethod;
/**
 * Created by Sebastian Paciorek
 */

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import buying.tickets.R;
import buying.tickets.gesture.view.GestureMainActivity;
import buying.tickets.speech.view.SpeechMainActivity;
import buying.tickets.touch.view.TouchMainActivity;

public class ChooseMethodActivity extends AppCompatActivity {

    private static ChooseMethodActivity chooseMethodActivity;

    private ChooseMethodAdapter chooseMethodAdapter;

    private LinearLayout pageDotsLinearLayout;
    private ImageView[] dotsImageView;
    private ViewPager viewPager;

    private int numberOfDots;

    private int currentMethodSelected = 0;

    private ArrayList<ChooseMethodItem> chooseMethodItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_method);

        setTitle(getResources().getString(R.string.choose_method_title));

        chooseMethodActivity = this;

        setComponents();
        loadData();
        setDisplayHeight();
        setChooseMethodAdapter();
        setViewPager();
    }

    private void setComponents() {
        pageDotsLinearLayout = findViewById(R.id.choose_method_dotsLinearLayout);
        viewPager = findViewById(R.id.choose_methodViewPager);

    }

    private void loadData() {
        chooseMethodItems = new ArrayList<>();
        int[] titles = {R.string.choose_method_title_touch, R.string.choose_method_title_gesture, R.string.choose_method_title_speech};
        int[] descriptions = {R.string.choose_method_description_touch, R.string.choose_method_description_gesture, R.string.choose_method_description_speech};
        int[] images = {R.drawable.choose_method_touch, R.drawable.choose_method_gesture, R.drawable.choose_method_speech};

        for (int i = 0; i < images.length; i++) {
            ChooseMethodItem chooseMethodItem = new ChooseMethodItem();
            chooseMethodItem.setImageID(images[i]);
            chooseMethodItem.setTitle(getResources().getString(titles[i]));
            chooseMethodItem.setDescription(getResources().getString(descriptions[i]));

            chooseMethodItems.add(chooseMethodItem);
        }
    }

    private void setDisplayHeight(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ChooseMethodAdapter.screenHeight = size.y;
    }

    private void setChooseMethodAdapter() {
        chooseMethodAdapter = new ChooseMethodAdapter(this, chooseMethodItems);
    }

    private void setViewPager() {
        viewPager.setAdapter(chooseMethodAdapter);
        viewPager.setCurrentItem(0);

        setViewPagerUI();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < numberOfDots; i++) {
                    dotsImageView[i].setImageDrawable(ContextCompat.getDrawable(ChooseMethodActivity.this, R.drawable.non_selected_item_dot));
                }
                currentMethodSelected = position;
                dotsImageView[position].setImageDrawable(ContextCompat.getDrawable(ChooseMethodActivity.this, R.drawable.selected_item_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setViewPagerUI() {
        numberOfDots = chooseMethodAdapter.getCount();
        dotsImageView = new ImageView[numberOfDots];

        for (int i = 0; i < numberOfDots; i++) {
            dotsImageView[i] = new ImageView(this);
            dotsImageView[i].setImageDrawable(ContextCompat.getDrawable(ChooseMethodActivity.this, R.drawable.non_selected_item_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(6, 0, 6, 0);

            pageDotsLinearLayout.addView(dotsImageView[i], params);
        }

        dotsImageView[0].setImageDrawable(ContextCompat.getDrawable(ChooseMethodActivity.this, R.drawable.selected_item_dot));
    }

    public void startChosenMethodActivity() {
        switch (currentMethodSelected) {
            case 0:
                startTouchMainActivity();
                break;

            case 1:
                startGestureMainActivity();
                break;

            case 2:
                startSpeechMainActivity();
                break;
        }
    }

    private void startTouchMainActivity() {
        Intent intent = new Intent(this, TouchMainActivity.class);
        startActivity(intent);
    }

    private void startGestureMainActivity() {
        Intent intent = new Intent(this, GestureMainActivity.class);
        startActivity(intent);
    }

    private void startSpeechMainActivity() {
        Intent intent = new Intent(this, SpeechMainActivity.class);
        startActivity(intent);
    }

    public static ChooseMethodActivity getInstance() {
        return chooseMethodActivity;
    }

}
