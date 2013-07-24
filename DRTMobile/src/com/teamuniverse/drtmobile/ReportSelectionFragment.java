package com.teamuniverse.drtmobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

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
public class ReportSelectionFragment extends Fragment {
	private Activity					m;
	DatabaseManager						db;
	private ArrayAdapter<CharSequence>	eventNameAdapter;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ReportSelectionFragment() {
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
			View view = inflater.inflate(R.layout.fragment_report_selection, container, false);
			SetterUpper.setup(m, view);
			final Spinner eventYearSpinner = (Spinner) view.findViewById(R.id.year);
			final Spinner eventNameSpinner = (Spinner) view.findViewById(R.id.event_name);
			
			eventNameAdapter = new ArrayAdapter<CharSequence>(m, android.R.layout.simple_spinner_item);
			eventNameSpinner.setAdapter(eventNameAdapter);
			eventYearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
					int year = Integer.parseInt(parent.getItemAtPosition(pos).toString());
					eventNameAdapter.clear();
					switch (year) {
						case 2010:
							eventNameAdapter.addAll(m.getResources().getStringArray(R.array.names_array_2010));
							break;
						case 2011:
							eventNameAdapter.addAll(m.getResources().getStringArray(R.array.names_array_2011));
							break;
						case 2012:
							eventNameAdapter.addAll(m.getResources().getStringArray(R.array.names_array_2012));
							break;
						case 2013:
							eventNameAdapter.addAll(m.getResources().getStringArray(R.array.names_array_2013));
							break;
						default:
							eventNameAdapter.addAll(m.getResources().getStringArray(R.array.names_array_2013));
							break;
					}
					eventNameAdapter.notifyDataSetChanged();
					if (!(eventNameSpinner.getSelectedItemPosition() <= 6)) eventNameSpinner.setSelection(0);
				}
				
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
			
			((Button) view.findViewById(R.id.admin_condition_canned_report)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View button) {
					if (eventNameSpinner.getSelectedItemPosition() != 0) {
						db = new DatabaseManager(m);
						db.sessionSet("event_year", eventYearSpinner.getSelectedItem().toString());
						db.sessionSet("event_name", eventNameSpinner.getSelectedItem().toString());
						db.close();
						SectionListActivity.m.putSection(SectionAdder.ADMIN_CONDITION_CANNED_REPORT);
					} else Toast.makeText(m, m.getString(R.string.no_event_name_picked), Toast.LENGTH_SHORT).show();
				}
			});
			((Button) view.findViewById(R.id.cre_building_closure_delayed_open_canned_report)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View button) {
					if (eventNameSpinner.getSelectedItemPosition() != 0) {
						db = new DatabaseManager(m);
						db.sessionSet("event_year", eventYearSpinner.getSelectedItem().toString());
						db.sessionSet("event_year", eventNameSpinner.getSelectedItem().toString());
						db.close();
						SectionListActivity.m.putSection(SectionAdder.CRE_BUILDING_CLOSURE_DELAYED_OPEN_CANNED_REPORT);
					} else Toast.makeText(m, m.getString(R.string.no_event_name_picked), Toast.LENGTH_SHORT).show();
				}
			});
			
			SectionListActivity.backStackViews.add(view);
			return view;
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