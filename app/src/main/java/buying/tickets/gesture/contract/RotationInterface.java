/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 00:13
 */

package buying.tickets.gesture.contract;

import android.content.Context;
/**
 * Created by Sebastian Paciorek
 */
public interface RotationInterface {

    interface View {

    }

    interface Presenter {
        void setContext(Context context);

        void setView(RotationInterface.View view);
    }
}
