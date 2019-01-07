package com.example.hanna.maze;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        writeToDatabase();

        listData = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot childDataSnapShot : dataSnapshot.child("highscore").getChildren()){

                    int playerRank = Integer.parseInt(childDataSnapShot.child("playerRank").getValue().toString());
                    String playerName = childDataSnapShot.child("playerName").getValue().toString();
                    double playerTime = Double.parseDouble(childDataSnapShot.child("playerTime").getValue().toString());

                    listData.add(new Highscore(playerRank, playerName, playerTime));
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new HighscoreListAdapter(listData);
        recyclerView.setAdapter(adapter);

    }

    private void writeToDatabase(){

        Highscore highscore1 = new Highscore(1, "Hanna Medén", 15.1);
        databaseReference.child("highscore").child("1").setValue(highscore1);

        Highscore highscore2 = new Highscore(2, "Niklas Nordgren", 16.2);
        databaseReference.child("highscore").child("2").setValue(highscore2);

        Highscore highscore3 = new Highscore(3, "Niklas2 Nordgren", 17.2);
        databaseReference.child("highscore").child("3").setValue(highscore3);

        Highscore highscore4 = new Highscore(4, "Niklas3 Nordgren", 18.2);
        databaseReference.child("highscore").child("4").setValue(highscore4);

        Highscore highscore5 = new Highscore(5, "Niklas4 Nordgren", 19.2);
        databaseReference.child("highscore").child("5").setValue(highscore5);

    }


}
