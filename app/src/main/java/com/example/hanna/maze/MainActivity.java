package com.example.hanna.maze;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";

    public static int width;
    public static int height;

    public static int statusBarHeight;

    private Spinner spinnerSize;
    private Spinner spinnerLevel;

    private TextView text;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);

        // text = findViewById(R.id.text);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void startGame(View v) {

        String size = spinnerSize.getSelectedItem().toString();
        int level = Integer.parseInt(spinnerLevel.getSelectedItem().toString());




/*

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Be ready")
                .setMessage("The game will start in.. ")
                .setNegativeButton("hej", null)
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            private static final int AUTO_DISMISS_MILLIS = 1000;
            @Override
            public void onShow(final DialogInterface dialog) {
                final Button defaultButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                final CharSequence positiveButtonText = defaultButton.getText();
                new CountDownTimer(AUTO_DISMISS_MILLIS, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        defaultButton.setText(String.format(
                                Locale.getDefault(), "%s (%d)",
                                positiveButtonText,
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1 //add one so it never displays zero

                        ));
                    }
                    @Override
                    public void onFinish() {
                        if (((AlertDialog) dialog).isShowing()) {

                            dialog.dismiss();
                        }
                    }
                }.start();
            }
        });
        dialog.show();
*/



        Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
        gameIntent.putExtra("size", size);
        gameIntent.putExtra("level", level);
        PendingIntent pendingIntent = TaskStackBuilder.create(MainActivity.this)
                .addNextIntentWithParentStack(gameIntent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        startActivity(gameIntent);







/*


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle("Game starts in ")

                .setMessage(" ");

        builder.show();*/


    }


    public void startHighscore(View view) {

        Intent hsIntent = new Intent(this, HighscoreActivity.class);

        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(hsIntent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        startActivity(hsIntent);
    }
}
