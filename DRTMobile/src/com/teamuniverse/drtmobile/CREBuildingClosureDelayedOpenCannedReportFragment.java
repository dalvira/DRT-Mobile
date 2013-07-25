package com.teamuniverse.drtmobile;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.att.intern.webservice.Incident;
import com.att.intern.webservice.Webservice;
import com.att.intern.webservice.Webservice.TokenInvalidException;
import com.teamuniverse.drtmobile.sectionsetup.SectionDetailActivity;
import com.teamuniverse.drtmobile.sectionsetup.SectionListActivity;
import com.teamuniverse.drtmobile.support.DatabaseManager;
import com.teamuniverse.drtmobile.support.IncidentInfo;
import com.teamuniverse.drtmobile.support.SectionAdder;
import com.teamuniverse.drtmobile.support.SetterUpper;

/**
 * A fragment representing a single Section detail screen. This fragment is
 * either contained in a {@link SectionListActivity} in two-pane mode (on
 * tablets) or a {@link SectionDetailActivity} on handsets.
 */
public class CREBuildingClosureDelayedOpenCannedReportFragment extends Fragment {
	private final static int[]		ORDER	= { IncidentInfo.EVENT_NAME, IncidentInfo.INITIAL_REPORT_DATE, IncidentInfo.BUILDING_STATUS, IncidentInfo.ZIP_CODE, IncidentInfo.BUILDING_ADDRESS, IncidentInfo.STATE, IncidentInfo.BUILDING_TYPE, IncidentInfo.PM_ATTUID, IncidentInfo.ASSESSMENT_NOTES, IncidentInfo.STATUS_NOTES };
	/** The shortcut to the current activity */
	private static Activity			m;
	/** The progress bar that is shown to indicate background processes */
	private static ProgressBar		progress;
	/** The handler that will allow the multi-threading */
	private static Handler			handler;
	private static DatabaseManager	db;
	
	private static int				year;
	private static String			eventName;
	
	public static LinearLayout		list;
	public static View				view;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public CREBuildingClosureDelayedOpenCannedReportFragment() {
	}
	
	/**
	 * Define the Activity object m to facilitate use later in the activity.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m = getActivity();
	}
	
	/**
	 * Call SetterUpper.setup() to fill in the ATTUID and authorization of the
	 * current user.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (SectionListActivity.backButtonPressed) {
			SectionListActivity.backStackViews.pop();
			View restoring = SectionListActivity.backStackViews.peek();
			((FrameLayout) restoring.getParent()).removeView(restoring);
			return restoring;
		} else {
			View view = inflater.inflate(R.layout.fragment_cre_building_closure_delayed_open_canned_report, container, false);
			SetterUpper.setup(m, view);
			
			db = new DatabaseManager(m);
			year = Integer.parseInt(db.sessionUnset("event_year"));
			eventName = db.sessionUnset("event_name");
			((TextView) view.findViewById(R.id.subtitle)).setText(eventName + " of " + year);
			db.close();
			progress = (ProgressBar) view.findViewById(R.id.progress);
			handler = new Handler();
			list = (LinearLayout) view.findViewById(R.id.list_container);
			
			search(list);
			
			SectionListActivity.backStackViews.add(view);
			return view;
		}
	}
	
	static ArrayList<Incident>	results;
	static boolean				success;
	static boolean				timedOutDuringSearch;
	
	public static void search(final LinearLayout container) {
		results = new ArrayList<Incident>(0);
		success = false;
		timedOutDuringSearch = false;
		try {
			progress.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Thread(new Runnable() {
			public void run() {
				Webservice ws = new Webservice(m);
				
				db = new DatabaseManager(m);
				String token = db.sessionGet("token");
				db.close();
				
				try {
					results = ws.getCREReport(token, eventName, year);
					success = true;
				} catch (TokenInvalidException e) {
					timedOutDuringSearch = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.postDelayed(new Runnable() {
					public void run() {
						
						try {
							// Hide the progress bar
							progress.setVisibility(View.GONE);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (success) {
							SetterUpper.fillIn(m, container, results, ORDER);
						} else if (timedOutDuringSearch) {
							SetterUpper.timedOut(m, SectionAdder.CRE_BUILDING_CLOSURE_DELAYED_OPEN_CANNED_REPORT);
						}
					}
				}, 0);
			}
		}).start();
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
}