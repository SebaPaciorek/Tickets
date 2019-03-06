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
public interface GesturePaymentMethodInterface {
    interface View {
        void setSelectedMastercard(boolean isSelected);

        void setSelectedMasterpass(boolean isSelected);

        void setSelectedVisa(boolean isSelected);

        void setSelectedReturnButton(boolean isSelected);
    }

    interface Presenter {
        void setContext(Context context);

        void setView(GesturePaymentMethodInterface.View view);

        boolean isReturnButtonSelected();

        void setCurrentItemSelectedDown();

        boolean isMasterpassSelected();

        boolean isMastercardSelected();

        boolean isVisaSelected();

        void setReturnButtonSelected(boolean returnButtonSelected);

        void setVisaSelected(boolean visaSelected);

        void setMasterpassSelected(boolean masterpassSelected);

        void setMastercardSelected(boolean mastercardSelected);

        boolean isStopProgress();

        void setStopProgress(boolean stopProgress);
    }

}
