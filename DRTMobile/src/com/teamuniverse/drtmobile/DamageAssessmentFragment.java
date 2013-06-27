package com.teamuniverse.drtmobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.teamuniverse.drtmobile.sectionsetup.SectionDetailActivity;
import com.teamuniverse.drtmobile.sectionsetup.SectionListActivity;
import com.teamuniverse.drtmobile.support.DatabaseManager;
import com.teamuniverse.drtmobile.support.LayoutSetterUpper;

/**
 * A fragment representing a single Section detail screen. This fragment is
 * either contained in a {@link SectionListActivity} in two-pane mode (on
 * tablets) or a {@link SectionDetailActivity} on handsets.
 */
public class DamageAssessmentFragment extends Fragment {
	// private static ProgressBar progress;
	// private static boolean querying;
	// private Handler handler;
	private Activity	m;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public DamageAssessmentFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_damage_assessment, container, false);
		LayoutSetterUpper.setup(m, view);
		
		DatabaseManager db = new DatabaseManager(m);
		((EditText) view.findViewById(R.id.attuid2)).setText(db.sessionGet("attuid"));
		db.close();
		
		// progress = null;
		// querying = false;
		// handler = new Handler();
		
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
		
		return view;
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
