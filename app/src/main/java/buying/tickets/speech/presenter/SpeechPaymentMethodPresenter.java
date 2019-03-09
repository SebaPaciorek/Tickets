/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 16:16
 */

package buying.tickets.speech.presenter;

import android.content.Context;
import android.os.CountDownTimer;

import java.util.ArrayList;

import buying.tickets.R;
import buying.tickets.speech.contract.SpeechPaymentMethodInterface;

/**
 * Created by Sebastian Paciorek
 */
public class SpeechPaymentMethodPresenter implements SpeechPaymentMethodInterface.Presenter {

    private static SpeechPaymentMethodPresenter speechPaymentMethodPresenter;

    private Context context;
    private SpeechPaymentMethodInterface.View view;

    private String activity;

    public SpeechPaymentMethodPresenter() {
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(SpeechPaymentMethodInterface.View view) {
        this.view = view;
    }

    @Override
    public void findMatch(ArrayList<String> voiceResults) {
        String mastercard = view.getMastercardTextView().getText().toString().toLowerCase();
        String masterpass = view.getMasterpassTextView().getText().toString().toLowerCase();
        String visa = view.getVisaTextView().getText().toString().toLowerCase();
        String summary = view.getReturnButton().getText().toString().toLowerCase();

        if (isMatchExist(voiceResults, mastercard)) {
            view.setListeningActionsInfoTextView(context.getResources().getString(R.string.speech_results_checked_success_message));
            view.setSelectedMastercard(true);
            view.setSelectedMasterpass(false);
            view.setSelectedVisa(false);
            view.setSelectedReturnButton(false);
            activity = "payment";
            setCountDownTimerStartActivity();
        } else if (isMatchExist(voiceResults, masterpass)) {
            view.setListeningActionsInfoTextView(context.getResources().getString(R.string.speech_results_checked_success_message));
            view.setSelectedMastercard(false);
            view.setSelectedMasterpass(true);
            view.setSelectedVisa(false);
            view.setSelectedReturnButton(false);
            activity = "payment";
            setCountDownTimerStartActivity();
        } else if (isMatchExist(voiceResults, visa)) {
            view.setListeningActionsInfoTextView(context.getResources().getString(R.string.speech_results_checked_success_message));
            view.setSelectedMastercard(false);
            view.setSelectedMasterpass(false);
            view.setSelectedVisa(true);
            view.setSelectedReturnButton(false);
            activity = "payment";
            setCountDownTimerStartActivity();
        } else if (isMatchExist(voiceResults, summary)) {
            view.setListeningActionsInfoTextView(context.getResources().getString(R.string.speech_results_checked_success_message));
            view.setSelectedMastercard(false);
            view.setSelectedMasterpass(false);
            view.setSelectedVisa(false);
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
                    case "payment":
                        view.stopListening();
                        view.startPaymentActivity();
                        break;

                    case "summary":
                        view.startSummaryActivity();
                        break;
                }
            }
        }.start();
    }

    public static SpeechPaymentMethodPresenter getInstance() {
        if (speechPaymentMethodPresenter == null) {
            speechPaymentMethodPresenter = new SpeechPaymentMethodPresenter();
        }
        return speechPaymentMethodPresenter;
    }
}
