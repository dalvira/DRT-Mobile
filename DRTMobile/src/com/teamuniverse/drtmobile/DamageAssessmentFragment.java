package com.teamuniverse.drtmobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

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
public class DamageAssessmentFragment extends Fragment {
	private Activity		m;
	private DatabaseManager	db;
	
	private EditText		getRecordNumber;
	private Button			getButton;
	private Button			addButton;
	
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
		getRecordNumber = (EditText) view.findViewById(R.id.damage_get_record_number);
		
		getButton = (Button) view.findViewById(R.id.damage_assessment_get);
		getButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				get();
			}
		});
		
		getRecordNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					v.performClick();
					getButton.performClick();
					return true;
				}
				return false;
			}
		});
		
		addButton = (Button) view.findViewById(R.id.damage_assessment_add);
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				add();
			}
		});
		
		TabHost tabHost = (TabHost) view.findViewById(android.R.id.tabhost);
		tabHost.setup();
		// Get tab!
		TabSpec tspec = tabHost.newTabSpec("Tab1");
		tspec.setContent(R.id.damage_get);
		tspec.setIndicator("Get");
		tabHost.addTab(tspec);
		// Add tab!
		tspec = tabHost.newTabSpec("Tab2");
		tspec.setContent(R.id.damage_add);
		tspec.setIndicator("Add");
		tabHost.addTab(tspec);
		
		// db = new DatabaseManager(m);
		// ((EditText)
		// view.findViewById(R.id.damage_add_attuid)).setText(db.sessionGet("attuid"));
		// db.close();
		return view;
	}
	
	private void get() {
		String text = getRecordNumber.getText().toString();
		
		// Hide virtual keyboard
		InputMethodManager imm = (InputMethodManager) m.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getRecordNumber.getWindowToken(), 0);
		
		if (!text.equals("")) {
			
			db = new DatabaseManager(m);
			db.sessionSet("record_number", text);
			db.close();
			
			SectionListActivity.m.putSection(SectionAdder.DAMAGE_ASSESSMENT_GET);
		} else {
			// 1. Instantiate an AlertDialog.Builder with its
			// constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(m);
			// 2. Chain together various setter methods to set the
			// dialog
			// characteristics
			builder.setMessage(R.string.zip_invalid).setTitle(R.string.zip_invalid_title);
			// 3. Add an okay
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			// 4. Get the AlertDialog from create() and show it
			builder.create().show();
		}
	}
	
	private void add() {
		// TODO put all fields into session with DatabaseManager. Send to add
		db = new DatabaseManager(m);
		if (!"field1".equals("")) db.sessionSet("field1name", "field1");
		db.close();
		SectionListActivity.m.putSection(SectionAdder.DAMAGE_ASSESSMENT_ADD);
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
