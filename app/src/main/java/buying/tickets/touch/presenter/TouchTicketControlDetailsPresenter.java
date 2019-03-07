/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 00:13
 */

package buying.tickets.touch.presenter;

import android.content.Context;

import buying.tickets.touch.contract.TouchTicketsControlDetailsInterface;
import buying.tickets.touch.model.Ticket;
/**
 * Created by Sebastian Paciorek
 */
public class TouchTicketControlDetailsPresenter implements TouchTicketsControlDetailsInterface.Presenter {

    private static TouchTicketControlDetailsPresenter touchTicketControlDetailsPresenter;

    private Context context;
    private TouchTicketsControlDetailsInterface.View view;

    private Ticket ticket;

    public TouchTicketControlDetailsPresenter() {
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(TouchTicketsControlDetailsInterface.View view) {
        this.view = view;
    }

    @Override
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public static TouchTicketControlDetailsPresenter getInstance() {
        if (touchTicketControlDetailsPresenter == null) {
            touchTicketControlDetailsPresenter = new TouchTicketControlDetailsPresenter();
        }
        return touchTicketControlDetailsPresenter;
    }
}
