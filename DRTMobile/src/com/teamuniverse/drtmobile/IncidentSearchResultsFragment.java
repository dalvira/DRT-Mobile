package com.teamuniverse.drtmobile;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.att.intern.webservice.Incident;
import com.att.intern.webservice.Webservice;
import com.att.intern.webservice.Webservice.TokenInvalidException;
import com.teamuniverse.drtmobile.sectionsetup.SectionDetailActivity;
import com.teamuniverse.drtmobile.sectionsetup.SectionListActivity;
import com.teamuniverse.drtmobile.support.DatabaseManager;
import com.teamuniverse.drtmobile.support.SectionAdder;
import com.teamuniverse.drtmobile.support.SetterUpper;

/**
 * A fragment representing a single Section detail screen. This fragment is
 * either contained in a {@link SectionListActivity} in two-pane mode (on
 * tablets) or a {@link SectionDetailActivity} on handsets.
 */
public class IncidentSearchResultsFragment extends Fragment {
	private static final int	COLUMNS	= 3;
	
	private static ProgressBar	progress;
	private static boolean		querying;
	private Handler				handler;
	private Activity			m;
	
	private DatabaseManager		db;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public IncidentSearchResultsFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_incident_search_results, container, false);
		SetterUpper.setup(m, view);
		
		progress = (ProgressBar) view.findViewById(R.id.progress);
		querying = false;
		handler = new Handler();
		LinearLayout list = (LinearLayout) view.findViewById(R.id.list_container);
		
		Button backButton = (Button) view.findViewById(R.id.back_button);
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				db = new DatabaseManager(m);
				db.sessionSet("back", "true");
				db.close();
				SectionListActivity.m.putSection(SectionAdder.INCIDENT_SEARCH);
			}
		});
		
		search(list);
		
		return view;
	}
	
	ArrayList<Incident>	results	= null;
	AlertDialog.Builder	builder	= null;
	boolean				success	= true;
	
	private void search(LinearLayout containerRef) {
		final LinearLayout container = containerRef;
		// Get contents of the EditTexts
		if (!querying) {
			querying = true;
			new Thread(new Runnable() {
				public void run() {
					Webservice ws = LogonActivity.ws;
					
					db = new DatabaseManager(m);
					String token = db.sessionGet("token");
					int zip = Integer.parseInt(db.sessionGet("zip"));
					db.close();
					
					try {
						// ws.resetData();
						results = ws.geolocSearch(token, zip);
					} catch (TokenInvalidException e) {
						success = false;
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					handler.postDelayed(new Runnable() {
						public void run() {
							
							if (success) {
								if (results.size() == 0) {
									TextView temp = new TextView(m);
									temp.setPadding(0, 4, 0, 4);
									temp.setText(R.string.no_results);
									temp.setGravity(Gravity.CENTER_HORIZONTAL);
									container.addView(temp);
								} else {
									TextView temp;
									LinearLayout each;
									for (int i = 0; i < results.size(); i++) {
										if (i != 0) m.getLayoutInflater().inflate(R.layout.divider_line, container);
										
										// TODO add each search result here
										each = new LinearLayout(m);
										each.setPadding(0, 4, 0, 4);
										each.setTag(R.string.record_number, results.get(i).getRecNumber() + "");
										each.setOrientation(LinearLayout.HORIZONTAL);
										each.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
										each.setClickable(true);
										
										if (i % 2 == 1) {
											each.setBackgroundColor(Color.rgb(220, 220, 220));
											each.setTag(R.string.default_color, "color");
										} else each.setTag(R.string.default_color, "none");
										
										each.setOnTouchListener(new OnTouchListener() {
											@Override
											public boolean onTouch(View v, MotionEvent event) {
												switch (event.getAction()) {
													case MotionEvent.ACTION_DOWN:
														v.setBackgroundColor(Color.rgb(0x33, 0xb5, 0xe5));
														break;
													case MotionEvent.ACTION_MOVE:
														v.setBackgroundColor(Color.rgb(0x33, 0xb5, 0xe5));
														break;
													case MotionEvent.ACTION_CANCEL:
														if (v.getTag(R.string.default_color).equals("color")) v.setBackgroundColor(Color.rgb(220, 220, 220));
														else v.setBackgroundColor(Color.TRANSPARENT);
														break;
													case MotionEvent.ACTION_UP:
														if (v.getTag(R.string.default_color).equals("color")) v.setBackgroundColor(Color.rgb(220, 220, 220));
														else v.setBackgroundColor(Color.TRANSPARENT);
														
														db = new DatabaseManager(m);
														db.sessionSet("record_number", (String) v.getTag(R.string.record_number));
														db.sessionSet("from", "incident");
														db.close();
														SectionListActivity.m.putSection(SectionAdder.DAMAGE_ASSESSMENT_GET);
														
														break;
												}
												return true;
											}
										});
										
										for (int j = 0; j < COLUMNS; j++) {
											temp = new TextView(m);
											temp.setGravity(Gravity.CENTER);
											temp.setLayoutParams(new TableLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
											temp.setMaxLines(1);
											if (j == 0) temp.setText(results.get(i).getEventName());
											else if (j == 1) temp.setText(results.get(i).getGeoLoc() + "");
											else if (j == 2) temp.setText(results.get(i).getIncidentNotes());
											// else if (j == 3) temp.setText();
											// else if (j == 4) temp.setText();
											// else if (j == 5) temp.setText();
											
											each.addView(temp);
										}
										
										container.addView(each);
									}
								}
								// Hide the progress bar
								progress.setVisibility(View.GONE);
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