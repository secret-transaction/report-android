package com.secrettransaction.report.activity;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.secrettransaction.report.ORMHelper;
import com.secrettransaction.report.R;

import com.secrettransaction.report.entity.Report;

import java.sql.SQLException;

/**
 * A fragment representing a single Report detail screen.
 * This fragment is either contained in a {@link ReportListActivity}
 * in two-pane mode (on tablets) or a {@link ReportDetailActivity}
 * on handsets.
 */
public class ReportDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The Report being displayed right now
     */
    private Report report;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReportDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            Long reportId = getArguments().getLong(ARG_ITEM_ID);
            try {
                //TODO: not sure if this is the right place to fetch from database
                Dao<Report, Long> dao = ORMHelper.sharedInstance().getDao(Report.class);
                report = dao.queryForId(reportId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (report != null) {
            ((TextView) rootView.findViewById(R.id.report_detail)).setText(report.getTitle());
        }

        return rootView;
    }
}
