/*
 * Created by Sebastian Paciorek on 6.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 06.03.19 23:54
 */

package buying.tickets.application;

import android.app.Application;

import io.realm.Realm;
/**
 * Created by Sebastian Paciorek
 */
public class TicketsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
