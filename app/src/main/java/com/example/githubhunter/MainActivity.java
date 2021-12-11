package com.example.githubhunter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubhunter.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText searchBox;
    TextView urlDisplay;
    TextView searchResults;


    public class gitHubQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String gitHubSearchResults = null;

            try {
                gitHubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return gitHubSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
                showJsonData();
                searchResults.setText(s);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.launch) {
            Log.i("MainActivity", "El usuario ha pulsado search");

            Context context = MainActivity.this;
            Toast.makeText(context, R.string.launch_pressed, Toast.LENGTH_LONG).show();
            URL githubUrl = NetworkUtils.buildUrl(searchBox.getText().toString());
            urlDisplay.setText(githubUrl.toString());

            new gitHubQueryTask().execute(githubUrl);
        }else if(itemId == R.id.clear){
            urlDisplay.setText("");
            searchBox.setText("");
            searchResults.setText("");

        }
        return true;
    }

    private void showJsonData(){
        searchResults.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBox = (EditText) findViewById(R.id.search_box);
        urlDisplay = (TextView) findViewById(R.id.url_display);
        searchResults = (TextView) findViewById(R.id.github_search_results);
    }
}