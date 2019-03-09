/*
 * Created by Sebastian Paciorek on 9.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 08.03.19 21:31
 */

package buying.tickets.gesture.presenter.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import buying.tickets.R;
import buying.tickets.gesture.contract.RotationInterface;
import buying.tickets.gesture.model.Rotation;
/**
 * Created by Sebastian Paciorek
 */
public class RotationPresenter implements RotationInterface.Presenter, SensorEventListener {

    private static RotationPresenter rotationPresenter;

    private Context context;
    private RotationInterface.View view;

    private Rotation rotation;

    private Sensor rotationSensor;
    private SensorManager sensorManager;

    private static final float RADIAN_2_DEGREE = -57.2957795f;

    private RotationPresenter() {
        getRotationSensorInstance();
        rotation = new Rotation();
    }

    public static RotationPresenter getInstance() {
        if (rotationPresenter == null) {
            rotationPresenter = new RotationPresenter();
        }
        return rotationPresenter;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (rotationSensor == event.sensor) {
            float[] rotationMatrix = new float[9];
            float[] adjustedRotationMatrix = new float[9];
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);

            int AXIS_X = SensorManager.AXIS_X;
            int AXIS_Z = SensorManager.AXIS_Z;

            SensorManager.remapCoordinateSystem(rotationMatrix, AXIS_X, AXIS_Z, adjustedRotationMatrix);
            float[] orientation = new float[3];

            SensorManager.getOrientation(adjustedRotationMatrix, orientation);

            rotation.setAzimuth(orientation[0] * -RADIAN_2_DEGREE);
            rotation.setPitch(orientation[1] * RADIAN_2_DEGREE);
            rotation.setRoll(orientation[2] * -RADIAN_2_DEGREE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void getRotationSensorInstance() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null) {
            rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            registerRotationListener();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.gesture_sensor_error_title), Toast.LENGTH_SHORT).show();
        }
    }

    private void registerRotationListener() {
        sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void unregisterSensorListener() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setView(RotationInterface.View view) {
        this.view = view;
    }
}
