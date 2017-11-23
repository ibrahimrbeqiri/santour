package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.group4.santour.R;

import java.util.List;

import models.JournalEntry;

/**
 * Created by Emile on 23.11.2017.
 */

//Custom Adapter class to put the List of Journal into the listView
public class TestAdapter extends ArrayAdapter<JournalEntry> {

    private TextView title;
    private TextView content;

    public TestAdapter(Context context, List<JournalEntry> journal) {
        super(context, 0, journal);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        JournalEntry journal = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_test, parent, false);
        }
        // Lookup view for data population
        title = convertView.findViewById(R.id.titleTest);
        content = convertView.findViewById(R.id.contentTest);
        // Populate the data into the template view using the data object
        title.setText(journal.getTitle());
        content.setText(journal.getContent());
        // Return the completed view to render on screen
        return convertView;
    }
}
