/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 12:36
 */

package buying.tickets.gesture.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.text.DateFormat;

import buying.tickets.R;
import buying.tickets.gesture.presenter.GestureSummaryPresenter;
import buying.tickets.gesture.presenter.GestureTicketControlPresenter;
import buying.tickets.touch.view.TouchBuyTicketActivity;
/**
 * Created by Sebastian Paciorek
 */
public class GesturePaymentActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_payment);
        setTitle(getResources().getString(R.string.payment_title));

        setHandler();
    }

    private void setHandler() {
        handler = new Handler();
        try {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setTicketDate();
                    GestureTicketControlPresenter.getInstance().addNewBoughtTicket(GestureSummaryPresenter.getInstance().getTicket());
//                    finishBuyTicketActivity();
                    finishTicketsActivity();
                    finishSummaryActivity();
                    finishPaymentMethodActivity();
                    startControlTicketActivity();
                    finish();
                }
            }, 2000);
        } catch (Exception e) {

        }
    }
    @Override
    protected void onPause() {
        handler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private void startControlTicketActivity() {
        Intent intent = new Intent(this, GestureTicketControlActivity.class);
        startActivity(intent);
    }

    private void finishBuyTicketActivity() {
        if (TouchBuyTicketActivity.getInstance() != null) {
            TouchBuyTicketActivity.getInstance().finish();
        }
    }

    private void finishTicketsActivity() {
        if (GestureTicketsActivity.getInstance() != null) {
            GestureTicketsActivity.getInstance().finish();
        }
    }

    private void finishSummaryActivity() {
        if (GestureSummaryActivity.getInstance() != null) {
            GestureSummaryActivity.getInstance().finish();
        }
    }

    private void finishPaymentMethodActivity() {
        if (GesturePaymentMethodActivity.getInstance() != null) {
            GesturePaymentMethodActivity.getInstance().finish();
        }
    }

    private void setTicketDate() {
        DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        java.util.Date date = new java.util.Date();
        GestureSummaryPresenter.getInstance().getTicket().setDate(dateFormat.format(date));
    }

    @Override
    public void onBackPressed() {
        //disable back button
//        super.onBackPressed();
    }
}
