/*
 * Created by Sebastian Paciorek on 7.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 07.03.19 00:13
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import buying.tickets.R;
import buying.tickets.gesture.contract.AccelerometerInterface;
import buying.tickets.gesture.contract.GestureTicketsInterface;
import buying.tickets.gesture.presenter.GestureSummaryPresenter;
import buying.tickets.gesture.presenter.GestureTicketsPresenter;
import buying.tickets.gesture.presenter.sensor.AccelerometerPresenter;
import buying.tickets.touch.model.Ticket;
import buying.tickets.touch.presenter.TicketsRecyclerViewAdapter;
/**
 * Created by Sebastian Paciorek
 */
public class GestureTicketsActivity extends AppCompatActivity implements AccelerometerInterface.View, GestureTicketsInterface.View {

    private static GestureTicketsActivity gestureTicketsActivity;

    private AccelerometerInterface.Presenter accelerometerPresenter;
    private GestureTicketsInterface.Presenter gestureTicketsPresenter;

    private RecyclerView recyclerView;
    private TicketsRecyclerViewAdapter ticketsRecyclerViewAdapter;

    private ProgressBar progressBar;
    private TextView progressInfoTextView;
    private Button returnButton;

    private LinearLayoutManager recyclerLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private String kindOfTicket = null;

    private Handler timerHandler;
    private Runnable runnable = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_tickets);
        setTitle(getResources().getString(R.string.tickets_title));

        gestureTicketsActivity = this;

        accelerometerPresenter = AccelerometerPresenter.getInstance();
        accelerometerPresenter.setContext(this);
        accelerometerPresenter.setView(this);
        accelerometerPresenter.setActivity("tickets");
        accelerometerPresenter.getAccelerometerSensorInstance();

        gestureTicketsPresenter = GestureTicketsPresenter.getInstance();
        gestureTicketsPresenter.setContext(this);
        gestureTicketsPresenter.setView(this);

        setComponents();

        setLayoutManager();
        setDividerItemDecoration();

        getExtras();
        setContent();

        showTickets();
        setInitialSelection();
    }

    private void setComponents() {
        recyclerView = findViewById(R.id.gesture_tickets_recyclerview);
        progressBar = findViewById(R.id.tickets_progressBar);
        progressInfoTextView = findViewById(R.id.tickets_progress_infotextView);
        returnButton = findViewById(R.id.gesture_ticket_returnButton);

        setReturnButton();
    }

    private void setLayoutManager() {
        recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerLayoutManager);
    }

    private void setDividerItemDecoration() {
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), recyclerLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void getExtras() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            kindOfTicket = bundle.getString("ticket");
        }
    }

    private void setContent() {
        if (kindOfTicket != null) {
            if (kindOfTicket.equals("discount")) {
                gestureTicketsPresenter.setDiscountTicketList();
                setTitle(getResources().getString(R.string.discount_ticket_title));
            } else {
                gestureTicketsPresenter.setNormalTicketList();
                setTitle(getResources().getString(R.string.normal_ticket_title));
            }
        }
    }

    private void setInitialSelection() {
        List<Ticket> ticketList = gestureTicketsPresenter.getTicketList();

        if (ticketList.size() > 0) {
            gestureTicketsPresenter.setCurrentItemSelected(0);
            gestureTicketsPresenter.setReturnButtonSelected(false);
        } else {
            gestureTicketsPresenter.setCurrentItemSelected(-1);
            gestureTicketsPresenter.setReturnButtonSelected(true);
        }
    }

    private void showTickets() {
        List<Ticket> ticketList = gestureTicketsPresenter.getTicketList();

        if (ticketList.size() > 0) {
            ticketsRecyclerViewAdapter = new TicketsRecyclerViewAdapter(ticketList, "gesture");
        } else {
            ticketsRecyclerViewAdapter = new TicketsRecyclerViewAdapter(new ArrayList<>(), "gesture");
        }
        recyclerView.setAdapter(ticketsRecyclerViewAdapter);
        ticketsRecyclerViewAdapter.notifyDataSetChanged();
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
                    } else if (gestureTicketsPresenter.isStopProgress()) {
                        timerHandler.removeCallbacks(this);
                        stopProgressBar();
                        clearRunnable();
                        gestureTicketsPresenter.setStopProgress(false);
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

    private void startChooseActivity() {
        if (gestureTicketsPresenter.isReturnButtonSelected()) {
            startBuyTicketActivity();
        } else {
            startSummaryActivity(gestureTicketsPresenter.getTicketList().get(gestureTicketsPresenter.getCurrentItemSelected()));
        }
    }

    private void startBuyTicketActivity() {
        Intent intent = new Intent(this, GestureBuyTicketActivity.class);
        startActivity(intent);
        finish();
    }

    public void startSummaryActivity(Ticket ticket) {
        GestureSummaryPresenter.getInstance().setTicket(ticket);
        Intent intent = new Intent(this, GestureSummaryActivity.class);
        startActivity(intent);
        finish();
    }

    private void setReturnButton() {
        returnButton.setClickable(false);
    }

    @Override
    public void setSelectedReturnButton(boolean isSelected) {
        if (isSelected) {
            returnButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.selected_button_color)));
        } else {
            returnButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.unselected_button_color)));
        }
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

    @Override
    public void notifyDataSetChangedTicketsRecyclerView() {
        showTickets();
        recyclerView.scrollToPosition(gestureTicketsPresenter.getCurrentItemSelected());
        ticketsRecyclerViewAdapter.notifyDataSetChanged();
    }

    public static GestureTicketsActivity getInstance() {
        return gestureTicketsActivity;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
