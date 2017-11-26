package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.group4.santour.R;

import java.util.List;

import models.Track;

/**
 * Created by Emile on 23.11.2017.
 */

//Custom Adapter class to put the List of Journal into the listView
public class ListAdapter extends ArrayAdapter<Track> {

    private TextView title;
    private TextView content;

    public ListAdapter(Context context, List<Track> journal) {
        super(context, 0, journal);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Track track = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_track, parent, false);
        }
        // Lookup view for data population
        title = convertView.findViewById(R.id.titleTest);
        content = convertView.findViewById(R.id.contentTest);
        // Populate the data into the template view using the data object
        title.setText(track.getNameTrack());
        content.setText(track.getDescriptionTrack());
        // Return the completed view to render on screen
        return convertView;
    }
}