package com.teamuniverse.drtmobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
public class IncidentZIPSearchFragment extends Fragment {
	/** The shortcut to the current activity */
	private Activity		m;
	/** The access to the application database */
	private DatabaseManager	db;
	
	private EditText		zipBox;
	private String			zip;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public IncidentZIPSearchFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_incident_zip_search, container, false);
		SetterUpper.setup(m, view);
		
		zipBox = (EditText) view.findViewById(R.id.zip_code);
		db = new DatabaseManager(m);
		if (db.sessionGet("back").equals("true")) {
			zipBox.setText(db.sessionGet("zip"));
			db.sessionUnset("back");
		}
		db.close();
		zipBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					search();
					return true;
				}
				return false;
			}
		});
		
		((Button) view.findViewById(R.id.go_button)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				search();
			}
		});
		
		return view;
	}
	
	protected void search() {
		zip = zipBox.getText().toString();
		String message = IncidentHelper.isValidInfoForField(null, IncidentInfo.ZIP_CODE, zip);
		if (message.equals("")) {
			DatabaseManager db = new DatabaseManager(m);
			db.sessionSet("zip", zip);
			db.close();
			SectionListActivity.m.putSection(SectionAdder.INCIDENT_ZIP_RESULTS);
		} else {
			Toast.makeText(m, message, Toast.LENGTH_SHORT).show();
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