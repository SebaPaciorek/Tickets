/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 00:13
 */

package buying.tickets.gesture.presenter;

import android.content.Context;

import buying.tickets.gesture.contract.GestureBuyTicketInterface;
/**
 * Created by Sebastian Paciorek
 */
public class GestureBuyTicketPresenter implements GestureBuyTicketInterface.Presenter {

    private static GestureBuyTicketPresenter gestureBuyTicketPresenter;

    private Context context;
    private GestureBuyTicketInterface.View view;

    private boolean discountTicketsButtonSelected = true;
    private boolean normalTicketsButtonSelected = true;
    private boolean returnButtonSelected = false;
    private boolean stopProgress = false;

    public GestureBuyTicketPresenter() {
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(GestureBuyTicketInterface.View view) {
        this.view = view;
    }

    @Override
    public void setCurrentItemSelectedDown() {
        if (isReturnButtonSelected()) {
            setNormalTicketsButtonSelected(true);
            setDiscountTicketsButtonSelected(false);
            setReturnButtonSelected(false);
            view.setSelectedNormalTicketsButton(true);
            view.setSelectedDiscountTicketsButton(false);
            view.setSelectedReturnButton(false);
        } else if (isNormalTicketsButtonSelected()) {
            setNormalTicketsButtonSelected(false);
            setDiscountTicketsButtonSelected(true);
            setReturnButtonSelected(false);
            view.setSelectedNormalTicketsButton(false);
            view.setSelectedDiscountTicketsButton(true);
            view.setSelectedReturnButton(false);
        } else if (isDiscountTicketsButtonSelected()) {
            setNormalTicketsButtonSelected(false);
            setDiscountTicketsButtonSelected(false);
            setReturnButtonSelected(true);
            view.setSelectedNormalTicketsButton(false);
            view.setSelectedDiscountTicketsButton(false);
            view.setSelectedReturnButton(true);
        }
    }

    @Override
    public boolean isDiscountTicketsButtonSelected() {
        return discountTicketsButtonSelected;
    }

    @Override
    public void setDiscountTicketsButtonSelected(boolean discountTicketsButtonSelected) {
        this.discountTicketsButtonSelected = discountTicketsButtonSelected;
    }

    @Override
    public boolean isNormalTicketsButtonSelected() {
        return normalTicketsButtonSelected;
    }

    @Override
    public void setNormalTicketsButtonSelected(boolean normalTicketsButtonSelected) {
        this.normalTicketsButtonSelected = normalTicketsButtonSelected;
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

    public static GestureBuyTicketPresenter getInstance() {
        if (gestureBuyTicketPresenter == null) {
            gestureBuyTicketPresenter = new GestureBuyTicketPresenter();
        }
        return gestureBuyTicketPresenter;
    }
}
