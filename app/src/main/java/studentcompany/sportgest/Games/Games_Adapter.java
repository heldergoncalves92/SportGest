package studentcompany.sportgest.Games;

import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Game;


public class Games_Adapter extends RecyclerView.Adapter<Games_Adapter.ViewHolder> {
    private List<Game> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
     static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public AppCompatTextView mTextView, mTextView2, score1, score2;
        public View parent;

        public ViewHolder(View view) {
            super(view);

            parent = view;
/*
            mTextView = (AppCompatTextView)view.findViewById(R.id.name_home);
            mTextView2 = (AppCompatTextView)view.findViewById(R.id.name_visitor);
            score1 = (AppCompatTextView)view.findViewById(R.id.score_home);
            score2 = (AppCompatTextView)view.findViewById(R.id.score_visitor);
            */

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Games_Adapter(List<Game> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Games_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_game_base, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);
        Game game = mDataset.get(position);

        if (game.getHome_team() != null) {
            /*
            holder.mTextView.setText(game.getReport());
            holder.mTextView2.setText(game.getReport());
            holder.score1.setText(String.valueOf(game.getHome_score()));
            holder.score2.setText(String.valueOf(game.getVisitor_score()));
            */
        }


        if (position%2 ==0)
            holder.parent.setBackgroundColor(Color.LTGRAY);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}