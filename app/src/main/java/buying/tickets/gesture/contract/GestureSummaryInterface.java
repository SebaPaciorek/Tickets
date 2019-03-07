/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 00:13
 */

package buying.tickets.gesture.contract;

import android.content.Context;

import buying.tickets.touch.model.Ticket;
/**
 * Created by Sebastian Paciorek
 */
public interface GestureSummaryInterface {
    interface View {
        void setSelectedBuyAndPayButton(boolean isSelected);

        void setSelectedReturnButton(boolean isSelected);
    }

    interface Presenter {
        void setContext(Context context);

        void setView(GestureSummaryInterface.View view);

        Ticket getTicket();

        void setCurrentItemSelectedDown();

        boolean isReturnButtonSelected();

        void setReturnButtonSelected(boolean returnButtonSelected);

        void setBuyAndPayButtonSelected(boolean buyAndPayButtonSelected);

        boolean isStopProgress();

        void setStopProgress(boolean stopProgress);
    }
}
