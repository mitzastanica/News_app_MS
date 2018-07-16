package com.example.android.news_app_ms;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String NEWS_REQUEST_URL = "https://content.guardianapis.com/search?api-key=9fd861b2-8300-4629-836c-1dfaa83eb30f";
    private static final int NEWS_LOADER_ID = 1;
    private TextView mEmptyStateTextView;
    private NewsAdapter mAdapter;

    //Context
    private Context currentContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set context
        currentContext = this;

        ListView NewsListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = findViewById(R.id.empty_view);
        NewsListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        NewsListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected news.
        NewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current news that was clicked on
                News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri NewsUri = Uri.parse(currentNews.getmUrl());

                // Create a new intent to view the News URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, NewsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }

        });

        //News loader
        final LoaderCallbacks<List<News>> newsLoader = new LoaderCallbacks<List<News>>() {
            @Override
            public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

                // Create a new loader for the given URL
                return new NewsLoader(currentContext, NEWS_REQUEST_URL);
            }

            @Override
            public void onLoadFinished(Loader<List<News>> loader, List<News> news) {

                // Hide loading indicator because the data has been loaded
                View loadingIndicator = findViewById(R.id.loading_indicator);
                loadingIndicator.setVisibility(View.GONE);

                // Set empty state text to display "No news found."
                mEmptyStateTextView.setText(R.string.no_news);

                // If there is a valid list of {@link News}, then add them to the adapter's
                // data set. This will trigger the ListView to update.
                if (news != null && !news.isEmpty()) {
                    mAdapter.addAll(news);
                }
            }

            @Override
            public void onLoaderReset(Loader<List<News>> loader) {
                // Loader reset, so we can clear out our existing data.
                mAdapter.clear();
            }
        };

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loaders. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, newsLoader);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);

        }

    }
}