package studentcompany.sportgest.Exercises;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Exercise;


public class Exercises_Adapter extends RecyclerView.Adapter<Exercises_Adapter.ViewHolder> {
    private Context context;
    private static ListExercise_Fragment.OnItemSelected mListener;
    private List<Exercise> mDataset;

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

    public Exercises_Adapter(List<Exercise> myDataset, Context context, ListExercise_Fragment.OnItemSelected itemListener) {
        this.mDataset = myDataset;
        this.context = context;
        this.mListener = itemListener;
    }

    @Override
    public Exercises_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_exercise_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exercise exercise = mDataset.get(position);

        if (exercise != null) {
            holder.mTextView.setText(exercise.getTitle());
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}