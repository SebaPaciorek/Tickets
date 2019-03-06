/*
 * Created by Sebastian Paciorek on 6.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 06.03.19 23:54
 */

package buying.tickets.gesture.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import buying.tickets.R;
import buying.tickets.gesture.contract.AccelerometerInterface;
import buying.tickets.gesture.contract.GestureTicketControlInterface;
import buying.tickets.gesture.presenter.GestureSummaryPresenter;
import buying.tickets.gesture.presenter.GestureTicketControlDetailsPresenter;
import buying.tickets.gesture.presenter.GestureTicketControlPresenter;
import buying.tickets.gesture.presenter.sensor.AccelerometerPresenter;
import buying.tickets.touch.model.Ticket;
import buying.tickets.touch.presenter.BoughtTicketRecyclerViewAdapter;
import buying.tickets.touch.presenter.TouchTicketControlPresenter;
/**
 * Created by Sebastian Paciorek
 */
public class GestureTicketControlActivity extends AppCompatActivity implements AccelerometerInterface.View, GestureTicketControlInterface.View {

    private static GestureTicketControlActivity gestureTicketControlActivity;

    private GestureTicketControlInterface.Presenter gestureTicketControlPresenter;
    private AccelerometerInterface.Presenter accelerometerPresenter;

    private RecyclerView boughtTicketsRecyclerView;
    private BoughtTicketRecyclerViewAdapter boughtTicketRecyclerViewAdapter;

    private LinearLayoutManager recyclerLayoutManager;
    private DividerItemDecoration dividerItemDecoration;

    private ProgressBar progressBar;
    private TextView progressInfoTextView;
    private Button returnButton;

    private Handler timerHandler;
    private Runnable runnable = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_ticketcontrol);
        setTitle(getResources().getString(R.string.ticket_control_title));

        gestureTicketControlActivity = this;

        accelerometerPresenter = AccelerometerPresenter.getInstance();
        accelerometerPresenter.setContext(this);
        accelerometerPresenter.setView(this);
        accelerometerPresenter.setActivity("ticketsControl");
        accelerometerPresenter.getAccelerometerSensorInstance();

        gestureTicketControlPresenter = GestureTicketControlPresenter.getInstance();
        gestureTicketControlPresenter.setContext(this);
        gestureTicketControlPresenter.setView(this);

        setComponents();
        setLayoutManager();
        setDividerItemDecoration();

        showTickets();
        setInitialSelection();
    }

    private void setComponents() {
        boughtTicketsRecyclerView = findViewById(R.id.gesture_bought_ticketsRecyclerView);
        progressBar = findViewById(R.id.tickets_control_progressBar);
        progressInfoTextView = findViewById(R.id.tickets_control_progress_infotextView);
        returnButton = findViewById(R.id.gesture_ticketcontrol_returnButton);

        setReturnButton();
    }

    private void setLayoutManager() {
        recyclerLayoutManager = new LinearLayoutManager(this);
        boughtTicketsRecyclerView.setLayoutManager(recyclerLayoutManager);
    }

    private void setDividerItemDecoration() {
        dividerItemDecoration = new DividerItemDecoration(boughtTicketsRecyclerView.getContext(), recyclerLayoutManager.getOrientation());
        boughtTicketsRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void showTickets() {
        List<Ticket> ticketList = gestureTicketControlPresenter.getBoughtTicketsList();

        if (ticketList != null) {
            if (ticketList.size() > 0) {
                boughtTicketRecyclerViewAdapter = new BoughtTicketRecyclerViewAdapter(ticketList, "gesture");
            } else {
                boughtTicketRecyclerViewAdapter = new BoughtTicketRecyclerViewAdapter(new ArrayList<>(), "gesture");
            }
        } else {
            boughtTicketRecyclerViewAdapter = new BoughtTicketRecyclerViewAdapter(new ArrayList<>(), "gesture");
        }

        boughtTicketsRecyclerView.setAdapter(boughtTicketRecyclerViewAdapter);
        boughtTicketRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void setInitialSelection() {
        List<Ticket> ticketList = gestureTicketControlPresenter.getBoughtTicketsList();

        if (ticketList.size() > 0) {
            gestureTicketControlPresenter.setCurrentItemSelected(0);
            gestureTicketControlPresenter.setReturnButtonSelected(false);
        } else {
            gestureTicketControlPresenter.setCurrentItemSelected(-1);
            gestureTicketControlPresenter.setReturnButtonSelected(true);
        }
    }

    public void setProgressBar() {
        setProgressBarValue(0);
        timerHandler = new Handler();

        setRunnable();
    }

    public void startProgressBar() {
        setProgressBar();
    }

    private void setRunnable() {
        if (runnable == null) {
            setProgressBarValue(0);

            runnable = new Runnable() {
                int i = 0;

                @Override
                public void run() {

                    if (progressBar.getProgress() == progressBar.getMax()) {
                        accelerometerPresenter.unregisterAccelerometerListener();
                        timerHandler.removeCallbacks(this);
                        stopProgressBar();
                        startChooseActivity();
                    } else if (gestureTicketControlPresenter.isStopProgress()) {
                        timerHandler.removeCallbacks(this);
                        stopProgressBar();
                        clearRunnable();
                        gestureTicketControlPresenter.setStopProgress(false);
                    } else {
                        i++;
                        showProgressBar(true);
                        showProgressInfo(true);
                        setProgressBarValue(i);
                        timerHandler.postDelayed(this, 50);
                    }

                }
            };
            showProgressBar(true);
            showProgressInfo(true);
            timerHandler.postDelayed(runnable, 1000);
        }

    }

    public void clearRunnable() {
        runnable = null;
    }

    public void stopProgressBar() {
        timerHandler.removeCallbacks(runnable);
        showProgressBar(false);
        showProgressInfo(false);
    }

    private void setProgressBarValue(int value) {
        progressBar.setProgress(value);
    }

    private void setReturnButton() {
        returnButton.setClickable(false);
    }

    private void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void showProgressInfo(boolean show) {
        if (show) {
            progressInfoTextView.setVisibility(View.VISIBLE);
        } else {
            progressInfoTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void startChooseActivity() {
        if (gestureTicketControlPresenter.isReturnButtonSelected()) {
            startMainActivity();
        } else {
            startTicketControlDetailsActivity(gestureTicketControlPresenter.getBoughtTicketsList().get(gestureTicketControlPresenter.getCurrentItemSelected()));
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, GestureMainActivity.class);
        startActivity(intent);
        finish();
    }

    public void startTicketControlDetailsActivity(Ticket ticket) {
        GestureTicketControlDetailsPresenter.getInstance().setTicket(ticket);
        Intent intent = new Intent(this, GestureTicketControlDetailsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void notifyDataSetChangedTicketsRecyclerView() {
        showTickets();
        boughtTicketsRecyclerView.scrollToPosition(gestureTicketControlPresenter.getCurrentItemSelected()-3);
        boughtTicketRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void setSelectedReturnButton(boolean isSelected) {
        if (isSelected) {
            returnButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.selected_button_color)));
        } else {
            returnButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.unselected_button_color)));
        }
    }

    public static GestureTicketControlActivity getInstance() {
        return gestureTicketControlActivity;
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopProgressBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearRunnable();
    }

    @Override
    public void onBackPressed() {
        //disable back button
//        super.onBackPressed();
    }


}
