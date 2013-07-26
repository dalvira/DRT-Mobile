package com.teamuniverse.drtmobile;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.att.intern.webservice.Incident;
import com.att.intern.webservice.Webservice;
import com.att.intern.webservice.Webservice.TokenInvalidException;
import com.teamuniverse.drtmobile.sectionsetup.SectionDetailActivity;
import com.teamuniverse.drtmobile.sectionsetup.SectionListActivity;
import com.teamuniverse.drtmobile.support.DatabaseManager;
import com.teamuniverse.drtmobile.support.IncidentHelper;
import com.teamuniverse.drtmobile.support.IncidentInfo;
import com.teamuniverse.drtmobile.support.SectionAdder;
import com.teamuniverse.drtmobile.support.SetterUpper;

/**
 * A fragment representing a single Section detail screen. This fragment is
 * either contained in a {@link SectionListActivity} in two-pane mode (on
 * tablets) or a {@link SectionDetailActivity} on handsets.
 */
public class IncidentZIPResultsFragment extends Fragment {
	private final static int[]		SEARCH_ORDER	= { IncidentInfo.RECORD_NUMBER, IncidentInfo.BUILDING_NAME, IncidentInfo.STATE, IncidentInfo.PM_ATTUID, IncidentInfo.EVENT_NAME, IncidentInfo.INITIAL_REPORT_DATE };
	/** The shortcut to the current activity */
	private static Activity			m;
	/** The progress bar that is shown to indicate background processes */
	private static ProgressBar		progress;
	/** The handler that will allow the multi-threading */
	private static Handler			handler;
	private static DatabaseManager	db;
	
	private static int				zip;
	
	public static LinearLayout		list;
	public static View				view;
	
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
		if (SectionListActivity.backButtonPressed) {
			SectionListActivity.backStackViews.pop();
			View restoring = SectionListActivity.backStackViews.peek();
			((FrameLayout) restoring.getParent()).removeView(restoring);
			return restoring;
		} else {
			view = inflater.inflate(R.layout.fragment_incident_zip_results, container, false);
			SetterUpper.setup(m, view);
			
			db = new DatabaseManager(m);
			zip = Integer.parseInt(db.sessionUnset("zip"));
			((TextView) view.findViewById(R.id.subtitle)).setText("Results for " + zip);
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
					results = ws.geolocSearch(token, zip);
					success = true;
				} catch (TokenInvalidException e) {
					timedOutDuringSearch = true;
					e.printStackTrace();
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
							if (results.size() == 0) {
								TextView temp = new TextView(m);
								temp.setPadding(0, 4, 0, 4);
								temp.setText(R.string.no_results);
								temp.setGravity(Gravity.CENTER_HORIZONTAL);
								container.addView(temp);
								
								if (IncidentHelper.isValidInfoForField(null, IncidentInfo.ZIP_CODE, zip + "").equals("")) {
									Button addNew = new Button(m);
									addNew.setText(m.getString(R.string.add_incident_title));
									addNew.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View arg0) {
											DatabaseManager db = new DatabaseManager(m);
											db.sessionSet("adding_zip", zip + "");
											db.close();
											SectionListActivity.m.putSection(SectionAdder.ADD_INCIDENT);
										}
									});
									container.addView(addNew);
									
								} else Toast.makeText(m, IncidentHelper.isValidInfoForField(null, IncidentInfo.ZIP_CODE, zip + ""), Toast.LENGTH_SHORT).show();
							} else {
								TextView temp;
								LinearLayout eachRecord, eachField;
								for (int i = 0; i < results.size(); i++) {
									if (i != 0) m.getLayoutInflater().inflate(R.layout.divider_line, container);
									
									eachRecord = new LinearLayout(m);
									eachRecord.setPadding(3, 6, 3, 6);
									eachRecord.setTag(R.string.record_number, results.get(i).getRecNumber() + "");
									eachRecord.setOrientation(LinearLayout.VERTICAL);
									eachRecord.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
									eachRecord.setClickable(true);
									
									if (i % 2 == 1) {
										eachRecord.setBackgroundColor(Color.rgb(220, 220, 220));
										eachRecord.setTag(R.string.default_color, "color");
									} else eachRecord.setTag(R.string.default_color, "none");
									
									eachRecord.setOnTouchListener(new OnTouchListener() {
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
													db.close();
													SectionListActivity.m.putSection(SectionAdder.INCIDENT_REC_NUM_RESULTS);
													
													break;
											}
											return true;
										}
									});
									
									IncidentInfo[] fields = IncidentHelper.getInfos(results.get(i), SEARCH_ORDER);
									for (int k = 0; k < fields.length; k++) {
										eachField = new LinearLayout(m);
										eachField.setOrientation(LinearLayout.HORIZONTAL);
										
										temp = new TextView(m);
										temp.setGravity(Gravity.CENTER);
										temp.setMaxLines(1);
										temp.setLayoutParams(new TableLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
										temp.setGravity(Gravity.LEFT);
										temp.setText(fields[k].getDescriptor());
										eachField.addView(temp);
										
										temp = new TextView(m);
										if (fields[k].getId() == IncidentInfo.STATE) {
											String currentState = fields[k].getValue() + "";
											for (int l = 0; l < IncidentInfo.STATE_POSTALS.length; l++) {
												if (currentState.equals(IncidentInfo.STATE_POSTALS[l])) {
													temp.setText(IncidentInfo.STATE_NAMES[l]);
													break;
												}
											}
										} else temp.setText(fields[k].getValue() + "");
										temp.setMaxEms(10);
										eachField.addView(temp);
										
										eachRecord.addView(eachField);
									}
									container.addView(eachRecord);
								}
							}
						} else if (timedOutDuringSearch) {
							SetterUpper.timedOut(m, SectionAdder.INCIDENT_ZIP_RESULTS);
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