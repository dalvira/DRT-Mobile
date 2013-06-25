package com.teamuniverse.drtmobile;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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
public class ZIPSearchResultsFragment extends Fragment {
	private static ProgressBar	progress;
	private static boolean		querying;
	private Handler				handler;
	private Activity			me;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ZIPSearchResultsFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_report_selection, container, false);
		
		LinearLayout list = (LinearLayout) view.findViewById(R.id.zip_results_list);
		search(list);
		
		progress = (ProgressBar) view.findViewById(R.id.logon_progress);
		querying = false;
		handler = new Handler();
		me = getActivity();
		
		return view;
	}
	
	ArrayList<Incident>	results	= null;
	
	private void search(LinearLayout containerRef) {
		final LinearLayout container = containerRef;
		// Get contents of the EditTexts
		if (!querying) {
			querying = true;
			progress.setVisibility(View.VISIBLE);
			new Thread(new Runnable() {
				public void run() {
					Webservice ws = new Webservice(me);
					
					DatabaseManager db = new DatabaseManager(me);
					String token = db.sessionGet("token");
					int zip = Integer.parseInt(db.sessionGet("zip"));
					db.close();
					
					try {
						results = ws.geolocSearch(token, zip);
					} catch (TokenInvalidException e) {
						e.printStackTrace();
						// 1. Instantiate an AlertDialog.Builder with its
						// constructor
						AlertDialog.Builder builder = new AlertDialog.Builder(me);
						// 2. Chain together various setter methods to set the
						// dialog
						// characteristics
						builder.setMessage(R.string.token_invalid).setTitle(R.string.token_invalid_title);
						// 3. Add an okay
						builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								Intent intent = new Intent(me.getApplicationContext(), LogonActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							}
						});
						// 4. Get the AlertDialog from create() and show it
						builder.create().show();
					} catch (Exception e) {
						e.printStackTrace();
					}
					handler.postDelayed(new Runnable() {
						public void run() {
							// Hide the progress bar
							progress.setVisibility(View.INVISIBLE);
							
							for (int i = 0; i < results.size(); i++) {
								// TODO add each search result here
								container.addView(new TextView(me));
							}
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