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
public class BuildingSearchFragment extends Fragment {
	private static Button	search;
	private static EditText	zipBox;
	private static String	zip;
	private static Activity	m;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public BuildingSearchFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_building_search, container, false);
		LayoutSetterUpper.setup(m, view);
		
		zipBox = (EditText) view.findViewById(R.id.zip_code);
		search = (Button) view.findViewById(R.id.zip_button);
		
		zipBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					search();
					return true;
				}
				return false;
			}
		});
		
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				search();
			}
		});
		
		return view;
	}
	
	protected void search() {
		zip = zipBox.getText().toString();
		// Hide virtual keyboard
		InputMethodManager imm = (InputMethodManager) m.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(zipBox.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(zipBox.getWindowToken(), 0);
		
		if (!zip.equals("") && zip.length() == 5 && zip.matches("[0-9]{5}")) {
			DatabaseManager db = new DatabaseManager(m);
			db.sessionSet("zip", zip);
			db.close();
			
			SectionListActivity.m.putSection(SectionAdder.BUILDING_SEARCH_RESULTS);
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