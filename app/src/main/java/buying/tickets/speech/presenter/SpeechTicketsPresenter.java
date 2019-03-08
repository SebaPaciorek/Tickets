/*
 * Created by Sebastian Paciorek on 8.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 08.03.19 15:26
 */

package buying.tickets.speech.presenter;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import buying.tickets.R;
import buying.tickets.gesture.contract.GestureTicketsInterface;
import buying.tickets.gesture.presenter.GestureTicketsPresenter;
import buying.tickets.speech.contract.SpeechTicketsInterface;
import buying.tickets.touch.model.Ticket;
import buying.tickets.touch.presenter.TouchSummaryPresenter;

/**
 * Created by Sebastian Paciorek
 */
public class SpeechTicketsPresenter implements SpeechTicketsInterface.Presenter {

    private static SpeechTicketsPresenter speechTicketsPresenter;

    private Context context;
    private SpeechTicketsInterface.View view;

    private List<Ticket> ticketList;

    private String activity;

    private int currentItemSelected = 0;

    public SpeechTicketsPresenter() {
        ticketList = new ArrayList<>();
    }

    @Override
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    @Override
    public void setDiscountTicketList() {
        ticketList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(String.valueOf(context.getResources().getString(R.string.discount_tickets_json)));
            for (int i = 0; i < jsonArray.length(); i++) {
                convertJSONObjectToTicket(jsonArray.getJSONObject(i));
            }
        } catch (JSONException e) {
            view.showMessage(String.valueOf(context.getResources().getString(R.string.error_get_tickets_json_message)));
        }
    }

    @Override
    public void setNormalTicketList() {
        ticketList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(String.valueOf(context.getResources().getString(R.string.normal_tickets_json)));
            for (int i = 0; i < jsonArray.length(); i++) {
                convertJSONObjectToTicket(jsonArray.getJSONObject(i));
            }
        } catch (JSONException e) {
            view.showMessage(String.valueOf(context.getResources().getString(R.string.error_get_tickets_json_message)));
        }
    }

    private void convertJSONObjectToTicket(JSONObject jsonObject) {
        Ticket ticket = new Ticket();
        try {
            ticket.setName(jsonObject.get("name").toString());
            ticket.setPrice(jsonObject.get("price").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        addTicketToList(ticket);
    }

    private void addTicketToList(Ticket ticket) {
        ticketList.add(ticket);
    }

    @Override
    public void findMatch(String results) {
        Log.d("LOG", "results: " + results);
        results = results.toLowerCase();
        for (int i = 0; i < ticketList.size(); i++) {
            //bilet 20 min.
            if (i == 0) {
                if (findMatchTicket20min(results)) {
                    view.setListeningActionsInfoTextView(context.getResources().getString(R.string.speech_results_checked_success_message));
                    view.setSelectedReturnButton(false);
                    currentItemSelected = i;
                    setCountDownTimerStartActivity();
                }
            }
            //bilet jednorazowy przesiadkowy 75
            else if (i == 1) {
                if (findMatchTicket75min(results)) {
                    view.setListeningActionsInfoTextView(context.getResources().getString(R.string.speech_results_checked_success_message));
                    view.setSelectedReturnButton(false);
                    currentItemSelected = i;
                    setCountDownTimerStartActivity();
                }
            }
            //bilet jednorazowy przesiadkowy 90
            else if (i == 2) {
                if (findMatchTicket90min(results)) {
                    view.setListeningActionsInfoTextView(context.getResources().getString(R.string.speech_results_checked_success_message));
                    view.setSelectedReturnButton(false);
                    currentItemSelected = i;
                    setCountDownTimerStartActivity();
                }
            } else if (ticketList.get(i).getName().contains(results)) {
                view.setListeningActionsInfoTextView(context.getResources().getString(R.string.speech_results_checked_success_message));
                view.setSelectedReturnButton(false);
                currentItemSelected = i;
                setCountDownTimerStartActivity();
            } else {
                view.setListeningActionsInfoTextView(context.getResources().getString(R.string.speech_results_checked_error_message));
                view.setCountDownTimerStartListening();
            }
        }
    }

    private boolean findMatchTicket20min(String reults) {
        String sequences[] = context.getResources().getStringArray(R.array.ticket_20min_match_sequence);
        for (String sequence : sequences
        ) {
            if (reults.contains(sequence)) return true;
        }
        return false;
    }

    private boolean findMatchTicket75min(String reults) {
        String sequences[] = context.getResources().getStringArray(R.array.ticket_75min_match_sequence);
        for (String sequence : sequences
        ) {
            if (reults.contains(sequence)) return true;
        }
        return false;
    }

    private boolean findMatchTicket90min(String reults) {
        String sequences[] = context.getResources().getStringArray(R.array.ticket_90min_match_sequence);
        for (String sequence : sequences
        ) {
            if (reults.contains(sequence)) return true;
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
                    case "summary":
                        view.stopListening();
                        TouchSummaryPresenter.getInstance().setTicket(ticketList.get(getCurrentItemSelected()));
                        view.startSummaryActivity();
                        break;

                    case "buyTicket":
                        view.startBuyTicketActivity();
                        break;
                }
            }
        }.start();
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(SpeechTicketsInterface.View view) {
        this.view = view;
    }

    @Override
    public int getCurrentItemSelected() {
        return currentItemSelected;
    }

    public static SpeechTicketsPresenter getInstance() {
        if (speechTicketsPresenter == null) {
            speechTicketsPresenter = new SpeechTicketsPresenter();
        }
        return speechTicketsPresenter;
    }
}
