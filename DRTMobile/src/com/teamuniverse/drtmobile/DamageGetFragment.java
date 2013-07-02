package com.teamuniverse.drtmobile;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.att.intern.webservice.Incident;
import com.att.intern.webservice.Webservice;
import com.att.intern.webservice.Webservice.TokenInvalidException;
import com.teamuniverse.drtmobile.sectionsetup.SectionDetailActivity;
import com.teamuniverse.drtmobile.sectionsetup.SectionListActivity;
import com.teamuniverse.drtmobile.support.DatabaseManager;
import com.teamuniverse.drtmobile.support.LayoutSetterUpper;
import com.teamuniverse.drtmobile.support.SectionAdder;

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
		LayoutSetterUpper.setup(m, view);
		
		progress = (ProgressBar) view.findViewById(R.id.progress);
		querying = false;
		handler = new Handler();
		
		db = new DatabaseManager(m);
		db.sessionUnset("goto_tab");
		db.close();
		
		Button backButton = (Button) view.findViewById(R.id.damage_assessment_back);
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
							
							if (success) {
								if (result == null) {
									TextView temp = new TextView(m);
									temp.setText(R.string.no_record);
									temp.setGravity(Gravity.CENTER_HORIZONTAL);
									container.addView(temp);
								} else {
									// TODO add a search result here
									TextView temp = new TextView(m);
									temp.setText("Hello #1");
									container.addView(temp);
								}
							} else LayoutSetterUpper.timedOut(m);
							
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

// Incident incident = new Incident();
// incident.setAssessNotes(null);
// incident.setBuildingAddress(null);
// incident.setBuildingName(null);
// incident.setBuildingStatus(null);
// incident.setBuildingType(null);
// incident.setCompltnDate(null);
// incident.setComPowerIndicator(null);
// incident.setContactPhone(null);
// incident.setCreLead(null);
// incident.setDamageIndicator(null);
// incident.setElecIssueClsdIndicator(null);
// incident.setElecIssueIndicator(null);
// incident.setEnvIssueClsdIndicator(null);
// incident.setEnvIssueIndicator(null);
// incident.setEstCapCost(0);
// incident.setEstExpenseCost(0);
// incident.setEventName(null);
// incident.setFenceGateIssueClsdIndicator(null);
// incident.setFenceGateIssueIndicator(null);
// incident.setGenIssueClsdIndicator(null);
// incident.setGenIssueIndicator(null);
// incident.setGeoLoc(0);
// incident.setGroundsIssueClsdIndicator(null);
// incident.setGroundsIssueIndicator(null);
// incident.setIncidentCompltnDate(null);
// incident.setIncidentNotes(null);
// incident.setIncidentStatus(null);
// incident.setIncidentYear(0);
// incident.setInitialRptDate(null);
// incident.setMechIssueClsdIndicator(null);
// incident.setMechIssueIndicator(null);
// incident.setMobCOIndicator(null);
// incident.setOnGeneratorIndicator(null);
// incident.setOtherIssueClsdIndicator(null);
// incident.setOtherIssueIndicator(null);
// incident.setPlumbIssueClsdIndicator(null);
// incident.setPlumbIssueIndicator(null);
// incident.setPMAttuid(null);
// incident.setRecNumber(0);
// incident.setReqATTUID(null);
// incident.setRoofsIssueClsdIndicator(null);
// incident.setRoofsIssueIndicator(null);
// incident.setSafetyIssueClsdIndicator(null);
// incident.setSafetyIssueIndicator(null);
// incident.setState(null);
// incident.setStatusNotes(null);
// incident.setStructIssueClsdIndicator(null);
// incident.setStructIssueIndicator(null);
// incident.setUnOccupiableIndicator(null);
// incident.setWaterIssueClsdIndicator(null);
// incident.setWaterIssueIndicator(null);
// incident.setWorkReqNumber(null);