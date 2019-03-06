/*
 * Created by Sebastian Paciorek on 6.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 06.03.19 23:54
 */

package buying.tickets.touch.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.text.DateFormat;

import buying.tickets.R;
import buying.tickets.touch.presenter.TouchSummaryPresenter;
import buying.tickets.touch.presenter.TouchTicketControlPresenter;
/**
 * Created by Sebastian Paciorek
 */
public class TouchPaymentActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_payment);
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
                    TouchTicketControlPresenter.getInstance().addNewBoughtTicket(TouchSummaryPresenter.getInstance().getTicket());
                    finishBuyTicketActivity();
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
        Intent intent = new Intent(this, TouchTicketControlActivity.class);
        startActivity(intent);
    }

    private void finishBuyTicketActivity() {
        if (TouchBuyTicketActivity.getInstance() != null) {
            TouchBuyTicketActivity.getInstance().finish();
        }
    }

    private void finishTicketsActivity() {
        if (TouchTicketsActivity.getInstance() != null) {
            TouchTicketsActivity.getInstance().finish();
        }
    }

    private void finishSummaryActivity() {
        if (TouchSummaryActivity.getInstance() != null) {
            TouchSummaryActivity.getInstance().finish();
        }
    }

    private void finishPaymentMethodActivity() {
        if (TouchPaymentMethodActivity.getInstance() != null) {
            TouchPaymentMethodActivity.getInstance().finish();
        }
    }

    private void setTicketDate() {
        DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        java.util.Date date = new java.util.Date();
        TouchSummaryPresenter.getInstance().getTicket().setDate(dateFormat.format(date));
    }
}
