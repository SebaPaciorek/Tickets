/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 00:13
 */

package buying.tickets.gesture.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import buying.tickets.R;
import buying.tickets.gesture.contract.AccelerometerInterface;
import buying.tickets.gesture.contract.GestureBuyTicketInterface;
import buying.tickets.gesture.presenter.GestureBuyTicketPresenter;
import buying.tickets.gesture.presenter.sensor.AccelerometerPresenter;
/**
 * Created by Sebastian Paciorek
 */
public class GestureBuyTicketActivity extends AppCompatActivity implements AccelerometerInterface.View, GestureBuyTicketInterface.View {

    private static GestureBuyTicketActivity gestureBuyTicketActivity;

    private AccelerometerInterface.Presenter accelerometerPresenter;
    private GestureBuyTicketInterface.Presenter gestureBuyTicketPresenter;

    private String kindOfTicket = null;

    private Button discountTicketButton;
    private Button normalTicketButton;
    private Button returnButton;

    private ProgressBar progressBar;
    private TextView progressInfoTextView;

    private Handler timerHandler;
    private Runnable runnable = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_buyticket);
        setTitle(getResources().getString(R.string.buy_ticket_title));

        gestureBuyTicketActivity = this;

        gestureBuyTicketPresenter = GestureBuyTicketPresenter.getInstance();
        gestureBuyTicketPresenter.setContext(this);
        gestureBuyTicketPresenter.setView(this);

        accelerometerPresenter = AccelerometerPresenter.getInstance();
        accelerometerPresenter.setContext(this);
        accelerometerPresenter.setView(this);
        accelerometerPresenter.setActivity("buyTicket");
        accelerometerPresenter.getAccelerometerSensorInstance();

        setComponents();
        setInitialSelection();
    }

    private void setComponents() {
        discountTicketButton = findViewById(R.id.gesture_discount_ticketButton);
        normalTicketButton = findViewById(R.id.gesture_normal_ticketButton);
        returnButton = findViewById(R.id.gesture_buy_ticket_details_returnButton);
        progressBar = findViewById(R.id.buy_ticket_progressBar);
        progressInfoTextView = findViewById(R.id.buy_ticket_progress_infotextView);

        setDiscountTicketButton();
        setNormalTicketButton();
        setReturnButton();
    }

    private void setDiscountTicketButton() {
        discountTicketButton.setClickable(false);
    }

    private void setNormalTicketButton() {
        normalTicketButton.setClickable(false);
    }

    private void setReturnButton() {
        returnButton.setClickable(false);
    }

    public static GestureBuyTicketActivity getInstance() {
        return gestureBuyTicketActivity;
    }

    private void setInitialSelection() {
        gestureBuyTicketPresenter.setNormalTicketsButtonSelected(true);
        gestureBuyTicketPresenter.setDiscountTicketsButtonSelected(false);
        gestureBuyTicketPresenter.setReturnButtonSelected(false);

        setSelectedNormalTicketsButton(true);
        setSelectedDiscountTicketsButton(false);
        setSelectedReturnButton(false);
    }

    public void startProgressBar() {
        setProgressBar();
    }

    public void setProgressBar() {
        setProgressBarValue(0);
        timerHandler = new Handler();

        setRunnable();
    }

    private void setRunnable() {
        if (runnable == null) {
            setProgressBarValue(0);

            runnable = new Runnable() {
                int i = 0;

                @Override
                public void run() {

                    if (progressBar.getProgress() == progressBar.getMax()) {
                        accelerometerPresenter.unregisterAccelerometerListener();
                        timerHandler.removeCallbacks(this);
                        stopProgressBar();
                        startChooseActivity();
                    } else if (gestureBuyTicketPresenter.isStopProgress()) {
                        timerHandler.removeCallbacks(this);
                        stopProgressBar();
                        clearRunnable();
                        gestureBuyTicketPresenter.setStopProgress(false);
                    } else {
                        i++;
                        showProgressBar(true);
                        showProgressInfo(true);
                        setProgressBarValue(i);
                        timerHandler.postDelayed(this, 50);
                    }

                }
            };
            showProgressBar(true);
            showProgressInfo(true);
            timerHandler.postDelayed(runnable, 1000);
        }

    }

    private void startChooseActivity() {
        if (gestureBuyTicketPresenter.isNormalTicketsButtonSelected()) {
            kindOfTicket = "normal";
            startTicketsActivity();
        } else if (gestureBuyTicketPresenter.isDiscountTicketsButtonSelected()) {
            kindOfTicket = "discount";
            startTicketsActivity();
        } else if (gestureBuyTicketPresenter.isReturnButtonSelected()) {
            startMainActivity();
        }
    }

    private void startTicketsActivity() {
        Intent intent = new Intent(this, GestureTicketsActivity.class);
        intent.putExtra("ticket", kindOfTicket);
        startActivity(intent);
        finish();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, GestureMainActivity.class);
        startActivity(intent);
        finish();
    }

    public void stopProgressBar() {
        timerHandler.removeCallbacks(runnable);
        showProgressBar(false);
        showProgressInfo(false);
    }

    private void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void showProgressInfo(boolean show) {
        if (show) {
            progressInfoTextView.setVisibility(View.VISIBLE);
        } else {
            progressInfoTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void setProgressBarValue(int value) {
        progressBar.setProgress(value);
    }

    public void clearRunnable() {
        runnable = null;
    }

    @Override
    public void setSelectedDiscountTicketsButton(boolean isSelected) {
        if (isSelected) {
            discountTicketButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.selected_button_color)));
        } else {
            discountTicketButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.unselected_button_color)));
        }
    }

    @Override
    public void setSelectedNormalTicketsButton(boolean isSelected) {
        if (isSelected) {
            normalTicketButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.selected_button_color)));
        } else {
            normalTicketButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.unselected_button_color)));
        }
    }

    @Override
    public void setSelectedReturnButton(boolean isSelected) {
        if (isSelected) {
            returnButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.selected_button_color)));
        } else {
            returnButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.unselected_button_color)));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopProgressBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearRunnable();
    }

    @Override
    public void onBackPressed() {
        //disable back button
//        super.onBackPressed();
    }
}
