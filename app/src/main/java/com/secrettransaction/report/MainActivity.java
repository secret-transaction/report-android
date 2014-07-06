package com.secrettransaction.report;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    TextView clientId, title, message, latestVersion;

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
    }

    public void statusCheck() {
        Toast.makeText(this, "Not Yet Implemented", Toast.LENGTH_LONG).show();
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
