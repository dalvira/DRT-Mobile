package com.teamuniverse.drtmobile;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
import com.teamuniverse.drtmobile.support.IncidentInfo;
import com.teamuniverse.drtmobile.support.SectionAdder;
import com.teamuniverse.drtmobile.support.SetterUpper;

/**
 * A fragment representing a single Section detail screen. This fragment is
 * either contained in a {@link SectionListActivity} in two-pane mode (on
 * tablets) or a {@link SectionDetailActivity} on handsets.
 */
public class DamageGetFragment extends Fragment {
	private static ProgressBar	progress;
	private static boolean		querying;
	private Handler				handler;
	private DatabaseManager		db;
	private Activity			m;
	
	private final int			COLUMNS	= 2;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public DamageGetFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_damage_get, container, false);
		SetterUpper.setup(m, view);
		
		progress = (ProgressBar) view.findViewById(R.id.progress);
		querying = false;
		handler = new Handler();
		
		db = new DatabaseManager(m);
		db.sessionUnset("goto_tab");
		db.close();
		
		Button backButton = (Button) view.findViewById(R.id.back_button);
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				db = new DatabaseManager(m);
				db.sessionSet("back", "true");
				String which = db.sessionGet("from");
				db.sessionUnset("from");
				db.close();
				if (which.equals("incident")) SectionListActivity.m.putSection(SectionAdder.INCIDENT_SEARCH_RESULTS);
				else SectionListActivity.m.putSection(SectionAdder.DAMAGE_ASSESSMENT);
			}
		});
		
		LinearLayout list = (LinearLayout) view.findViewById(R.id.list_container);
		search(list);
		
		return view;
	}
	
	Incident	result	= null;
	boolean		success	= false;
	
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
					int recordNumber = (int) Long.parseLong(db.sessionGet("record_number"));
					db.close();
					
					try {
						result = ws.incidByRecNum(token, recordNumber);
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
							
							TextView temp;
							LinearLayout each;
							if (success) {
								if (result == null) {
									temp = new TextView(m);
									temp.setText(R.string.no_record);
									temp.setGravity(Gravity.CENTER_HORIZONTAL);
									container.addView(temp);
								} else {
									IncidentInfo[] infos = IncidentInfo.getInfos(result);
									for (int i = 0; i < infos.length; i++) {
										if (i != 0) m.getLayoutInflater().inflate(R.layout.divider_line, container);
										
										each = new LinearLayout(m);
										each.setPadding(0, 4, 0, 4);
										each.setOrientation(LinearLayout.HORIZONTAL);
										each.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
										// each.setClickable(true);
										
										if (i % 2 == 1) {
											each.setBackgroundColor(Color.rgb(220, 220, 220));
											each.setTag(R.string.default_color, "color");
										} else each.setTag(R.string.default_color, "none");
										
										// each.setOnTouchListener(new
										// OnTouchListener() {
										// @Override
										// public boolean onTouch(View v,
										// MotionEvent event) {
										// switch (event.getAction()) {
										// case MotionEvent.ACTION_DOWN:
										// v.setBackgroundColor(Color.rgb(0x33,
										// 0xb5, 0xe5));
										// break;
										// case MotionEvent.ACTION_MOVE:
										// v.setBackgroundColor(Color.rgb(0x33,
										// 0xb5, 0xe5));
										// break;
										// case MotionEvent.ACTION_CANCEL:
										// if
										// (v.getTag(R.string.default_color).equals("color"))
										// v.setBackgroundColor(Color.rgb(220,
										// 220, 220));
										// else
										// v.setBackgroundColor(Color.TRANSPARENT);
										// break;
										// case MotionEvent.ACTION_UP:
										// if
										// (v.getTag(R.string.default_color).equals("color"))
										// v.setBackgroundColor(Color.rgb(220,
										// 220, 220));
										// else
										// v.setBackgroundColor(Color.TRANSPARENT);
										//
										// db = new DatabaseManager(m);
										// db.sessionSet("record_number",
										// (String)
										// v.getTag(R.string.record_number));
										// db.sessionSet("from", "incident");
										// db.close();
										// SectionListActivity.m.putSection(SectionAdder.DAMAGE_ASSESSMENT_GET);
										//
										// break;
										// }
										// return true;
										// }
										// });
										
										for (int j = 0; j < COLUMNS; j++) {
											temp = new TextView(m);
											temp.setGravity(Gravity.CENTER);
											temp.setLayoutParams(new TableLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
											if (j == 0) temp.setText(infos[i].getKey());
											else if (j == 1) {
												try {
													temp.setText((String) infos[i].getValue());
												} catch (ClassCastException e) {
													temp.setText((Integer) infos[i].getValue() + "");
												}
											}
											each.addView(temp);
										}
										
										container.addView(each);
									}
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