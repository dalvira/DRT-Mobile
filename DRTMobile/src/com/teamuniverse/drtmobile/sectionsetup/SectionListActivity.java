package com.teamuniverse.drtmobile.sectionsetup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.teamuniverse.drtmobile.R;
import com.teamuniverse.drtmobile.support.DatabaseManager;
import com.teamuniverse.drtmobile.support.ErrorReporter;
import com.teamuniverse.drtmobile.support.SectionAdder;

/**
 * An activity representing a list of Sections. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link SectionDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link SectionListFragment} and the item details (if present) is a
 * {@link SectionDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link SectionListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class SectionListActivity extends FragmentActivity implements
		SectionListFragment.Callbacks {
	
	public final static String			FRAG_ID	= "com.teamuniverse.drtmobile.FRAG_ID";
	
	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	public static boolean				mTwoPane;
	public static SectionListActivity	m;
	
	private DatabaseManager				db;
	
	// The detail container view will be present only in the
	// large-screen layouts (res/values-large and
	// res/values-sw600dp). If this view is present, then the
	// activity should be in two-pane mode.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_activity_section_list);
		m = this;
		
		mTwoPane = findViewById(R.id.section_detail_container) != null;
		if (mTwoPane) {
			
			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((SectionListFragment) getSupportFragmentManager().findFragmentById(R.id.section_list)).setActivateOnItemClick(true);
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		ErrorReporter errReporter = new ErrorReporter();
		errReporter.Init(this);
		
		db = new DatabaseManager(this);
		if (db.checkSetting("send_diagnostics")) errReporter.CheckErrorAndSendMail(this);
		db.close();
	}
	
	/**
	 * This methods returns the user to the section at which the user was before
	 * pausing, if applicable.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if (mTwoPane) {
			
			db = new DatabaseManager(this);
			String selected = db.sessionGet("selected_section");
			String authorization = db.sessionGet("authorization");
			int which;
			if (selected.equals("")) {
				if (authorization.equals("ADM")) which = SectionAdder.INCIDENT_ZIP_SEARCH;
				else which = SectionAdder.REPORT_SELECTION;
				db.sessionSet("selected_section", which + "");
			} else which = (int) Long.parseLong(selected);
			db.close();
			
			try {
				((SectionListFragment) getSupportFragmentManager().findFragmentById(R.id.section_list)).setActivatedPosition(authorization.equals("RPT") ? SectionAdder.PARENTS_RPT_FIXER[which]
																																						: SectionAdder.SECTION_PARENTS[which]);
				getSupportFragmentManager().beginTransaction().replace(R.id.section_detail_container, SectionAdder.getSection(which)).commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Callback method from {@link SectionListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			db = new DatabaseManager(this);
			db.sessionSet("selected_section", id);
			db.close();
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			try {
				getSupportFragmentManager().beginTransaction().replace(R.id.section_detail_container, SectionAdder.getSection((int) Long.parseLong(id))).commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, SectionDetailActivity.class);
			detailIntent.putExtra(FRAG_ID, id);
			startActivity(detailIntent);
		}
	}
	
	/**
	 * This method dynamically puts a section in the SectionDetailActivity part
	 * of the screen. In tablet view, it adds the section to the right side, in
	 * phone view, it simply starts the fragment as fullscreen. Call it with a
	 * reference to the final int of SectionAdder that refers to the desired
	 * fragment.
	 * 
	 * @param id
	 *            The integer ID of the desired fragment. This should be a
	 *            reference to the appropriate final int of SectionAdder.
	 */
	public void putSection(int id) {
		if (mTwoPane) {
			db = new DatabaseManager(this);
			db.sessionSet("selected_section", id + "");
			String authorization = db.sessionGet("authorization");
			db.close();
			try {
				((SectionListFragment) getSupportFragmentManager().findFragmentById(R.id.section_list)).setActivatedPosition(authorization.equals("RPT") ? SectionAdder.PARENTS_RPT_FIXER[id]
																																						: SectionAdder.SECTION_PARENTS[id]);
				getSupportFragmentManager().beginTransaction().replace(R.id.section_detail_container, SectionAdder.getSection(id)).commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Intent detailIntent = new Intent(this, SectionDetailActivity.class);
			detailIntent.putExtra(FRAG_ID, id + "");
			startActivity(detailIntent);
		}
	}
}