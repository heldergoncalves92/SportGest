package studentcompany.sportgest.EventCategories;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.EventCategory;

public class EventCategory_List_Adapter extends RecyclerView.Adapter<EventCategory_List_Adapter.ViewHolder> {

    private static EventCategory_Fragment_List.OnItemSelected mListener;
    private List<EventCategory> mDataset;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public AppCompatTextView mTextView_name;
        public View parent;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            parent = view;
            mTextView_name = (AppCompatTextView)view.findViewById(R.id.role_name);
        }

        @Override
        public void onClick(View v) {
            mListener.itemSelected(getLayoutPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventCategory_List_Adapter(List<EventCategory> myDataset, EventCategory_Fragment_List.OnItemSelected mListener) {

        this.mDataset = myDataset;
        this.mListener = mListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventCategory_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.role_card_item, parent, false);
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
        EventCategory positionObj = mDataset.get(position);

        if (positionObj != null) {
            holder.mTextView_name.setText(positionObj.getName());

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
