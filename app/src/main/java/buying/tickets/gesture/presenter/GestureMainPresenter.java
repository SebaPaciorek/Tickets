/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 08.03.19 21:31
 */

package buying.tickets.gesture.presenter;

import android.content.Context;

import buying.tickets.gesture.contract.GestureMainInterface;
/**
 * Created by Sebastian Paciorek
 */
public class GestureMainPresenter implements GestureMainInterface.Presenter {

    private static GestureMainPresenter gestureMainPresenter;

    private Context context;
    private GestureMainInterface.View view;

    private boolean ticketsButtonSelected = true;
    private boolean ticketsControlButtonSelected = true;
    private boolean returnButtonSelected = false;
    private boolean stopProgress = false;
    public GestureMainPresenter() {
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(GestureMainInterface.View view) {
        this.view = view;
    }

    @Override
    public void setCurrentItemSelectedDown() {
        if (isReturnButtonSelected()) {
            setTicketsButtonSelected(true);
            setTicketsControlButtonSelected(false);
            setReturnButtonSelected(false);
            view.setSelectedTicketsButton(true);
            view.setSelectedTicketsControlButton(false);
            view.setSelectedReturnButton(false);
        } else if (isTicketsButtonSelected()) {
            setTicketsButtonSelected(false);
            setTicketsControlButtonSelected(true);
            setReturnButtonSelected(false);
            view.setSelectedTicketsButton(false);
            view.setSelectedTicketsControlButton(true);
            view.setSelectedReturnButton(false);
        } else if (isTicketsControlButtonSelected()) {
            setTicketsButtonSelected(false);
            setTicketsControlButtonSelected(false);
            setReturnButtonSelected(true);
            view.setSelectedTicketsButton(false);
            view.setSelectedTicketsControlButton(false);
            view.setSelectedReturnButton(true);
        }
    }

    @Override
    public boolean isReturnButtonSelected() {
        return returnButtonSelected;
    }

    @Override
    public boolean isTicketsButtonSelected() {
        return ticketsButtonSelected;
    }

    @Override
    public boolean isTicketsControlButtonSelected() {
        return ticketsControlButtonSelected;
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
    public void setTicketsButtonSelected(boolean ticketsButtonSelected) {
        this.ticketsButtonSelected = ticketsButtonSelected;
    }

    @Override
    public void setReturnButtonSelected(boolean returnButtonSelected) {
        this.returnButtonSelected = returnButtonSelected;
    }

    @Override
    public void setTicketsControlButtonSelected(boolean ticketsControlButtonSelected) {
        this.ticketsControlButtonSelected = ticketsControlButtonSelected;
    }

    public static GestureMainPresenter getInstance() {
        if (gestureMainPresenter == null) {
            gestureMainPresenter = new GestureMainPresenter();
        }
        return gestureMainPresenter;
    }
}
