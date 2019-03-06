/*
 * Created by Sebastian Paciorek on 6.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 06.03.19 23:54
 */

package buying.tickets.gesture.contract;

import android.content.Context;
/**
 * Created by Sebastian Paciorek
 */
public interface GestureMainInterface {
    interface View {
        void setSelectedTicketsButton(boolean isSelected);

        void setSelectedTicketsControlButton(boolean isSelected);

        void setSelectedReturnButton(boolean isSelected);
    }

    interface Presenter {
        void setContext(Context context);

        void setView(GestureMainInterface.View view);

        void setCurrentItemSelectedDown();

        boolean isReturnButtonSelected();

        boolean isTicketsButtonSelected();

        boolean isTicketsControlButtonSelected();

        void setReturnButtonSelected(boolean returnButtonSelected);

        void setTicketsButtonSelected(boolean ticketsButtonSelected);

        void setTicketsControlButtonSelected(boolean ticketsControlButtonSelected);

        boolean isStopProgress();

        void setStopProgress(boolean stopProgress);
    }
}
