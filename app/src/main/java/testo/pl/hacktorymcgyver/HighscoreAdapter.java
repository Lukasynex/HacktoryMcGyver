package testo.pl.hacktorymcgyver;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Lukasz Marczak on 2015-06-22.
 */
public class HighscoreAdapter extends RecyclerView.Adapter<HighscoreAdapter.ViewHolder> {
    public static final String TAG = HighscoreAdapter.class.getSimpleName();
    private static List<Record> mDataset = null;
    private static Context context = null;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View v;
        public TextView dataName;
        public TextView dataResult;
        public ImageView beaconIcon;


        public ViewHolder(View v) {
            super(v);
            this.v = v;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getPosition();
            if ((mDataset.size() < position) || mDataset.size() == 0)
                return;


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HighscoreAdapter(Context parent) {
        context = parent;
        mDataset = Config.getHighScores();
        Collections.sort(mDataset, new Comparator<Record>() {
            @Override
            public int compare(Record fruite1, Record fruite2) {

                return fruite1.compareTo(fruite2);
            }
        });
//        Set<Record> set = new HashSet<>();
//        Set<Record> valuesSet = new HashSet<>();
//        List<Record> arr = new ArrayList<>();
//        ArrayList<String> values = new ArrayList<>();
//
//        for (Record rec : mDataset) {
//            values.add(rec.value);
//            valuesSet.add(rec);
//        }
//
//        for (Record rec : mDataset) {
//            set.add(rec);
//        }
//
//        for (Record rec : set) {
//            arr.add(rec);
//        }
//        mDataset = arr;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_favourites_item_view, parent, false);
        //  v.setOnClickListener(new OnSuggestionClickListener());
        ViewHolder vh = new ViewHolder(v);
        vh.dataName = (TextView) v.findViewById(R.id.list_favourites_item_textview);
        vh.dataResult = (TextView) v.findViewById(R.id.result);
        vh.beaconIcon = (ImageView) v.findViewById(R.id.list_favourites_item_imageview);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "inBindViewHolder");
        if (mDataset.size() <= position || mDataset.size() == 0)
            return;
        Record thisRecord = mDataset.get(position);
        holder.dataName.setText(thisRecord.name);
        holder.dataResult.setText(thisRecord.value);

        int imageResource = (thisRecord.name.equals(Config.GREEN_BEACON_NAME)) ? R.drawable.pist : R.drawable.purple;
        holder.beaconIcon.setImageResource(imageResource);

    }

    @Override
    public int getItemCount() {
        if (mDataset == null) {
            Log.e(TAG, "getItemCount: mDataset is null!");
            return 0;
        }
        return mDataset.size();
    }

}