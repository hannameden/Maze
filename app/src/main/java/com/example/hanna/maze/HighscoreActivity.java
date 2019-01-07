package com.example.hanna.maze;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

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

    }


}
