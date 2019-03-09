/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 09.03.19 12:36
 */

package buying.tickets.gesture.presenter.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import buying.tickets.R;
import buying.tickets.gesture.contract.AccelerometerInterface;
import buying.tickets.gesture.model.Gravity;
import buying.tickets.gesture.presenter.GestureBuyTicketPresenter;
import buying.tickets.gesture.presenter.GestureMainPresenter;
import buying.tickets.gesture.presenter.GesturePaymentMethodPresenter;
import buying.tickets.gesture.presenter.GestureSummaryPresenter;
import buying.tickets.gesture.presenter.GestureTicketControlPresenter;
import buying.tickets.gesture.presenter.GestureTicketsPresenter;
import buying.tickets.gesture.view.GestureBuyTicketActivity;
import buying.tickets.gesture.view.GestureMainActivity;
import buying.tickets.gesture.view.GesturePaymentMethodActivity;
import buying.tickets.gesture.view.GestureSummaryActivity;
import buying.tickets.gesture.view.GestureTicketControlActivity;
import buying.tickets.gesture.view.GestureTicketsActivity;
/**
 * Created by Sebastian Paciorek
 */
public class AccelerometerPresenter implements AccelerometerInterface.Presenter, SensorEventListener {

    private static AccelerometerPresenter accelerometerPresenter;

    private Context context;
    private AccelerometerInterface.View view;

    private Gravity gravity;

    private Sensor accelerometerSensor;
    private SensorManager sensorManager;

    private String activity = "tickets";

    private float accelerometerMotion = 0.000000f;
    private float accelerometerCurrent = SensorManager.GRAVITY_EARTH;
    private float accelerometerLast = SensorManager.GRAVITY_EARTH;

    public AccelerometerPresenter() {
        gravity = new Gravity();
    }

    public static AccelerometerPresenter getInstance() {
        if (accelerometerPresenter == null) {
            accelerometerPresenter = new AccelerometerPresenter();
        }
        return accelerometerPresenter;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (accelerometerSensor == event.sensor) {
            float gravityFloats[] = event.values.clone();

            float xLast = gravity.getX();
            float yLast = gravity.getY();
            float zLast = gravity.getZ();

            gravity.setX(gravityFloats[0]);   //left right motion
            gravity.setY(gravityFloats[1]);   // screen down/up
            gravity.setZ(gravityFloats[2]);

            accelerometerLast = accelerometerCurrent;
//            accelerometerCurrent = (float) Math.sqrt(gravity.getX() * gravity.getX() + gravity.getY() * gravity.getY() + gravity.getZ() * gravity.getZ());
            accelerometerCurrent = (float) Math.sqrt(gravity.getY() * gravity.getY());
            float delta = accelerometerCurrent - accelerometerLast;
            accelerometerMotion = accelerometerMotion * 0.9f + delta;

//           Log.d("LOG", "LAST: " + yLast + "          CURRENT: " + gravity.getY());

            if (yLast < gravity.getY()) {

                if (accelerometerMotion > 1.1f) {
                    switch (activity) {
                        case "main":
                            GestureMainPresenter.getInstance().setCurrentItemSelectedDown();
//                            GestureMainActivity.getInstance().stopProgressBar();
//                            GestureMainActivity.getInstance().clearRunnable();
                            GestureMainPresenter.getInstance().setStopProgress(true);
                            break;

                        case "buyTicket":
                            GestureBuyTicketPresenter.getInstance().setCurrentItemSelectedDown();
                            GestureBuyTicketPresenter.getInstance().setStopProgress(true);
                            break;

                        case "tickets":
                            GestureTicketsPresenter.getInstance().setCurrentItemSelectedDown();
                            GestureTicketsActivity.getInstance().notifyDataSetChangedTicketsRecyclerView();
//                            GestureTicketsActivity.getInstance().stopProgressBar();
//                            GestureTicketsActivity.getInstance().clearRunnable();
                            GestureTicketsPresenter.getInstance().setStopProgress(true);
                            break;

                        case "ticketsControl":
                            GestureTicketControlPresenter.getInstance().setCurrentItemSelectedDown();
                            GestureTicketControlActivity.getInstance().notifyDataSetChangedTicketsRecyclerView();
                            GestureTicketControlPresenter.getInstance().setStopProgress(true);
                            break;

                        case "summary":
                            GestureSummaryPresenter.getInstance().setCurrentItemSelectedDown();
                            GestureSummaryActivity.getInstance().stopProgressBar();
                            GestureSummaryActivity.getInstance().clearRunnable();
                            break;

                        case "paymentmethod":
                            GesturePaymentMethodPresenter.getInstance().setCurrentItemSelectedDown();
//                            GesturePaymentMethodActivity.getInstance().stopProgressBar();
//                            GesturePaymentMethodActivity.getInstance().clearRunnable();
                            GesturePaymentMethodPresenter.getInstance().setStopProgress(true);
                            break;
                    }

                } else {
                    switch (activity) {
                        case "main":
                            GestureMainActivity.getInstance().startProgressBar();
                            break;

                        case "buyTicket":
                            GestureBuyTicketActivity.getInstance().startProgressBar();
                            break;

                        case "tickets":
                            GestureTicketsActivity.getInstance().startProgressBar();
                            break;

                        case "ticketsControl":
                            GestureTicketControlActivity.getInstance().startProgressBar();
                            break;

                        case "summary":
                            GestureSummaryActivity.getInstance().startProgressBar();
                            break;

                        case "paymentmethod":
                            GesturePaymentMethodActivity.getInstance().startProgressBar();
                            break;
                    }

                }
            } else if (yLast > gravity.getY()) {

                if (accelerometerMotion > 1.3f) {
//                    GestureTicketsPresenter.getInstance().setCurrentItemSelectedUp();
//                    GestureTicketsActivity.getInstance().notifyDataSetChangedTicketsRecyclerView();
//                    Log.d("LOG", "Element up " + accelerometerMotion + "");
                }
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void getAccelerometerSensorInstance() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            registerAccelerometerListener();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.gesture_sensor_error_title), Toast.LENGTH_SHORT).show();
        }
    }

    private void registerAccelerometerListener() {
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
    }

    @Override
    public void unregisterAccelerometerListener() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(AccelerometerInterface.View view) {
        this.view = view;
    }

    public String getActivity() {
        return activity;
    }

    @Override
    public void setActivity(String activity) {
        this.activity = activity;
    }
}
