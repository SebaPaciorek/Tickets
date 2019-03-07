/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 00:13
 */

package buying.tickets.gesture.presenter;

import android.content.Context;

import buying.tickets.gesture.contract.GestureSummaryInterface;
import buying.tickets.touch.model.Ticket;
/**
 * Created by Sebastian Paciorek
 */
public class GestureSummaryPresenter implements GestureSummaryInterface.Presenter {

    private static GestureSummaryPresenter gestureSummaryPresenter;

    private Context context;
    private GestureSummaryInterface.View view;

    private Ticket ticket;

    private boolean buyAndPayButtonSelected = true;
    private boolean returnButtonSelected = false;
    private boolean stopProgress = false;

    public GestureSummaryPresenter() {
        this.ticket = new Ticket();
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(GestureSummaryInterface.View view) {
        this.view = view;
    }

    public static GestureSummaryPresenter getInstance() {
        if (gestureSummaryPresenter == null) {
            gestureSummaryPresenter = new GestureSummaryPresenter();
        }
        return gestureSummaryPresenter;
    }

    @Override
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public boolean isBuyAndPayButtonSelected() {
        return buyAndPayButtonSelected;
    }

    @Override
    public void setBuyAndPayButtonSelected(boolean buyAndPayButtonSelected) {
        this.buyAndPayButtonSelected = buyAndPayButtonSelected;
    }

    @Override
    public boolean isReturnButtonSelected() {
        return returnButtonSelected;
    }

    @Override
    public void setReturnButtonSelected(boolean returnButtonSelected) {
        this.returnButtonSelected = returnButtonSelected;
    }

    @Override
    public boolean isStopProgress() {
        return stopProgress;
    }

    @Override
    public void setStopProgress(boolean stopProgress) {
        this.stopProgress = stopProgress;
    }

    @Override
    public void setCurrentItemSelectedDown() {
        if (isBuyAndPayButtonSelected()) {
            setBuyAndPayButtonSelected(false);
            setReturnButtonSelected(true);
            view.setSelectedBuyAndPayButton(false);
            view.setSelectedReturnButton(true);
        } else {
            setBuyAndPayButtonSelected(true);
            setReturnButtonSelected(false);
            view.setSelectedBuyAndPayButton(true);
            view.setSelectedReturnButton(false);
        }
    }
}
