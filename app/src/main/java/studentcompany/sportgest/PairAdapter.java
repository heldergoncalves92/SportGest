package studentcompany.sportgest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import studentcompany.sportgest.daos.Pair;

// We can create custom adapters
class PairAdapter<A,B> extends ArrayAdapter<Pair<A,B>> {
    private int resource;
    private int textViewReource;
    private int pairPosition;

    public PairAdapter(Context context, int resource, List<Pair<A,B>> values, int pairPosition, int textViewReource){
        super(context, resource, values);
        this.resource = resource;
        this.textViewReource = textViewReource;
        this.pairPosition = pairPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(resource, parent, false);

        String text;
        switch(pairPosition){
            case 1:
                text = getItem(position).getFirst().toString();
                break;
            case 2:
                text = getItem(position).getSecond().toString();
                break;
            default:
                text = "N/A";
        }

        // Get the TextView we want to edit
        TextView theTextView = (TextView) theView.findViewById(textViewReource);

        // Put the next TV Show into the TextView
        theTextView.setText(text);

        return theView;

    }
}