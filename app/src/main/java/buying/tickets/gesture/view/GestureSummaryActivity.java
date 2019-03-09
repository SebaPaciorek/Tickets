/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 12:36
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
import buying.tickets.gesture.contract.GestureSummaryInterface;
import buying.tickets.gesture.presenter.GestureSummaryPresenter;
import buying.tickets.gesture.presenter.sensor.AccelerometerPresenter;
/**
 * Created by Sebastian Paciorek
 */
public class GestureSummaryActivity extends AppCompatActivity implements AccelerometerInterface.View, GestureSummaryInterface.View {

    private static GestureSummaryActivity gestureSummaryActivity;

    private GestureSummaryInterface.Presenter gestureSummaryPresenter;

    private AccelerometerInterface.Presenter accelerometerPresenter;

    private TextView nameTextView;
    private TextView priceTextView;
    private Button buyAndPayButton;
    private Button returnButton;

    private ProgressBar progressBar;
    private TextView progressInfoTextView;

    private Handler timerHandler;
    private Runnable runnable = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_summary);
        setTitle(getResources().getString(R.string.summary_title));

        gestureSummaryActivity = this;

        gestureSummaryPresenter = GestureSummaryPresenter.getInstance();
        gestureSummaryPresenter.setContext(this);
        gestureSummaryPresenter.setView(this);

        accelerometerPresenter = AccelerometerPresenter.getInstance();
        accelerometerPresenter.setContext(this);
        accelerometerPresenter.setView(this);
        accelerometerPresenter.setActivity("summary");
        accelerometerPresenter.getAccelerometerSensorInstance();

        setComponents();

        setNameTextView();
        setPriceTextView();

        setInitialSelection();
    }

    private void setComponents() {
        nameTextView = findViewById(R.id.gesture_summary_nametextView);
        priceTextView = findViewById(R.id.gesture_summary_pricetextView);
        buyAndPayButton = findViewById(R.id.gesture_summary_buy_and_payButton);
        returnButton = findViewById(R.id.gesture_summary_returnButton);
        progressBar = findViewById(R.id.summary_progressBar);
        progressInfoTextView = findViewById(R.id.summary_progress_infotextView);

        setBuyAndPayButton();
        setReturnButton();
    }

    public void setNameTextView() {
        nameTextView.setText(gestureSummaryPresenter.getTicket().getName());
    }

    public void setPriceTextView() {
        priceTextView.setText(gestureSummaryPresenter.getTicket().getPrice());
    }

    private void setInitialSelection() {
        gestureSummaryPresenter.setBuyAndPayButtonSelected(true);
        gestureSummaryPresenter.setReturnButtonSelected(false);
        setSelectedBuyAndPayButton(true);
        setSelectedReturnButton(false);
    }

    private void setBuyAndPayButton() {
        buyAndPayButton.setClickable(false);
    }

    private void setReturnButton() {
        returnButton.setClickable(false);
    }

    @Override
    public void setSelectedBuyAndPayButton(boolean isSelected) {
        if (isSelected) {
            buyAndPayButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.selected_button_color)));
        } else {
            buyAndPayButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.unselected_button_color)));
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
                    } else if (gestureSummaryPresenter.isStopProgress()) {
                        timerHandler.removeCallbacks(this);
                        stopProgressBar();
                        clearRunnable();
                        gestureSummaryPresenter.setStopProgress(false);
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
        if (gestureSummaryPresenter.isReturnButtonSelected()) {
            startTicketsActivity();
        } else {
            startPaymentMethodActivity();
        }
    }

    private void startTicketsActivity() {
        Intent intent = new Intent(this, GestureTicketsActivity.class);
        startActivity(intent);
        finish();
    }

    public void startPaymentMethodActivity() {
        Intent intent = new Intent(this, GesturePaymentMethodActivity.class);
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

    public static GestureSummaryActivity getInstance() {
        return gestureSummaryActivity;
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
