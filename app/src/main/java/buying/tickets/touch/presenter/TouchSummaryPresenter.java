/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 08.03.19 21:31
 */

package buying.tickets.touch.presenter;

import android.content.Context;

import buying.tickets.touch.contract.TouchSummaryInterface;
import buying.tickets.touch.model.Ticket;
/**
 * Created by Sebastian Paciorek
 */
public class TouchSummaryPresenter implements TouchSummaryInterface.Presenter {

    private static TouchSummaryPresenter touchSummaryPresenter;

    private Context context;
    private TouchSummaryInterface.View view;

    private Ticket ticket;

    public TouchSummaryPresenter() {
        ticket = new Ticket();
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(TouchSummaryInterface.View view) {
        this.view = view;
    }

    public static TouchSummaryPresenter getInstance() {
        if (touchSummaryPresenter == null) {
            touchSummaryPresenter = new TouchSummaryPresenter();
        }
        return touchSummaryPresenter;
    }

    @Override
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
