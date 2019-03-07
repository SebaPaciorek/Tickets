/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 20:05
 */

package buying.tickets.internetConnection;

/**
 * Created by Sebastian Paciorek
 */
public interface InternetConnectionInterface {
    interface View {
        void showInternetTextView(boolean isConnected);
    }

    interface Presenter {
        void changedStatusInternet(boolean isConnected);

        void getView(InternetConnectionInterface.View view);
    }
}
