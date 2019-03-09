/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 12:35
 */

package buying.tickets.speech.presenter;

import android.content.Context;

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

    public static SpeechSummaryPresenter getInstance() {
        if (speechSummaryPresenter == null) {
            speechSummaryPresenter = new SpeechSummaryPresenter();
        }
        return speechSummaryPresenter;
    }
}
