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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import buying.tickets.R;
import buying.tickets.gesture.contract.AccelerometerInterface;
import buying.tickets.gesture.contract.GesturePaymentMethodInterface;
import buying.tickets.gesture.presenter.GesturePaymentMethodPresenter;
import buying.tickets.gesture.presenter.sensor.AccelerometerPresenter;
import buying.tickets.touch.view.TouchPaymentActivity;
/**
 * Created by Sebastian Paciorek
 */
public class GesturePaymentMethodActivity extends AppCompatActivity implements AccelerometerInterface.View, GesturePaymentMethodInterface.View {

    private static GesturePaymentMethodActivity touchPaymentMethodActivity;

    private AccelerometerInterface.Presenter accelerometerPresenter;

    private GesturePaymentMethodInterface.Presenter gesturePaymentMethodPresenter;

    private ProgressBar progressBar;
    private TextView progressInfoTextView;

    private TextView mastercardTextView;
    private TextView masterpassTextView;
    private TextView visaTextView;
    private Button returnButton;

    private Handler timerHandler;
    private Runnable runnable = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_paymentmethod);
        setTitle(getResources().getString(R.string.payment_method_title));

        touchPaymentMethodActivity = this;

        gesturePaymentMethodPresenter = GesturePaymentMethodPresenter.getInstance();
        gesturePaymentMethodPresenter.setContext(this);
        gesturePaymentMethodPresenter.setView(this);

        accelerometerPresenter = AccelerometerPresenter.getInstance();
        accelerometerPresenter.setContext(this);
        accelerometerPresenter.setView(this);
        accelerometerPresenter.setActivity("paymentmethod");
        accelerometerPresenter.getAccelerometerSensorInstance();

        setComponents();

        setInitialSelection();
    }

    private void setComponents() {
        mastercardTextView = findViewById(R.id.gesture_paymentmethod_mastercard_textView);
        masterpassTextView = findViewById(R.id.gesture_paymentmethod_masterpass_textView);
        visaTextView = findViewById(R.id.gesture_paymentmethod_visa_textView);
        returnButton = findViewById(R.id.gesture_paymentmethod_returnButton);

        progressBar = findViewById(R.id.paymentmethod_progressBar);
        progressInfoTextView = findViewById(R.id.paymentmethod_progress_infotextView);

        setReturnButton();
    }

    private void setReturnButton() {
        returnButton.setClickable(false);
    }

    private void setInitialSelection() {
        setSelectedMastercard(true);
        setSelectedMasterpass(false);
        setSelectedVisa(false);
        setSelectedReturnButton(false);

        gesturePaymentMethodPresenter.setMastercardSelected(true);
        gesturePaymentMethodPresenter.setMasterpassSelected(false);
        gesturePaymentMethodPresenter.setVisaSelected(false);
        gesturePaymentMethodPresenter.setReturnButtonSelected(false);
    }

    @Override
    public void setSelectedMastercard(boolean isSelected) {
        if (isSelected) {
            mastercardTextView.setBackgroundColor(Color.parseColor(getResources().getString(R.string.selected_recycler_view_item_color)));
        } else {
            mastercardTextView.setBackgroundColor(Color.parseColor(getResources().getString(R.string.unselected_recycler_view_item_color)));
        }
    }

    @Override
    public void setSelectedMasterpass(boolean isSelected) {
        if (isSelected) {
            masterpassTextView.setBackgroundColor(Color.parseColor(getResources().getString(R.string.selected_recycler_view_item_color)));
        } else {
            masterpassTextView.setBackgroundColor(Color.parseColor(getResources().getString(R.string.unselected_recycler_view_item_color)));
        }
    }

    @Override
    public void setSelectedVisa(boolean isSelected) {
        if (isSelected) {
            visaTextView.setBackgroundColor(Color.parseColor(getResources().getString(R.string.selected_recycler_view_item_color)));
        } else {
            visaTextView.setBackgroundColor(Color.parseColor(getResources().getString(R.string.unselected_recycler_view_item_color)));
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
                    } else if (gesturePaymentMethodPresenter.isStopProgress()) {
                        timerHandler.removeCallbacks(this);
                        stopProgressBar();
                        clearRunnable();
                        gesturePaymentMethodPresenter.setStopProgress(false);
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
        if (gesturePaymentMethodPresenter.isReturnButtonSelected()) {
            startSummaryActivity();
        } else if (gesturePaymentMethodPresenter.isMastercardSelected()) {
            startPaymentActivity();
        } else if (gesturePaymentMethodPresenter.isMasterpassSelected()) {
            startPaymentActivity();
        } else if (gesturePaymentMethodPresenter.isVisaSelected()) {
            startPaymentActivity();
        }
    }

    private void startSummaryActivity() {
        Intent intent = new Intent(this, GestureSummaryActivity.class);
        startActivity(intent);
        finish();
    }

    private void startPaymentActivity() {
        Intent intent = new Intent(this, GesturePaymentActivity.class);
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

    public static GesturePaymentMethodActivity getInstance() {
        return touchPaymentMethodActivity;
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
