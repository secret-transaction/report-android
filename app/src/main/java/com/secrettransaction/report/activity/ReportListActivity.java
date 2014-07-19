package com.secrettransaction.report.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.j256.ormlite.dao.Dao;
import com.secrettransaction.report.ORMHelper;
import com.secrettransaction.report.R;
import com.secrettransaction.report.entity.Report;
import com.secrettransaction.report.entity.ReportImage;

import java.sql.SQLException;

/**
 * An activity representing a list of Reports. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ReportDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ReportListFragment} and the item details
 * (if present) is a {@link ReportDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ReportListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ReportListActivity extends Activity implements ReportListFragment.Callbacks {

    private static final String TAG = ReportListActivity.class.getSimpleName();

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        if (findViewById(R.id.report_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ReportListFragment) getFragmentManager()
                    .findFragmentById(R.id.report_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link ReportListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(Long id) {
        Log.d(TAG, String.format("Selected:%s", id));

        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putLong(ReportDetailFragment.ARG_ITEM_ID, id);
            ReportDetailFragment fragment = new ReportDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.report_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ReportDetailActivity.class);
            detailIntent.putExtra(ReportDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.report_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add:
                addReport();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addReport() {
        ORMHelper orm = ORMHelper.sharedInstance();

        try {
            Dao<Report, Long> reportDao = orm.getDao(Report.class);
            Dao<ReportImage, Long> reportImageDao = orm.getDao(ReportImage.class);

            Report report = new Report();
            report.setTitle("Test Title");
            report.setDetail("Test Detail");
            int rowUpdated = reportDao.create(report);

            ReportImage image1 = new ReportImage();
            image1.setReport(report);

            ReportImage image2 = new ReportImage();
            image2.setReport(report);

            rowUpdated += reportImageDao.create(image1);
            rowUpdated += reportImageDao.create(image2);

            Log.d(TAG, String.format("(%s row updated)R:%s, I1:%s, I2:%s", rowUpdated, report, image1, image2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
