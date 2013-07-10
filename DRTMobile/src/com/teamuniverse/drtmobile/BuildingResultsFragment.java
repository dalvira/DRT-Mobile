package com.teamuniverse.drtmobile;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.att.intern.webservice.Incident;
import com.att.intern.webservice.Webservice;
import com.att.intern.webservice.Webservice.TokenInvalidException;
import com.teamuniverse.drtmobile.sectionsetup.SectionDetailActivity;
import com.teamuniverse.drtmobile.sectionsetup.SectionListActivity;
import com.teamuniverse.drtmobile.support.DatabaseManager;
import com.teamuniverse.drtmobile.support.SetterUpper;

/**
 * A fragment representing a single Section detail screen. This fragment is
 * either contained in a {@link SectionListActivity} in two-pane mode (on
 * tablets) or a {@link SectionDetailActivity} on handsets.
 */
public class BuildingResultsFragment extends Fragment {
	/** The shortcut to the current activity */
	private static Activity		m;
	/** The progress bar that is shown to indicate background processes */
	private static ProgressBar	progress;
	/** A boolean that will stop many clicks from starting a bunch of threads */
	private static boolean		querying;
	/** The handler that will allow the multi-threading */
	private Handler				handler;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public BuildingResultsFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_building_results, container, false);
		SetterUpper.setup(m, view);
		
		progress = (ProgressBar) view.findViewById(R.id.progress);
		querying = false;
		handler = new Handler();
		
		LinearLayout list = (LinearLayout) view.findViewById(R.id.list_container);
		search(list);
		
		return view;
	}
	
	ArrayList<Incident>	results	= null;
	boolean				success	= false;
	
	private void search(LinearLayout containerRef) {
		final LinearLayout container = containerRef;
		// Get contents of the EditTexts
		if (!querying) {
			querying = true;
			new Thread(new Runnable() {
				public void run() {
					Webservice ws = new Webservice(m);
					
					DatabaseManager db = new DatabaseManager(m);
					String token = db.sessionGet("token");
					int zip = Integer.parseInt(db.sessionGet("zip"));
					db.close();
					
					try {
						results = ws.geolocSearch(token, zip);
						success = true;
					} catch (TokenInvalidException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					handler.postDelayed(new Runnable() {
						public void run() {
							// Hide the progress bar
							progress.setVisibility(View.GONE);
							
							if (success) {
								if (results.size() == 0) {
									TextView temp = new TextView(m);
									temp.setText(R.string.no_results);
									temp.setGravity(Gravity.CENTER_HORIZONTAL);
									container.addView(temp);
								} else for (int i = 0; i < results.size(); i++) {
									// TODO add each search result here
									TextView temp = new TextView(m);
									temp.setText("Hello #" + i);
									container.addView(temp);
								}
							} else SetterUpper.timedOut(m);
							
						}
					}, 0);
				}
			}).start();
		}
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