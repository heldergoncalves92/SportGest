package studentcompany.sportgest.Games;

import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studentcompany.sportgest.Games.Game_Fragment_list;
import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Exercise;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Team;

/**
 * Created by heldergoncalves on 05/01/16.
 */
public class GAdapter extends RecyclerView.Adapter<GAdapter.ViewHolder> {

    private static Game_Fragment_list.OnItemSelected mListener;
    private List<Game> mDataset;

    private int currentPos = -1;
    private ViewHolder currentVH = null;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private GAdapter su;
        public AppCompatTextView mTextView, mTextView2, score1, score2;
        public CardView parent;

        public ViewHolder(View view, GAdapter su) {
            super(view);
            view.setOnClickListener(this);

            this.su = su;
            parent = (CardView)view;
            mTextView = (AppCompatTextView)view.findViewById(R.id.name_home);
            mTextView2 = (AppCompatTextView)view.findViewById(R.id.name_visitor);
            score1 = (AppCompatTextView)view.findViewById(R.id.score_home);
            score2 = (AppCompatTextView)view.findViewById(R.id.score_visitor);


        }

        @Override
        public void onClick(View v) {
            parent.setCardBackgroundColor(Color.parseColor("#ccebff"));
            su.itemSelected(this, getLayoutPosition());

            mListener.itemSelected(getLayoutPosition());
        }


        public void focus_loss() {
            parent.setCardBackgroundColor(Color.WHITE);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GAdapter(List<Game> myDataset, Game_Fragment_list.OnItemSelected mListener) {

        this.mDataset = myDataset;
        this.mListener = mListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_game_base, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v, this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Game game = mDataset.get(position);

        if (game != null) {
            holder.mTextView.setText(game.getHome_team().getName());
            holder.mTextView2.setText(game.getVisitor_team().getName());
            holder.score1.setText(String.valueOf(game.getHome_score()));
            holder.score2.setText(String.valueOf(game.getVisitor_score()));
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void itemSelected(ViewHolder vh, int position){

        if(currentPos != -1 && currentVH != null){
            currentVH.focus_loss();
        }

        currentVH = vh;
        currentPos = position;
    }
}
