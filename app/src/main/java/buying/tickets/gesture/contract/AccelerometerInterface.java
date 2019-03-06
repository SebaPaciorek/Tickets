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
public interface AccelerometerInterface {

    interface View {

    }

    interface Presenter {
        void setContext(Context context);

        void setView(AccelerometerInterface.View view);

        void getAccelerometerSensorInstance();

        void unregisterAccelerometerListener();

        void setActivity(String activity);
    }
}
