package studentcompany.sportgest.Games;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Event;

/**
 * Created by heldergoncalves on 20/01/16.
 */
public class Game_GameMode_History_List_Adapter extends RecyclerView.Adapter<Game_GameMode_History_List_Adapter.ViewHolder> {

    private static Game_Fragment_GameMode_History.OnItemSelected mListener;
    private List<Event> mDataset;

    private int tag;
    private int currentPos = -1;
    private ViewHolder currentVH = null;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case

        private Game_GameMode_History_List_Adapter su = null;
        public AppCompatTextView mTextView_num,mTextView_name,mTextView_color,mTextView_time;
        public CardView parent;


        public ViewHolder(View view, Game_GameMode_History_List_Adapter su) {
            super(view);
            view.setOnClickListener(this);

            this.su = su;
            parent = (CardView) view;
            mTextView_num = (AppCompatTextView)view.findViewById(R.id.event_number);
            mTextView_name = (AppCompatTextView)view.findViewById(R.id.event_name);
            mTextView_color = (AppCompatTextView)view.findViewById(R.id.event_color);
            mTextView_time = (AppCompatTextView)view.findViewById(R.id.event_time);
        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();

            if(su.currentPos != pos){
                parent.setCardBackgroundColor(Color.parseColor("#ccebff"));
                su.itemSelected(this, getLayoutPosition());
                mListener.itemSelected(getLayoutPosition(), su.tag);

            } else if(su.tag != 0){
                focus_loss();
                mListener.itemDesselected(pos, su.tag);
            }
        }

        public void focus_loss() {
            parent.setCardBackgroundColor(Color.WHITE);
            su.currentPos=-1;
            su.currentVH=null;
        }

        public void focus_gain() {
            parent.setCardBackgroundColor(Color.parseColor("#ccebff"));
            su.currentVH = this;
            su.currentPos = getLayoutPosition();
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Game_GameMode_History_List_Adapter(List<Event> myDataset, Game_Fragment_GameMode_History.OnItemSelected mListener, int tag) {

        this.mDataset = myDataset;
        this.mListener = mListener;
        this.tag = tag;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Game_GameMode_History_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_events_history_card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v, this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Event event = mDataset.get(position);

        if (event != null) {
            int mins = 0, secs = event.getDate();
            String time = "";
            if(event.getEventCategory().hasTimestamp())
            {
                mins = secs / 60;
                secs = secs % 60;
                time = mins+":"+secs;
            }

            holder.mTextView_name.setText(event.getPlayer().getNickname());
            holder.mTextView_num.setText(event.getPlayer().getNumber()+"");
            holder.mTextView_time.setText(time);
            holder.mTextView_color.setTextColor(event.getEventCategory().getColor());
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

    public int getCurrentPos(){
        return currentPos;
    }


    public Event getCurrentItem(){
        if(currentPos>=0)
            return mDataset.get(currentPos);
        else
            return null;
    }

}
