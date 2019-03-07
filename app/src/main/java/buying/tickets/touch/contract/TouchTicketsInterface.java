/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 00:13
 */

package buying.tickets.touch.contract;

import android.content.Context;

import java.util.List;

import buying.tickets.touch.model.Ticket;
/**
 * Created by Sebastian Paciorek
 */
public interface TouchTicketsInterface {
    interface View {
        void showMessage(String message);
    }

    interface Presenter {
        void setContext(Context context);

        void setView(TouchTicketsInterface.View view);

        List<Ticket> getTicketList();

        void setDiscountTicketList();

        void setNormalTicketList();
    }
}
