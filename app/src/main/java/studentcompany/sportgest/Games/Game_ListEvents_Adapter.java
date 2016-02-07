package studentcompany.sportgest.Games;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Event;
import studentcompany.sportgest.domains.EventCategory;

import static studentcompany.sportgest.R.id.event_count;
import static studentcompany.sportgest.R.id.event_name;


public class Game_ListEvents_Adapter extends RecyclerView.Adapter<Game_ListEvents_Adapter.ViewHolder> {
    private List<Event> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView_num, mTextView_name;
        public View parent;

        public ViewHolder(View view) {
            super(view);

            parent = view;
            mTextView_num = (TextView)view.findViewById(event_count);
            mTextView_name = (TextView)view.findViewById(event_name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Game_ListEvents_Adapter(List<Event> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_events_list_item, parent, false);
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
        Event event = mDataset.get(position);

        if (event != null) {
            holder.mTextView_num.setText(String.valueOf(event.getId()));
            holder.mTextView_name.setText("Um nome!! Editar!");

            if (position%2 ==0)
                holder.parent.setBackgroundColor(Color.LTGRAY);

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}