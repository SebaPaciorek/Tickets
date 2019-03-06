/*
 * Created by Sebastian Paciorek on 6.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 06.03.19 23:54
 */

package buying.tickets.touch.contract;

import android.content.Context;

import java.util.List;

import buying.tickets.touch.model.Ticket;
/**
 * Created by Sebastian Paciorek
 */
public interface TouchTicketControlInterface {
    interface View {

    }

    interface Presenter {
        void setContext(Context context);

        void setView(TouchTicketControlInterface.View view);

        void addNewBoughtTicket(Ticket ticket);

        List<Ticket> getBoughtTicketsList();
    }
}
