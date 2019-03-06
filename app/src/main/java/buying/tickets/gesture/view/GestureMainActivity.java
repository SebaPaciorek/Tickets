/*
 * Created by Sebastian Paciorek on 6.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 06.03.19 23:54
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
import buying.tickets.choosemethod.ChooseMethodActivity;
import buying.tickets.gesture.contract.AccelerometerInterface;
import buying.tickets.gesture.contract.GestureMainInterface;
import buying.tickets.gesture.presenter.GestureMainPresenter;
import buying.tickets.gesture.presenter.GestureSummaryPresenter;
import buying.tickets.gesture.presenter.sensor.AccelerometerPresenter;
import buying.tickets.touch.view.TouchTicketsActivity;
/**
 * Created by Sebastian Paciorek
 */
public class GestureMainActivity extends AppCompatActivity implements AccelerometerInterface.View, GestureMainInterface.View {

    private static GestureMainActivity gestureMainActivity;

    private Button buyTicketButton;
    private Button ticketControlButton;
    private Button returnButton;

    private String kindOfTicket = null;

    private AccelerometerInterface.Presenter accelerometerPresenter;
    private GestureMainInterface.Presenter gestureMainPresenter;

    private ProgressBar progressBar;
    private TextView progressInfoTextView;

    private Handler timerHandler;
    private Runnable runnable = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_main);
        setTitle(getResources().getString(R.string.tickets_title));

        gestureMainActivity = this;

        gestureMainPresenter = GestureMainPresenter.getInstance();
        gestureMainPresenter.setContext(this);
        gestureMainPresenter.setView(this);

        accelerometerPresenter = AccelerometerPresenter.getInstance();
        accelerometerPresenter.setContext(this);
        accelerometerPresenter.setView(this);
        accelerometerPresenter.setActivity("main");
        accelerometerPresenter.getAccelerometerSensorInstance();

        setComponents();

        setInitialSelection();
    }

    private void setComponents() {
        buyTicketButton = findViewById(R.id.gesture_buy_ticketButton);
        ticketControlButton = findViewById(R.id.gesture_ticket_controlButton);
        returnButton = findViewById(R.id.gesture_main_returnButton);
        progressBar = findViewById(R.id.main_progressBar);
        progressInfoTextView = findViewById(R.id.main_progress_infotextView);

        setBuyTicketsButton();
        setTicketControlButtonButton();
        setReturnButton();
    }

    private void setBuyTicketsButton() {
        buyTicketButton.setClickable(false);
    }

    private void setTicketControlButtonButton() {
        ticketControlButton.setClickable(false);
    }

    private void setReturnButton() {
        returnButton.setClickable(false);
    }

    public static GestureMainActivity getInstance() {
        return gestureMainActivity;
    }

    private void setInitialSelection() {
        gestureMainPresenter.setTicketsButtonSelected(true);
        gestureMainPresenter.setTicketsControlButtonSelected(false);
        gestureMainPresenter.setReturnButtonSelected(false);

        setSelectedTicketsButton(true);
        setSelectedTicketsControlButton(false);
        setSelectedReturnButton(false);
    }

    @Override
    public void setSelectedTicketsButton(boolean isSelected) {
        if (isSelected) {
            buyTicketButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.selected_button_color)));
        } else {
            buyTicketButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.unselected_button_color)));
        }
    }

    @Override
    public void setSelectedTicketsControlButton(boolean isSelected) {
        if (isSelected) {
            ticketControlButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.selected_button_color)));
        } else {
            ticketControlButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.unselected_button_color)));
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
                    } else if (gestureMainPresenter.isStopProgress()) {
                        timerHandler.removeCallbacks(this);
                        stopProgressBar();
                        clearRunnable();
                        gestureMainPresenter.setStopProgress(false);
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
        if (gestureMainPresenter.isTicketsButtonSelected()) {
            startBuyTicketsActivity();
        } else if (gestureMainPresenter.isTicketsControlButtonSelected()) {
            startTicketsControlActivity();
        } else if (gestureMainPresenter.isReturnButtonSelected()) {
            startChooseMethodActivity();
        }
    }

    private void startBuyTicketsActivity() {
        Intent intent = new Intent(this, GestureBuyTicketActivity.class);
//        kindOfTicket = "discount";
//        intent.putExtra("ticket", kindOfTicket);
        startActivity(intent);
        finish();
    }

    private void startTicketsControlActivity() {
        Intent intent = new Intent(this, GestureTicketControlActivity.class);
        startActivity(intent);
        finish();
    }

    private void startChooseMethodActivity() {
        Intent intent = new Intent(this, ChooseMethodActivity.class);
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
