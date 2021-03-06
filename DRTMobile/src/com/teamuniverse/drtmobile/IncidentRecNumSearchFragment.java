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
import android.widget.FrameLayout;
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
public class IncidentRecNumSearchFragment extends Fragment {
	/** The shortcut to the current activity */
	private Activity		m;
	private DatabaseManager	db;
	
	private EditText		getRecordNumber;
	private Button			button;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public IncidentRecNumSearchFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		try {
			db = new DatabaseManager(m);
			boolean goingBack = db.checkSetting("going_back");
			db.setSetting("going_back", false);
			db.close();
			if (goingBack) throw new NullPointerException();
			
			View view = inflater.inflate(R.layout.fragment_incident_rec_num_search, container, false);
			SetterUpper.setup(m, view);
			getRecordNumber = (EditText) view.findViewById(R.id.damage_get_record_number);
			
			db = new DatabaseManager(m);
			if (db.sessionGet("back").equals("true")) {
				getRecordNumber.setText(db.sessionGet("record_number"));
				db.sessionUnset("back");
			}
			db.close();
			
			// getRecordNumber.requestFocus();
			
			button = (Button) view.findViewById(R.id.go_button);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					get();
				}
			});
			
			getRecordNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
						button.performClick();
						return true;
					}
					return false;
				}
			});
			
			SectionListActivity.backStackViews.add(view);
			return view;
		} catch (Exception e) {
			SectionListActivity.backStackViews.pop();
			View restoring = SectionListActivity.backStackViews.peek();
			((FrameLayout) restoring.getParent()).removeView(restoring);
			return restoring;
		}
	}
	
	private void get() {
		String text = getRecordNumber.getText().toString();
		
		String message = IncidentHelper.isValidInfoForField(null, IncidentInfo.RECORD_NUMBER, text);
		if (message.equals("")) {
			db = new DatabaseManager(m);
			db.sessionSet("record_number", text);
			db.close();
			SectionListActivity.m.putSection(SectionAdder.INCIDENT_REC_NUM_RESULTS);
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
