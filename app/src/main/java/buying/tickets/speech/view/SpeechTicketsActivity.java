/*
 * Created by Sebastian Paciorek on 8.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 08.03.19 14:52
 */

package buying.tickets.speech.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
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
import java.util.Locale;

import buying.tickets.R;
import buying.tickets.application.TicketsApplication;
import buying.tickets.choosemethod.ChooseMethodActivity;
import buying.tickets.internetConnection.InternetCheck;
import buying.tickets.internetConnection.InternetConnectionInterface;
import buying.tickets.internetConnection.InternetConnectorReceiver;
import buying.tickets.speech.contract.SpeechTicketsInterface;
import buying.tickets.speech.presenter.SpeechTicketsPresenter;
import buying.tickets.touch.model.Ticket;
import buying.tickets.touch.presenter.TicketsRecyclerViewAdapter;
import buying.tickets.touch.view.TouchSummaryActivity;

/**
 * Created by Sebastian Paciorek
 */
public class SpeechTicketsActivity extends AppCompatActivity implements RecognitionListener, InternetConnectionInterface.View, SpeechTicketsInterface.View {

    private static SpeechTicketsActivity speechTicketsActivity;

    private RecyclerView recyclerView;
    private TicketsRecyclerViewAdapter ticketsRecyclerViewAdapter;

    private Button returnButton;
    private TextView internetTextView;
    private TextView listeningActionsInfoTextView;
    private TextView listeningErrorInfoTextView;
    private ProgressBar progressBar;
    private TextView progressInfoTextView;

    private SpeechRecognizer speechRecognizer;
    private Intent recognizerIntent;

    private InternetCheck internetCheck;
    private InternetConnectionInterface.Presenter internetConnectionPresenter;
    private SpeechTicketsInterface.Presenter speechTicketsPresenter;

    private LinearLayoutManager recyclerLayoutManager;
    private DividerItemDecoration dividerItemDecoration;

