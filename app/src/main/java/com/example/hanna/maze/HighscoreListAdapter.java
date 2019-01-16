package com.example.hanna.maze;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Used to display Highscore objects in a RecyclerView object in the HighScoreActivity class.
 *
 * @author Hanna Medén, Niklas Nordgren
 * @version 2019-01-16
 */
public class HighscoreListAdapter extends RecyclerView.Adapter<HighscoreListAdapter.ViewHolder> {

    private List<Highscore> list;

    public HighscoreListAdapter(List<Highscore> list){
        this.list = list;
    }

    /**
     * {@inheritDoc}
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public HighscoreListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemview, viewGroup, false);

        return new ViewHolder(view);
    }

    /**
     * {@inheritDoc}
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull HighscoreListAdapter.ViewHolder viewHolder, int i) {

        Highscore highscore = list.get(i);

        viewHolder.playerRankTextView.setText(Integer.toString(highscore.getPlayerRank()));
        viewHolder.playerNameTextView.setText(highscore.getPlayerName());
        viewHolder.playerTimeTextView.setText(Double.toString(highscore.getPlayerTime()));

    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Used by each individual Highscore object to display its values in three different TextView objects
     * that are identified by using findViewById.
     */
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
