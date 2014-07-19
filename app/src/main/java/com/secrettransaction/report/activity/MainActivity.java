package com.secrettransaction.report.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.report_server.status.Status;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.secrettransaction.report.R;

import java.io.IOException;


public class MainActivity extends Activity {

    TextView clientId, title, message, latestVersion;
    Status api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clientId = (TextView) findViewById(R.id.clientIDTextView);
        title = (TextView) findViewById(R.id.titleTextView);
        message = (TextView) findViewById(R.id.messageTextView);
        latestVersion = (TextView) findViewById(R.id.latestVersionTextView);

        Button statusCheckButton = (Button) findViewById(R.id.statusCheckButton);
        statusCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusCheck();
            }
        });

        JsonFactory JSON_FACTORY = new AndroidJsonFactory();
        HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();

        Status.Builder builder = new Status.Builder(HTTP_TRANSPORT, JSON_FACTORY, null);
        api = builder.build();
    }

    public void statusCheck() {

        AsyncTask<Void, Void, com.appspot.report_server.status.model.Status> getStatusTask = new AsyncTask<Void, Void, com.appspot.report_server.status.model.Status>() {
            @Override
            protected com.appspot.report_server.status.model.Status doInBackground(Void... params) {

                com.appspot.report_server.status.model.Status status = null;

                try {
                    com.appspot.report_server.status.Status.StatusAPI.GetStatus s  = api.statusAPI().getStatus("Android V1");
                    status = s.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return status;
            }

            @Override
            protected void onPostExecute(com.appspot.report_server.status.model.Status status) {
               if (status != null) {
                   clientId.setText(String.format("Client ID: %s", status.getClientId()));
                   title.setText(String.format("Title: %s", status.getTitle()));
                   message.setText(String.format("Message: %s", status.getMessage()));
                   latestVersion.setText(String.format("Latest Version: %s", status.getLatestVersion()));
               }
            }
        };

        getStatusTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
