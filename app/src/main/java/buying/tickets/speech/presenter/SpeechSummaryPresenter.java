/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 13:10
 */

package buying.tickets.speech.presenter;

import android.content.Context;
import android.os.CountDownTimer;

import java.util.ArrayList;

import buying.tickets.R;
import buying.tickets.speech.contract.SpeechSummaryInterface;
import buying.tickets.touch.model.Ticket;

/**
 * Created by Sebastian Paciorek
 */
public class SpeechSummaryPresenter implements SpeechSummaryInterface.Presenter {

    private static SpeechSummaryPresenter speechSummaryPresenter;

    private Context context;
    private SpeechSummaryInterface.View view;

    private Ticket ticket;

    private String activity;

    public SpeechSummaryPresenter() {
        this.ticket = new Ticket();
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(SpeechSummaryInterface.View view) {
        this.view = view;
    }

    @Override
    public Ticket getTicket() {
        return ticket;
    }

    @Override
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public void findMatch(ArrayList<String> voiceResults) {
        String buyAndPay = view.getBuyAndPayButton().getText().toString().toLowerCase();
        String tickets = view.getReturnButton().getText().toString().toLowerCase();

        if (isMatchExist(voiceResults, buyAndPay)) {
            view.setListeningActionsInfoTextView(context.getResources().getString(R.string.speech_results_checked_success_message));
            view.setSelectedBuyAndPayButton(true);
            view.setSelectedReturnButton(false);
            activity = "buyAndPay";
            setCountDownTimerStartActivity();
        } else if (isMatchExist(voiceResults, tickets)) {
            view.setListeningActionsInfoTextView(context.getResources().getString(R.string.speech_results_checked_success_message));
            view.setSelectedBuyAndPayButton(false);
            view.setSelectedReturnButton(true);
            activity = "tickets";
            setCountDownTimerStartActivity();
        } else {
            view.showListeningErrorInfoMatchInfo(true);
            view.setListeningActionsInfoTextView(context.getResources().getString(R.string.speech_results_checked_error_message));
            view.setCountDownTimerStartListening();
        }
    }

    private boolean isMatchExist(ArrayList<String> voiceResults, String sequence) {
        for (String results : voiceResults
        ) {
            if (results.toLowerCase().contains(sequence.toLowerCase()) || sequence.toLowerCase().contains(results.toLowerCase()))
                return true;
        }
        return false;
    }

    private void setCountDownTimerStartActivity() {
        view.showProgressBar(true);
        view.showProgressInfo(true);
        new CountDownTimer(2000, 10) {

            public void onTick(long millisUntilFinished) {
                int value = (int) (100 - (float) (millisUntilFinished / 2000.0) * 100);
                view.setProgressBarValue(value);
            }

            public void onFinish() {
                view.showProgressBar(false);
                view.showProgressInfo(false);
                switch (activity) {
                    case "buyAndPay":
                        view.stopListening();
                        view.startPaymentActivity();
                        break;

                    case "tickets":
                        view.startTicketsActivity();
                        break;
                }
            }
        }.start();
    }

    public static SpeechSummaryPresenter getInstance() {
        if (speechSummaryPresenter == null) {
            speechSummaryPresenter = new SpeechSummaryPresenter();
        }
        return speechSummaryPresenter;
    }
}
