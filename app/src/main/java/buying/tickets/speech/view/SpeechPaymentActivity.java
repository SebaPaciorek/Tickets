/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 16:18
 */

package buying.tickets.speech.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.text.DateFormat;

import buying.tickets.R;
import buying.tickets.speech.presenter.SpeechSummaryPresenter;
import buying.tickets.speech.presenter.SpeechTicketControlPresenter;

/**
 * Created by Sebastian Paciorek
 */
public class SpeechPaymentActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_payment);
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
                    SpeechTicketControlPresenter.getInstance().addNewBoughtTicket(SpeechSummaryPresenter.getInstance().getTicket());
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
        Intent intent = new Intent(this, SpeechTicketControlActivity.class);
        startActivity(intent);
    }

    private void finishBuyTicketActivity() {
        if (SpeechBuyTicketActivity.getInstance() != null) {
            SpeechBuyTicketActivity.getInstance().finish();
        }
    }

    private void finishTicketsActivity() {
        if (SpeechTicketsActivity.getInstance() != null) {
            SpeechTicketsActivity.getInstance().finish();
        }
    }

    private void finishSummaryActivity() {
        if (SpeechSummaryActivity.getInstance() != null) {
            SpeechSummaryActivity.getInstance().finish();
        }
    }

    private void finishPaymentMethodActivity() {
        if (SpeechPaymentMethodActivity.getInstance() != null) {
            SpeechPaymentMethodActivity.getInstance().finish();
        }
    }

    private void setTicketDate() {
        DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        java.util.Date date = new java.util.Date();
        SpeechSummaryPresenter.getInstance().getTicket().setDate(dateFormat.format(date));
    }

    @Override
    public void onBackPressed() {
        //disable back button
//        super.onBackPressed();
    }
}
