package com.example.hanna.maze;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HighscoreListAdapter extends RecyclerView.Adapter<HighscoreListAdapter.ViewHolder> {

    private List<Highscore> list;

    public HighscoreListAdapter(List<Highscore> list){
        this.list = list;
    }

    @NonNull
    @Override
    public HighscoreListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemview, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighscoreListAdapter.ViewHolder viewHolder, int i) {

        Highscore highscore = list.get(i);

        viewHolder.playerRankTextView.setText(Integer.toString(highscore.getPlayerRank()));
        viewHolder.playerNameTextView.setText(highscore.getPlayerName());
        viewHolder.playerTimeTextView.setText(Double.toString(highscore.getPlayerTime()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView playerRankTextView;
        TextView playerNameTextView;
        TextView playerTimeTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //textView = (TextView) itemView.findViewById(R.id.textView);
            playerRankTextView = (TextView) itemView.findViewById(R.id.player_rank);
            playerNameTextView = (TextView) itemView.findViewById(R.id.player_name);
            playerTimeTextView = (TextView) itemView.findViewById(R.id.player_time);
        }
    }


}
