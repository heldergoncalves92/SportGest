package studentcompany.sportgest.Attributes;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.Attributes.ListAttribute_Fragment;
import studentcompany.sportgest.domains.Attribute;


public class Attributes_Adapter extends RecyclerView.Adapter<Attributes_Adapter.ViewHolder> {
    private Context context;
    private static ListAttribute_Fragment.OnItemSelected mListener;
    private List<Attribute> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public AppCompatTextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = (AppCompatTextView)view.findViewById(R.id.text_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.itemSelected(getLayoutPosition());
        }
    }

    public Attributes_Adapter(List<Attribute> myDataset, Context context, ListAttribute_Fragment.OnItemSelected itemListener) {
        this.mDataset = myDataset;
        this.context = context;
        this.mListener = itemListener;
    }

    @Override
    public Attributes_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Attribute attribute = mDataset.get(position);

        if (attribute != null) {
            holder.mTextView.setText(attribute.getName());
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}