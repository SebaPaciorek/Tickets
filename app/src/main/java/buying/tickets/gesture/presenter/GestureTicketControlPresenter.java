/*
 * Created by Sebastian Paciorek on 6.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 06.03.19 23:54
 */

package buying.tickets.gesture.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import buying.tickets.gesture.contract.GestureTicketControlInterface;
import buying.tickets.touch.model.Ticket;
import buying.tickets.touch.model.TicketList;
import io.realm.Realm;
import io.realm.RealmResults;
/**
 * Created by Sebastian Paciorek
 */
public class GestureTicketControlPresenter implements GestureTicketControlInterface.Presenter {

    private static GestureTicketControlPresenter gestureTicketControlPresenter;

    private Context context;
    private GestureTicketControlInterface.View view;

    private Realm realm;
    private TicketList ticketList;
    private boolean isReturnButtonSelected = false;
    private boolean stopProgress = false;

    private int currentItemSelected = 0;

    private GestureTicketControlPresenter() {
        realm = Realm.getDefaultInstance();
        createTicketListObject();
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(GestureTicketControlInterface.View view) {
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
    public int getCurrentItemSelected() {
        return currentItemSelected;
    }

    @Override
    public void setCurrentItemSelectedDown() {
        if (getCurrentItemSelected() < getBoughtTicketsList().size() - 1) {
            if (isReturnButtonSelected) {
                setCurrentItemSelected(0);
                setReturnButtonSelected(false);
            } else {
                setCurrentItemSelected(getCurrentItemSelected() + 1);
            }
        } else if (getCurrentItemSelected() == getBoughtTicketsList().size() - 1) {
            setReturnButtonSelected(true);
            setCurrentItemSelected(-1);
        }
    }

    @Override
    public void setCurrentItemSelected(int positionToSelect) {
        this.currentItemSelected = positionToSelect;
    }

    @Override
    public void setReturnButtonSelected(boolean isSelected) {
        isReturnButtonSelected = isSelected;
        view.setSelectedReturnButton(isSelected);
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

    public static GestureTicketControlPresenter getInstance() {
        if (gestureTicketControlPresenter == null) {
            gestureTicketControlPresenter = new GestureTicketControlPresenter();
        }
        return gestureTicketControlPresenter;
    }
}
