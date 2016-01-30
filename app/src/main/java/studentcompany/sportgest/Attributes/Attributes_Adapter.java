package studentcompany.sportgest.Attributes;

import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Attribute;


public class Attributes_Adapter extends RecyclerView.Adapter<Attributes_Adapter.ViewHolder> {

    private static Attribute_Fragment_List.OnItemSelected mListener;
    private List<Attribute> mDataset;

    private int tag;
    private int currentPos = -1;
    private ViewHolder currentVH = null;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case

        private Attributes_Adapter su = null;
        public AppCompatTextView mTextView_name;
        public CardView parent;


        public ViewHolder(View view, Attributes_Adapter su) {
            super(view);
            view.setOnClickListener(this);

            this.su = su;
            parent = (CardView) view;
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

    public Attributes_Adapter(List<Attribute> myDataset, Attribute_Fragment_List.OnItemSelected itemListener, int tag) {
        this.mDataset = myDataset;
        this.mListener = itemListener;
        this.tag = tag;
    }

    @Override
    public Attributes_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v, this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Attribute attribute = mDataset.get(position);

        if (attribute != null) {
            holder.mTextView_name.setText(attribute.getName());
        }

    }

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


    public Attribute getCurrentItem(){
        if(currentPos>=0)
            return mDataset.get(currentPos);
        else
            return null;
    }
}