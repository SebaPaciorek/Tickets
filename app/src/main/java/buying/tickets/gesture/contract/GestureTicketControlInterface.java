/*
 * Created by Sebastian Paciorek on 6.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 06.03.19 23:54
 */

package buying.tickets.gesture.contract;

import android.content.Context;

import java.util.List;

import buying.tickets.touch.model.Ticket;
/**
 * Created by Sebastian Paciorek
 */
public interface GestureTicketControlInterface {
    interface View {
        void notifyDataSetChangedTicketsRecyclerView();

        void setSelectedReturnButton(boolean isSelected);
    }

    interface Presenter {
        void setContext(Context context);

        void setView(GestureTicketControlInterface.View view);

        void addNewBoughtTicket(Ticket ticket);

        List<Ticket> getBoughtTicketsList();

        int getCurrentItemSelected();

        void setCurrentItemSelectedDown();

        boolean isReturnButtonSelected();

        void setReturnButtonSelected(boolean isSelected);

        void setCurrentItemSelected(int positionToSelect);

        boolean isStopProgress();

        void setStopProgress(boolean stopProgress);
    }

}
