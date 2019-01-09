package com.example.hanna.maze;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HighscoreActivity extends AppCompatActivity {

    private static final String TAG = "HighscoreActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Highscore> listData;

    private DatabaseReference databaseReference;

    private ValueEventListener valueEventListener;

    private boolean isButtonLevel1Pressed, isButtonLevel2Pressed, isButtonLevel3Pressed;
    private boolean isButton5x5Pressed, isButton10x10Pressed, isButton15x15Pressed;

    private Button buttonLevel1, buttonLevel2, buttonLevel3;
    private Button button5x5, button10x10, button15x15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        buttonLevel1 = (Button) findViewById(R.id.button_level_1);
        buttonLevel2 = (Button) findViewById(R.id.button_level_2);
        buttonLevel3 = (Button) findViewById(R.id.button_level_3);

        button5x5 = (Button) findViewById(R.id.button_5x5);
        button10x10 = (Button) findViewById(R.id.button_10x10);
        button15x15 = (Button) findViewById(R.id.button_15x15);

        buttonLevel1.setClickable(false);
        buttonLevel2.setClickable(false);
        buttonLevel3.setClickable(false);

        button5x5.setClickable(false);
        button10x10.setClickable(false);
        button15x15.setClickable(false);

        buttonLevel1.setBackgroundResource(android.R.drawable.btn_default);
        button5x5.setBackgroundResource(android.R.drawable.btn_default);

        buttonLevel2.setBackgroundResource(android.R.drawable.btn_default);
        buttonLevel3.setBackgroundResource(android.R.drawable.btn_default);

        button10x10.setBackgroundResource(android.R.drawable.btn_default);
        button15x15.setBackgroundResource(android.R.drawable.btn_default);

        buttonLevel1.getBackground().setColorFilter(buttonLevel1.getContext().getResources().getColor(R.color.colorDarkGray), PorterDuff.Mode.MULTIPLY);
        button5x5.getBackground().setColorFilter(button5x5.getContext().getResources().getColor(R.color.colorDarkGray), PorterDuff.Mode.MULTIPLY);

        isButton5x5Pressed = true;
        isButtonLevel1Pressed = true;

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        //writeToDatabase();

        listData = new ArrayList<>();

        databaseReference.addValueEventListener(valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot childDataSnapShot : dataSnapshot.child("highscore").child("5x5").child("level1").getChildren()){

                    int playerRank = Integer.parseInt(childDataSnapShot.child("playerRank").getValue().toString());
                    String playerName = childDataSnapShot.child("playerName").getValue().toString();
                    double playerTime = Double.parseDouble(childDataSnapShot.child("playerTime").getValue().toString());

                    listData.add(new Highscore(playerRank, playerName, playerTime));
                }

                adapter.notifyDataSetChanged();

                buttonLevel1.setClickable(true);
                buttonLevel2.setClickable(true);
                buttonLevel3.setClickable(true);

                button5x5.setClickable(true);
                button10x10.setClickable(true);
                button15x15.setClickable(true);

                databaseReference.removeEventListener(valueEventListener);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        adapter = new HighscoreListAdapter(listData);
        recyclerView.setAdapter(adapter);

    }

    public void onClickButtonLevel1(View v){

        isButtonLevel1Pressed = true;
        isButtonLevel2Pressed = false;
        isButtonLevel3Pressed = false;

        buttonLevel1.getBackground().setColorFilter(buttonLevel1.getContext().getResources().getColor(R.color.colorDarkGray), PorterDuff.Mode.MULTIPLY);
        buttonLevel2.getBackground().clearColorFilter();
        buttonLevel3.getBackground().clearColorFilter();

        readFromDatabase();
    }

    public void onClickButtonLevel2(View v){

        isButtonLevel1Pressed = false;
        isButtonLevel2Pressed = true;
        isButtonLevel3Pressed = false;

        buttonLevel2.getBackground().setColorFilter(buttonLevel2.getContext().getResources().getColor(R.color.colorDarkGray), PorterDuff.Mode.MULTIPLY);
        buttonLevel1.getBackground().clearColorFilter();
        buttonLevel3.getBackground().clearColorFilter();

        readFromDatabase();
    }

    public void onClickButtonLevel3(View v){

        isButtonLevel1Pressed = false;
        isButtonLevel2Pressed = false;
        isButtonLevel3Pressed = true;

        buttonLevel3.getBackground().setColorFilter(buttonLevel3.getContext().getResources().getColor(R.color.colorDarkGray), PorterDuff.Mode.MULTIPLY);
        buttonLevel1.getBackground().clearColorFilter();
        buttonLevel2.getBackground().clearColorFilter();

        readFromDatabase();
    }

    public void onClickButton5x5(View v){

        isButton5x5Pressed = true;
        isButton10x10Pressed = false;
        isButton15x15Pressed = false;

        button5x5.getBackground().setColorFilter(buttonLevel1.getContext().getResources().getColor(R.color.colorDarkGray), PorterDuff.Mode.MULTIPLY);
        button10x10.getBackground().clearColorFilter();
        button15x15.getBackground().clearColorFilter();

        readFromDatabase();
    }

    public void onClickButton10x10(View v){
        isButton5x5Pressed = false;
        isButton10x10Pressed = true;
        isButton15x15Pressed = false;

        button10x10.getBackground().setColorFilter(buttonLevel1.getContext().getResources().getColor(R.color.colorDarkGray), PorterDuff.Mode.MULTIPLY);
        button5x5.getBackground().clearColorFilter();
        button15x15.getBackground().clearColorFilter();

        readFromDatabase();
    }

    public void onClickButton15x15(View v){
        isButton5x5Pressed = false;
        isButton10x10Pressed = false;
        isButton15x15Pressed = true;

        button15x15.getBackground().setColorFilter(buttonLevel1.getContext().getResources().getColor(R.color.colorDarkGray), PorterDuff.Mode.MULTIPLY);
        button5x5.getBackground().clearColorFilter();
        button10x10.getBackground().clearColorFilter();

        readFromDatabase();
    }

    private void readFromDatabase(){

        if(isButtonLevel1Pressed){
            if(isButton5x5Pressed){

                readFromDatabaseHelperMethod("5x5", "level1");

            }else if(isButton10x10Pressed){

                readFromDatabaseHelperMethod("10x10", "level1");

            }else if(isButton15x15Pressed){

                readFromDatabaseHelperMethod("15x15", "level1");

            }
        }else if(isButtonLevel2Pressed){
            if(isButton5x5Pressed){

                readFromDatabaseHelperMethod("5x5", "level2");

            }else if(isButton10x10Pressed){

                readFromDatabaseHelperMethod("10x10", "level2");

            }else if(isButton15x15Pressed){

                readFromDatabaseHelperMethod("15x15", "level2");

            }
        }else if(isButtonLevel3Pressed){
            if(isButton5x5Pressed){

                readFromDatabaseHelperMethod("5x5", "level3");

            }else if(isButton10x10Pressed){

                readFromDatabaseHelperMethod("10x10", "level3");

            }else if(isButton15x15Pressed){

                readFromDatabaseHelperMethod("15x15", "level3");

            }
        }

    }

    private void readFromDatabaseHelperMethod(final String mazeSize, final String level){

        listData.clear();

        databaseReference.addValueEventListener(valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot childDataSnapShot : dataSnapshot.child("highscore").child(mazeSize).child(level).getChildren()){

                    int playerRank = Integer.parseInt(childDataSnapShot.child("playerRank").getValue().toString());
                    String playerName = childDataSnapShot.child("playerName").getValue().toString();
                    double playerTime = Double.parseDouble(childDataSnapShot.child("playerTime").getValue().toString());

                    listData.add(new Highscore(playerRank, playerName, playerTime));
                }

                adapter.notifyDataSetChanged();

                databaseReference.removeEventListener(valueEventListener);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
