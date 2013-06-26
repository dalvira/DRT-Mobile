package com.teamuniverse.drtmobile;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

/**
 * A fragment representing a single Section detail screen. This fragment is
 * either contained in a {@link SectionListActivity} in two-pane mode (on
 * tablets) or a {@link SectionDetailActivity} on handsets.
 */
public class BuildingSearchResultsFragment extends Fragment {
	private static ProgressBar	progress;
	private static boolean		querying;
	private Handler				handler;
	private Activity			m;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public BuildingSearchResultsFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_building_search_results, container, false);
		
		progress = (ProgressBar) view.findViewById(R.id.zip_progress);
		querying = false;
		handler = new Handler();
		m = getActivity();
		
		LinearLayout list = (LinearLayout) view.findViewById(R.id.zip_results_list);
		search(list);
		
		return view;
	}
	
	ArrayList<Incident>	results	= null;
	AlertDialog.Builder	builder	= null;
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
						// 1. Instantiate an AlertDialog.Builder with its
						// constructor
						builder = new AlertDialog.Builder(m);
						// 2. Chain together various setter methods to set the
						// dialog
						// characteristics
						builder.setMessage(R.string.token_invalid).setTitle(R.string.token_invalid_title);
						// 3. Add an okay
						builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								Intent intent = new Intent(m.getApplicationContext(), LogonActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							}
						});
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
									temp.setText("No results to show");
									temp.setGravity(Gravity.CENTER_HORIZONTAL);
									container.addView(temp);
								} else for (int i = 0; i < results.size(); i++) {
									// TODO add each search result here
									TextView temp = new TextView(m);
									temp.setText("Hello #" + i);
									container.addView(temp); // 4. Get the
								}							 // AlertDialog
							} else builder.create().show();  // from create()
						}									 // and show it
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