package studentcompany.sportgest.Exercises;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Exercise;

public class Exercise_List_Adapter extends RecyclerView.Adapter<Exercise_List_Adapter.ViewHolder> {

    private Context context;
    private static Exercise_Fragment_List.OnItemSelected mListener;
    private List<Exercise> mDataset;

    private int tag;
    private int currentPos = -1;
    private ViewHolder currentVH = null;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case

        private Exercise_List_Adapter su = null;
        public AppCompatTextView mTextView_name;
        public CardView parent;


        public ViewHolder(View view, Exercise_List_Adapter su) {
            super(view);
            view.setOnClickListener(this);

            this.su = su;
            parent = (CardView) view;
            mTextView_name = (AppCompatTextView)view.findViewById(R.id.role_name);
        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();

            //if(su.currentPos != pos){ //->Training Create exercise needs two clicks
            parent.setCardBackgroundColor(Color.parseColor("#ccebff"));
            su.itemSelected(this, getLayoutPosition());
            mListener.itemSelected(getLayoutPosition(), su.tag);
            //} else
            if(su.currentPos == pos && su.tag != 0){
                focus_loss();
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
    public Exercise_List_Adapter(List<Exercise> myDataset, Exercise_Fragment_List.OnItemSelected mListener, int tag) {

        this.mDataset = myDataset;
        this.mListener = mListener;
        this.tag = tag;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Exercise_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.role_card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v, this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Exercise exercise = mDataset.get(position);

        if (exercise != null) {
            holder.mTextView_name.setText(exercise.getTitle());

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


    public Exercise getCurrentItem(){
        if(currentPos>=0)
            return mDataset.get(currentPos);
        else
            return null;
    }

}