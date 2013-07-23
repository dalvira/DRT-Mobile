package com.teamuniverse.drtmobile.sectionsetup;

import java.util.Stack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.teamuniverse.drtmobile.LogonActivity;
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
	
	public final static String			FRAG_ID				= "com.teamuniverse.drtmobile.FRAG_ID";
	
	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	public static boolean				mTwoPane;
	public static SectionListActivity	m;
	public static FragmentManager		fragmentManager;
	private static Stack<Integer>		selectedParent;
	
	public static Stack<View>			backStackViews;
	public static boolean				backButtonPressed	= false;
	
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
		selectedParent = new Stack<Integer>();
		backStackViews = new Stack<View>();
		
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
				setSelectedParent(which);
				selectedParent.add(which);
				fragmentManager = getSupportFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.section_detail_container, SectionAdder.getSection(which)).commit();
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
		putSection(Integer.parseInt(id));
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
			try {
				setSelectedParent(id);
				selectedParent.add(id);
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.section_detail_container, SectionAdder.getSection(id));
				transaction.addToBackStack(null);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Intent detailIntent = new Intent(this, SectionDetailActivity.class);
			detailIntent.putExtra(FRAG_ID, id + "");
			startActivity(detailIntent);
		}
	}
	
	public static void setFragmentManager(FragmentManager fm) {
		fragmentManager = fm;
	}
	
	private void setSelectedParent(int id) {
		db = new DatabaseManager(this);
		String authorization = db.sessionGet("authorization");
		db.close();
		((SectionListFragment) getSupportFragmentManager().findFragmentById(R.id.section_list)).setActivatedPosition(authorization.equals("RPT") ? SectionAdder.PARENTS_RPT_FIXER[id]
																																				: SectionAdder.SECTION_PARENTS[id]);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.in_app_menu, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		if (mTwoPane) {
			fragmentManager = getSupportFragmentManager();
			if (fragmentManager.getBackStackEntryCount() > 0) {
				backButtonPressed = true;
				fragmentManager.popBackStackImmediate();
				selectedParent.pop();
				setSelectedParent(selectedParent.peek());
				backButtonPressed = false;
			} else super.onBackPressed();
		} else super.onBackPressed();
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.log_out:
				Intent intent = new Intent(getApplicationContext(), LogonActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
			case android.R.id.home:
				// This ID represents the Home or Up button. In the case of this
				// activity, the Up button is shown. Use NavUtils to allow users
				// to navigate up one level in the application structure. For
				// more details, see the Navigation pattern on Android Design:
				//
				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
				//
				NavUtils.navigateUpTo(this, new Intent(this, SectionListActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}