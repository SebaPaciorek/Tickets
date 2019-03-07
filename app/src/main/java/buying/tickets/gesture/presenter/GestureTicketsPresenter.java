/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 00:13
 */

package buying.tickets.gesture.presenter;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import buying.tickets.R;
import buying.tickets.gesture.contract.GestureTicketsInterface;
import buying.tickets.touch.model.Ticket;
/**
 * Created by Sebastian Paciorek
 */
public class GestureTicketsPresenter implements GestureTicketsInterface.Presenter {

    private static GestureTicketsPresenter gestureTicketsPresenter;

    private Context context;
    private GestureTicketsInterface.View view;
    private boolean isReturnButtonSelected = false;
    private boolean stopProgress = false;
    private List<Ticket> ticketList;

    private int currentItemSelected = 0;

    public GestureTicketsPresenter() {
        ticketList = new ArrayList<>();
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(GestureTicketsInterface.View view) {
        this.view = view;
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
    public int getCurrentItemSelected() {
        return currentItemSelected;
    }

    @Override
    public void setCurrentItemSelectedUp() {
        if (getCurrentItemSelected() > 0)
            setCurrentItemSelected(getCurrentItemSelected() - 1);
    }

    @Override
    public void setCurrentItemSelectedDown() {
        if (getCurrentItemSelected() < getTicketList().size() - 1) {
            if (isReturnButtonSelected) {
                setCurrentItemSelected(0);
                setReturnButtonSelected(false);
            } else {
                setCurrentItemSelected(getCurrentItemSelected() + 1);
            }
        } else if (getCurrentItemSelected() == getTicketList().size() - 1) {
            setReturnButtonSelected(true);
            setCurrentItemSelected(-1);
        }
    }

    @Override
    public void setReturnButtonSelected(boolean isSelected) {
        isReturnButtonSelected = isSelected;
        view.setSelectedReturnButton(isSelected);
    }

    @Override
    public void setCurrentItemSelected(int positionToSelect) {
        this.currentItemSelected = positionToSelect;
    }

    @Override
    public boolean isReturnButtonSelected() {
        return isReturnButtonSelected;
    }

    @Override
    public boolean isStopProgress() {
        return stopProgress;
    }

    @Override
    public void setStopProgress(boolean stopProgress) {
        this.stopProgress = stopProgress;
    }

    public static GestureTicketsPresenter getInstance() {
        if (gestureTicketsPresenter == null) {
            gestureTicketsPresenter = new GestureTicketsPresenter();
        }
        return gestureTicketsPresenter;
    }
}
