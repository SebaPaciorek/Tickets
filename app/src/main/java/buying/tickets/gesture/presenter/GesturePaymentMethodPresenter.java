/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 00:13
 */

package buying.tickets.gesture.presenter;

import android.content.Context;

import buying.tickets.gesture.contract.GesturePaymentMethodInterface;
import buying.tickets.gesture.contract.GestureSummaryInterface;
/**
 * Created by Sebastian Paciorek
 */
public class GesturePaymentMethodPresenter implements GesturePaymentMethodInterface.Presenter {

    private static GesturePaymentMethodPresenter gesturePaymentMethodPresenter;

    private Context context;
    private GesturePaymentMethodInterface.View view;

    private boolean returnButtonSelected = false;
    private boolean mastercardSelected = true;
    private boolean masterpassSelected = false;
    private boolean visaSelected = false;
    private boolean stopProgress = false;

    public Context getContext() {
        return context;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    public GesturePaymentMethodInterface.View getView() {
        return view;
    }

    @Override
    public void setView(GesturePaymentMethodInterface.View view) {
        this.view = view;
    }

    @Override
    public boolean isReturnButtonSelected() {
        return returnButtonSelected;
    }

    @Override
    public boolean isMastercardSelected() {
        return mastercardSelected;
    }

    @Override
    public boolean isMasterpassSelected() {
        return masterpassSelected;
    }

    @Override
    public boolean isVisaSelected() {
        return visaSelected;
    }

    @Override
    public void setMastercardSelected(boolean mastercardSelected) {
        this.mastercardSelected = mastercardSelected;
    }

    @Override
    public void setMasterpassSelected(boolean masterpassSelected) {
        this.masterpassSelected = masterpassSelected;
    }

    @Override
    public void setVisaSelected(boolean visaSelected) {
        this.visaSelected = visaSelected;
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
        if (isReturnButtonSelected()) {
            setReturnButtonSelected(false);
            mastercardSelected = true;
            masterpassSelected = false;
            visaSelected = false;
            view.setSelectedReturnButton(false);
            view.setSelectedMastercard(true);
            view.setSelectedMasterpass(false);
            view.setSelectedVisa(false);
        } else if (mastercardSelected) {
            setReturnButtonSelected(false);
            mastercardSelected = false;
            masterpassSelected = true;
            visaSelected = false;
            view.setSelectedReturnButton(false);
            view.setSelectedMastercard(false);
            view.setSelectedMasterpass(true);
            view.setSelectedVisa(false);
        } else if (masterpassSelected) {
            setReturnButtonSelected(false);
            mastercardSelected = false;
            masterpassSelected = false;
            visaSelected = true;
            view.setSelectedReturnButton(false);
            view.setSelectedMastercard(false);
            view.setSelectedMasterpass(false);
            view.setSelectedVisa(true);
        } else if (visaSelected) {
            setReturnButtonSelected(true);
            mastercardSelected = false;
            masterpassSelected = false;
            visaSelected = false;
            view.setSelectedReturnButton(true);
            view.setSelectedMastercard(false);
            view.setSelectedMasterpass(false);
            view.setSelectedVisa(false);
        }
    }

    public static GesturePaymentMethodPresenter getInstance(){
        if (gesturePaymentMethodPresenter == null) {
            gesturePaymentMethodPresenter = new GesturePaymentMethodPresenter();
        }
        return gesturePaymentMethodPresenter;
    }
}
