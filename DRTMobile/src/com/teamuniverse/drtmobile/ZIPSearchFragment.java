package com.teamuniverse.drtmobile;

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

import com.teamuniverse.drtmobile.sectionsetup.SectionDetailActivity;
import com.teamuniverse.drtmobile.sectionsetup.SectionListActivity;
import com.teamuniverse.drtmobile.support.DatabaseManager;
import com.teamuniverse.drtmobile.support.SectionAdder;

/**
 * A fragment representing a single Section detail screen. This fragment is
 * either contained in a {@link SectionListActivity} in two-pane mode (on
 * tablets) or a {@link SectionDetailActivity} on handsets.
 */
public class ZIPSearchFragment extends Fragment {
	private static Button	search;
	private static EditText	zipBox;
	private static String	zip;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ZIPSearchFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_zip_search, container, false);
		
		zipBox = (EditText) view.findViewById(R.id.zip_code);
		search = (Button) view.findViewById(R.id.zip_button);
		
		zip = zipBox.getText().toString();
		
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
		DatabaseManager db = new DatabaseManager(getActivity());
		db.sessionSet("zip", zip);
		db.close();
		
		SectionListActivity.main.putSection(SectionAdder.ZIP_SEARCH_RESULTS);
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