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
public interface GestureBuyTicketInterface {
    interface View {
        void setSelectedDiscountTicketsButton(boolean isSelected);

        void setSelectedNormalTicketsButton(boolean isSelected);

        void setSelectedReturnButton(boolean isSelected);
    }

    interface Presenter {
        void setContext(Context context);

        void setView(GestureBuyTicketInterface.View view);

        void setCurrentItemSelectedDown();

        boolean isReturnButtonSelected();

        boolean isDiscountTicketsButtonSelected();

        boolean isNormalTicketsButtonSelected();

        void setReturnButtonSelected(boolean returnButtonSelected);

        void setDiscountTicketsButtonSelected(boolean discountTicketsButtonSelected);

        void setNormalTicketsButtonSelected(boolean normalTicketsButtonSelected);

        boolean isStopProgress();

        void setStopProgress(boolean stopProgress);
    }
}
