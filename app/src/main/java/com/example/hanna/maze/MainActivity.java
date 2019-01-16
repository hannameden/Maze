package com.example.hanna.maze;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * This is the start of our programme. Where we start the game.
 * @author Hanna Medén, Niklas Nordgren
 * @version 2019-01-16
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";

    public static int width;
    public static int height;

    public static int statusBarHeight;

    private Spinner spinnerSize;
    private Spinner spinnerLevel;

    private int size, level;

    private LinearLayout linearLayout;

    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.main_layout);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            linearLayout.setBackgroundResource(R.drawable.bgflip);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);

        spinnerSize = (Spinner) findViewById(R.id.spinnerSize);
        spinnerSize.setGravity(Gravity.CENTER_HORIZONTAL);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sizes_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerSize.setAdapter(adapter);

        spinnerLevel = (Spinner) findViewById(R.id.spinnerLevel);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.level_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerLevel.setAdapter(adapter2);
    }

    /**
     * Currently not being used.
     * {@inheritDoc}
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * Currently not being used.
     * {@inheritDoc}
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Method for what happens when you press the Start button.
     * It extracts and sets the size and level of the maze and starts
     * the countdown timer.
     * @param v
     */
    public void startGame(View v) {

        String stringSize = spinnerSize.getSelectedItem().toString();

        if (stringSize.equals("10 x 10")) {
            size = 10;
        } else if (stringSize.equals("5 x 5")) {
            size = 5;
        } else {
            size = 15;
        }

        level = Integer.parseInt(spinnerLevel.getSelectedItem().toString());

        countDown();

    }

    /**
     * This starts a timer of 3 seconds in the form of a popup, when finished,
     * it starts the game.
     */
    public void countDown(){

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Be ready")
                .setMessage("The game will start in.. ")
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            private static final int AUTO_DISMISS_MILLIS = 3000;
            @Override
            public void onShow(final DialogInterface dialog) {
                final Button defaultButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                final CharSequence positiveButtonText = defaultButton.getText();
                new CountDownTimer(AUTO_DISMISS_MILLIS, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        ((AlertDialog) dialog).setMessage("The game will start in.. " +String.format(
                                Locale.getDefault(), "%s %d",
                                positiveButtonText,
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1 //add one so it never displays zero
                        ));
                    }
                    @Override
                    public void onFinish() {
                        if (((AlertDialog) dialog).isShowing()) {

                            startGameActivity();
                            dialog.dismiss();
                        }
                    }
                }.start();
            }
        });
        dialog.show();
    }

    /**
     * A method for starting the GameActivity.
     */
    public void startGameActivity(){

        Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
        gameIntent.putExtra("size", size);
        gameIntent.putExtra("level", level);

        startActivity(gameIntent);

    }

    /**
     * A method for starting the HigscoreActivity.
     * @param view
     */
    public void startHighscoreActivity(View view) {

        Intent hsIntent = new Intent(this, HighscoreActivity.class);

        startActivity(hsIntent);

    }
}
