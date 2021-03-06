/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 08.03.19 21:31
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
