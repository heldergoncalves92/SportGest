package studentcompany.sportgest.Trainings;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studentcompany.sportgest.Exercises.ListExercise_Fragment;
import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Exercise;
import studentcompany.sportgest.domains.Training;


public class Trainings_Adapter extends RecyclerView.Adapter<Trainings_Adapter.ViewHolder> {
    private Context context;
    private static ListTraining_Fragment.OnItemSelected mListener;
    private List<Training> mDataset;

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

    public Trainings_Adapter(List<Training> myDataset, Context context, ListTraining_Fragment.OnItemSelected itemListener) {
        this.mDataset = myDataset;
        this.context = context;
        this.mListener = itemListener;
    }

    @Override
    public Trainings_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Training training = mDataset.get(position);

        if (training != null) {
            holder.mTextView.setText(training.getTitle());
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}