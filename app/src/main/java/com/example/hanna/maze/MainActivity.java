package com.example.hanna.maze;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static int width;
    public static int height;

    private Spinner spinner;

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

        text = findViewById(R.id.text);

        spinner = (Spinner) findViewById(R.id.spinnerSize);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sizes_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Object spinnerChoice = parent.getItemAtPosition(position);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void startGame(View v) {

        String choice = spinner.getSelectedItem().toString();

        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra( "choice", choice);

        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(gameIntent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        startActivity(gameIntent);
    }
}