    private String activity = "tickets";
    private String kindOfTicket = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_tickets);
        setTitle(getResources().getString(R.string.tickets_title));
        speechTicketsActivity = this;

        checkInternetAccess();

        internetConnectionPresenter = new InternetConnectorReceiver();

        speechTicketsPresenter = SpeechTicketsPresenter.getInstance();
        speechTicketsPresenter.setContext(this);
        speechTicketsPresenter.setView(this);

        setComponents();

        setLayoutManager();
        setDividerItemDecoration();

        getExtras();
        setContent();

        showTickets();

        requestRecordAudioPermission();
    }

    private void setComponents() {
        recyclerView = findViewById(R.id.speech_tickets_recyclerview);
        returnButton = findViewById(R.id.speech_tickets_returnButton);
        internetTextView = findViewById(R.id.speech_tickets_internetTextView);
        listeningActionsInfoTextView = findViewById(R.id.speech_tickets_listening_actions_infotextView);
        listeningErrorInfoTextView = findViewById(R.id.speech_tickets_listening_error_infotextView);
        progressBar = findViewById(R.id.speech_tickets_progressBar);
        progressInfoTextView = findViewById(R.id.speech_tickets_progress_infotextView);

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
                speechTicketsPresenter.setDiscountTicketList();
                setTitle(getResources().getString(R.string.discount_ticket_title));
            } else {
                speechTicketsPresenter.setNormalTicketList();
                setTitle(getResources().getString(R.string.normal_ticket_title));
            }
        }
    }

    private void showTickets() {
        List<Ticket> ticketList = speechTicketsPresenter.getTicketList();

        if (ticketList.size() > 0) {
            ticketsRecyclerViewAdapter = new TicketsRecyclerViewAdapter(ticketList, "speech");
        } else {
            ticketsRecyclerViewAdapter = new TicketsRecyclerViewAdapter(new ArrayList<>(), "speech");
        }
        recyclerView.setAdapter(ticketsRecyclerViewAdapter);
        ticketsRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyDataSetChangedTicketsRecyclerView() {
        showTickets();
        ticketsRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void setReturnButton() {
        returnButton.setClickable(false);
    }

    private void checkInternetAccess() {
        internetCheck = new InternetCheck(internet -> {
            if (internet) {
                internetTextView.setVisibility(View.GONE);
            } else {
                internetTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setInternetConnection() {
        internetCheck = new InternetCheck(internet -> {
            internetConnectionPresenter.setConnected(internet);
        });

    }

    @Override
    public void showInternetTextView(boolean isConnected) {
        if (isConnected) {
            internetTextView.setVisibility(View.GONE);
        } else {
            internetTextView.setVisibility(View.VISIBLE);
        }
    }

    private void showListeningErrorInfoMatchInfo(boolean show) {
        if (show) {
            listeningErrorInfoTextView.setVisibility(View.VISIBLE);
        } else {
            listeningErrorInfoTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void setListeningErrorInfoTextView(String message) {
        listeningErrorInfoTextView.setText(message);
    }

    private void showListeningActionsInfoMatchInfo(boolean show) {
        if (show) {
            listeningActionsInfoTextView.setVisibility(View.VISIBLE);
        } else {
            listeningActionsInfoTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setListeningActionsInfoTextView(String message) {
        listeningActionsInfoTextView.setText(message);
    }

    private void requestRecordAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String requiredPermission = Manifest.permission.RECORD_AUDIO;

            if (checkCallingOrSelfPermission(requiredPermission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{requiredPermission}, 101);

            } else {
                setInternetConnection();
                if (speechRecognizer == null)promptSpeechInput();
                if (internetConnectionPresenter.isConnected()) startListening();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 101) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        return;
                    } else {
                        setInternetConnection();
                        checkIsRecognitionAvailable();
                        if (speechRecognizer == null)promptSpeechInput();
                        if (internetConnectionPresenter.isConnected()) startListening();
                    }
                }
            }
        }
    }

    private void checkIsRecognitionAvailable() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, getResources().getString(R.string.speech_recognition_available), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.speech_recognition_unavailable), Toast.LENGTH_SHORT).show();
        }
    }

    private void promptSpeechInput() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
    }

    private void startListening() {
        speechRecognizer.startListening(recognizerIntent);
    }

    public static SpeechTicketsActivity getInstance() {
        return speechTicketsActivity;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        setListeningActionsInfoTextView(getResources().getString(R.string.speech_on_ready_for_speech_message));
    }

    @Override
    public void onBeginningOfSpeech() {
        setListeningActionsInfoTextView(getResources().getString(R.string.speech_on_ready_for_speech_message) + "\n" + getResources().getString(R.string.speech_beginning_of_speech_message));
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        checkInternetAccess();
        TicketsApplication.activityResumed();
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
    }

    @Override
    public void onEndOfSpeech() {
        setListeningActionsInfoTextView(getResources().getString(R.string.speech_on_ready_for_speech_message) + "\n" + getResources().getString(R.string.speech_end_of_speech_message));
    }

    @Override
    public void onError(int error) {
        showListeningErrorInfoMatchInfo(true);
        switch (getErrorText(error)) {

            case "Audio recording error":
                setListeningErrorInfoTextView(getResources().getString(R.string.speech_audio_recording_error_message));
                break;
            case "Client side error":
                setListeningErrorInfoTextView(getResources().getString(R.string.speech_client_side_error_message));
                break;
            case "Insufficient permissions":
                setListeningErrorInfoTextView(getResources().getString(R.string.speech_insufficient_permission_error_message));
                break;
            case "Network error":
                setListeningErrorInfoTextView(getResources().getString(R.string.speech_network_error_message));
                showInternetTextView(false);
                setInternetConnection();
                break;
            case "Network timeout":
                setListeningErrorInfoTextView(getResources().getString(R.string.speech_network_timeout_error_message));
                break;
            case "No match":
                setListeningErrorInfoTextView(getResources().getString(R.string.speech_no_match_error_message));
                break;
            case "RecognitionService busy":
                setListeningErrorInfoTextView(getResources().getString(R.string.speech_recognition_service_busy_error_message));
                break;
            case "error from server":
                setListeningErrorInfoTextView(getResources().getString(R.string.speech_error_from_server_message));
                break;
            case "No speech input":
                setListeningErrorInfoTextView(getResources().getString(R.string.speech_no_speech_input_error__message));
                break;
        }
        setCountDownTimerStartListening();

    }

    @Override
    public void onResults(Bundle results) {
        setListeningActionsInfoTextView(getResources().getString(R.string.speech_checking_for_results_message));
        ArrayList<String> voiceResults = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        float[] confidenceScores = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);

        if (voiceResults != null) {
            if (confidenceScores != null) {
                if (confidenceScores.length > 0) {
                    if (confidenceScores[0] >= Float.valueOf(getResources().getString(R.string.accepted_confidence))) {
                        checkResults(voiceResults.get(0));
                    } else {
                        showListeningErrorInfoMatchInfo(true);
                        setListeningErrorInfoTextView(getResources().getString(R.string.speech_results_checked_error_message));
                        setCountDownTimerStartListening();
                    }
                } else {
                    showListeningErrorInfoMatchInfo(true);
                    setListeningErrorInfoTextView(getResources().getString(R.string.speech_results_checked_error_message));
                    setCountDownTimerStartListening();
                }
            } else {
                showListeningErrorInfoMatchInfo(true);
                setListeningErrorInfoTextView(getResources().getString(R.string.speech_results_checked_error_message));
                setCountDownTimerStartListening();
            }
        } else {
            showListeningErrorInfoMatchInfo(true);
            setListeningErrorInfoTextView(getResources().getString(R.string.speech_results_checked_error_message));
            setCountDownTimerStartListening();
        }
    }

    private void checkResults(String results) {
        speechTicketsPresenter.findMatch(results);
    }

    private void setCountDownTimerStartActivity() {
        showProgressBar(true);
        showProgressInfo(true);
        new CountDownTimer(2000, 10) {

            public void onTick(long millisUntilFinished) {
                int value = (int) (100 - (float) (millisUntilFinished / 2000.0) * 100);
                setProgressBarValue(value);
            }

            public void onFinish() {
                showProgressBar(false);
                showProgressInfo(false);
                switch (activity) {
                    case "tickets":
                        startSummaryActivity();
                        break;

                    case "buyTicket":
                        startBuyTicketActivity();
                        break;
                }
            }
        }.start();
    }

    @Override
    public void setCountDownTimerStartListening() {
        new CountDownTimer(2000, 10) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                showListeningErrorInfoMatchInfo(false);
                setInternetConnection();
                stopListening();
                promptSpeechInput();
                startListening();
            }
        }.start();
    }

    @Override
    public void stopListening() {
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
        speechRecognizer = null;
    }

    @Override
    public void setSelectedReturnButton(boolean isSelected) {
        if (isSelected) {
            returnButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.selected_button_color)));
        } else {
            returnButton.setBackgroundColor(Color.parseColor(getResources().getString(R.string.unselected_button_color)));
        }
    }

    @Override
    public void startSummaryActivity() {
        Intent intent = new Intent(this, TouchSummaryActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void startBuyTicketActivity() {
        Intent intent = new Intent(this, SpeechBuyTicketActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setProgressBarValue(int value) {
        progressBar.setProgress(value);
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgressInfo(boolean show) {
        if (show) {
            progressInfoTextView.setVisibility(View.VISIBLE);
        } else {
            progressInfoTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    @Override
    protected void onStart() {
        super.onStart();
        TicketsApplication.activityResumed();
        checkInternetAccess();
        if (speechRecognizer == null){
            promptSpeechInput();
            startListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TicketsApplication.activityResumed();
        checkInternetAccess();
        if (speechRecognizer == null){
            promptSpeechInput();
            startListening();
        }
    }

    @Override
    protected void onPause() {
        TicketsApplication.activityPaused();
        checkInternetAccess();
        stopListening();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopListening();
    }

    @Override
    public void onBackPressed() {
        //disable back button
//        super.onBackPressed();
    }
}
