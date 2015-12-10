package studentcompany.sportgest.Games;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Game;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Game> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
     static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public AppCompatTextView mTextView;

        public ViewHolder(LinearLayout layout) {
            super(layout);

            for (int i=0; i<layout.getChildCount(); i++) {

                View v = layout.getChildAt(i);

                switch (v.getId()){

                    case R.id.text_view:
                        mTextView = (AppCompatTextView) v;
                        break;
                }
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Game> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
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

        if (game.getHome_team() != null)
            holder.mTextView.setText(game.getReport());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}