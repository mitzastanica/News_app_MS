package com.example.android.news_app_ms;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class NewsAdapter extends ArrayAdapter<News> {

    NewsAdapter(Context context, List<News> newsApp) {
        super(context, 0, newsApp);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView;
        listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);

        // Find the article news at the given position in the list of News.
        News currentNews;
        currentNews = getItem(position);

        //Find the TextView with ID title
        TextView articleTitleTextView = listItemView.findViewById(R.id.title);
        assert currentNews != null;
        articleTitleTextView.setText(currentNews.getmTitle());

        //Find the TextView with ID section
        TextView articleSectionChip = listItemView.findViewById(R.id.section);
        articleSectionChip.setText(currentNews.getmSection());

        // Find the TextView with view ID date
        TextView dateView = null;
        TextView timeView = null;
        if (currentNews.getmDateTime() != null) {
            dateView = listItemView.findViewById(R.id.date);
            // Format the date string (i.e. "Mar 3, 1984")
            String formattedDate = formatDate(currentNews.getmDateTime()).concat(",");
            // Display the date of the current news in that TextView
            dateView.setText(formattedDate);

            // Find the TextView with view ID time
            timeView = listItemView.findViewById(R.id.time);
            // Format the time string (i.e. "4:30PM")
            String formattedTime = formatTime(currentNews.getmDateTime());
            // Display the time of the current news in that TextView
            timeView.setText(formattedTime);

            //Set date & time views as visible
            dateView.setVisibility(View.VISIBLE);
            timeView.setVisibility(View.VISIBLE);
        } else {
            //Set date & time views as gone
            dateView.setVisibility(View.GONE);
            timeView.setVisibility(View.GONE);
        }


        //Find the TextView with the ID author
        TextView authorTextView = listItemView.findViewById(R.id.author);
        authorTextView.setText(currentNews.getmAuthor());
        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
