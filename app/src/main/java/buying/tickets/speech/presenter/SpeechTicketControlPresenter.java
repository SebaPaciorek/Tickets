/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 16:15
 */

package buying.tickets.speech.presenter;

import android.content.Context;
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import buying.tickets.R;
import buying.tickets.speech.contract.SpeechTicketControlInterface;
import buying.tickets.touch.model.Ticket;
import buying.tickets.touch.model.TicketList;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Sebastian Paciorek
 */
public class SpeechTicketControlPresenter  implements SpeechTicketControlInterface.Presenter {

    private static SpeechTicketControlPresenter speechTicketControlPresenter;

    private Context context;
    private SpeechTicketControlInterface.View view;

    private Realm realm;
    private TicketList ticketList;

    private String activity;

    public SpeechTicketControlPresenter() {
        realm = Realm.getDefaultInstance();
        createTicketListObject();
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(SpeechTicketControlInterface.View view) {
        this.view = view;
    }

    @Override
    public void addNewBoughtTicket(Ticket ticket) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int maxID = 0;
                try {
                    maxID = findMaxID();
                } catch (Exception e) {
                }
                ticket.setId(maxID + 1);
                addBoughtTicket(ticket);
            }
        });
    }

    private void addBoughtTicket(Ticket ticket) {
        ticketList.getTicketRealmList().add(0, ticket);
    }

    private int findMaxID() {
        return Objects.requireNonNull(realm.where(Ticket.class).max("id")).intValue();
    }

    private void createTicketListObject() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ticketList = realm.createObject(TicketList.class);
            }
        });
    }

    @Override
    public List<Ticket> getBoughtTicketsList() {
        final List<Ticket> ticketBoughtTicketsList = new ArrayList<>();


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TicketList> ticketListRealmResults = realm.where(TicketList.class).findAll();
                List<TicketList> ticketListResult = realm.copyFromRealm(ticketListRealmResults);

                for (TicketList ticketList : ticketListResult
                ) {
                    ticketBoughtTicketsList.addAll(ticketList.getTicketRealmList());
                }
            }
        });
        Collections.sort(ticketBoughtTicketsList);

        return ticketBoughtTicketsList;
    }

    @Override
    public void findMatch(ArrayList<String> voiceResults) {
        String main = view.getReturnButton().getText().toString().toLowerCase();

        if (isMatchExist(voiceResults, main)) {
            view.setListeningActionsInfoTextView(context.getResources().getString(R.string.speech_results_checked_success_message));
            view.setSelectedReturnButton(true);
            activity = "main";
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

                    case "main":
                        view.startMainActivity();
                        break;
                }
            }
        }.start();
    }

    public static SpeechTicketControlPresenter getInstance() {
        if (speechTicketControlPresenter == null) {
            speechTicketControlPresenter = new SpeechTicketControlPresenter();
        }
        return speechTicketControlPresenter;
    }
}
