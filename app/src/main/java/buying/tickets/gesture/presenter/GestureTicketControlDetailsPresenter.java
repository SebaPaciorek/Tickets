/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 08.03.19 21:31
 */

package buying.tickets.gesture.presenter;

import android.content.Context;

import buying.tickets.gesture.contract.GestureTicketsControlDetailsInterface;
import buying.tickets.touch.model.Ticket;
/**
 * Created by Sebastian Paciorek
 */
public class GestureTicketControlDetailsPresenter implements GestureTicketsControlDetailsInterface.Presenter {

    private static GestureTicketControlDetailsPresenter gestureTicketControlDetailsPresenter;

    private Context context;
    private GestureTicketsControlDetailsInterface.View view;

    private Ticket ticket;

    public GestureTicketControlDetailsPresenter() {
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(GestureTicketsControlDetailsInterface.View view) {
        this.view = view;
    }

    @Override
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public static GestureTicketControlDetailsPresenter getInstance() {
        if (gestureTicketControlDetailsPresenter == null) {
            gestureTicketControlDetailsPresenter = new GestureTicketControlDetailsPresenter();
        }
        return gestureTicketControlDetailsPresenter;
    }

}
