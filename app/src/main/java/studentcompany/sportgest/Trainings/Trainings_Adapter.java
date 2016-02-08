package studentcompany.sportgest.Trainings;

import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Training;


public class Trainings_Adapter extends RecyclerView.Adapter<Trainings_Adapter.ViewHolder> {

    private static Training_Fragment_List.OnItemSelected mListener;
    private List<Training> mDataset;

    private int tag;
    private int currentPos = -1;
    private ViewHolder currentVH = null;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private Trainings_Adapter su;
        public AppCompatTextView mTextView_name;
        public CardView parent;

        public ViewHolder(View view, Trainings_Adapter su) {
            super(view);
            view.setOnClickListener(this);

            this.su = su;
            parent = (CardView)view;
            mTextView_name = (AppCompatTextView)view.findViewById(R.id.text_view);
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

    public Trainings_Adapter(List<Training> myDataset, Training_Fragment_List.OnItemSelected itemListener, int tag) {
        this.mDataset = myDataset;
        this.mListener = itemListener;
        this.tag = tag;
    }

    @Override
    public Trainings_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v, this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Training training = mDataset.get(position);

        if (training != null) {
            holder.mTextView_name.setText(training.getTitle());
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


    public Training getCurrentItem(){
        if(currentPos>=0)
            return mDataset.get(currentPos);
        else
            return null;
    }
}