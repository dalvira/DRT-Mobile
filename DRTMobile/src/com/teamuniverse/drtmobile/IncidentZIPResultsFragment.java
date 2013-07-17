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
public class IncidentZIPResultsFragment extends Fragment {
	/** The shortcut to the current activity */
	private static Activity		m;
	/** The progress bar that is shown to indicate background processes */
	private static ProgressBar	progress;
	/** The handler that will allow the multi-threading */
	private Handler				handler;
	private DatabaseManager		db;
	
	private static final int	COLUMNS	= 3;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public IncidentZIPResultsFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_incident_zip_results, container, false);
		SetterUpper.setup(m, view);
		
		db = new DatabaseManager(m);
		((TextView) view.findViewById(R.id.subtitle)).setText("Results for " + db.sessionGet("zip"));
		db.close();
		progress = (ProgressBar) view.findViewById(R.id.progress);
		handler = new Handler();
		LinearLayout list = (LinearLayout) view.findViewById(R.id.list_container);
		
		search(list);
		
		return view;
	}
	
	ArrayList<Incident>	results	= new ArrayList<Incident>(0);
	AlertDialog.Builder	builder	= null;
	boolean				success	= true;
	
	private void search(final LinearLayout container) {
		// Get contents of the EditTexts
		new Thread(new Runnable() {
			public void run() {
				Webservice ws = new Webservice(m);
				
				db = new DatabaseManager(m);
				String token = db.sessionGet("token");
				int zip = (int) Long.parseLong(db.sessionGet("zip"));
				db.close();
				
				try {
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
									
									each = new LinearLayout(m);
									each.setPadding(0, 6, 0, 6);
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
													SetterUpper.setSelected(m, v);
													break;
												case MotionEvent.ACTION_MOVE:
													SetterUpper.setSelected(m, v);
													break;
												case MotionEvent.ACTION_CANCEL:
													if (v.getTag(R.string.default_color).equals("color")) SetterUpper.setUnSelected(m, v, false);
													else SetterUpper.setUnSelected(m, v, true);
													break;
												case MotionEvent.ACTION_UP:
													if (v.getTag(R.string.default_color).equals("color")) SetterUpper.setUnSelected(m, v, false);
													else SetterUpper.setUnSelected(m, v, true);
													
													db = new DatabaseManager(m);
													db.sessionSet("record_number", (String) v.getTag(R.string.record_number));
													db.sessionSet("from", "incident");
													db.close();
													SectionListActivity.m.putSection(SectionAdder.INCIDENT_REC_NUM_RESULTS);
													
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
										else if (j == 1) temp.setText(results.get(i).getRecNumber() + "");
										else if (j == 2) temp.setText(results.get(i).getIncidentNotes());
										// else if (j == 3) temp.setText();
										// else if (j == 4) temp.setText();
										// else if (j == 5) temp.setText();
										
										each.addView(temp);
									}
									
									container.addView(each);
								}
							}
						} else {
							SetterUpper.timedOut(m);
							search(container);
						}
						// Hide the progress bar
						progress.setVisibility(View.GONE);
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