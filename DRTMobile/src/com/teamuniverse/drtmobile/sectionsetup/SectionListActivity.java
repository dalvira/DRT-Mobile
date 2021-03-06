package com.teamuniverse.drtmobile.sectionsetup;

import java.util.Stack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
	public static FragmentManager		fragmentManager;
	
	public static Stack<Integer>		selectedParent;
	public static Stack<View>			backStackViews;
	public static Stack<Fragment>		backStackFragment;
	
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
		backStackFragment = new Stack<Fragment>();
		
		mTwoPane = findViewById(R.id.section_detail_container) != null;
		if (mTwoPane) {
			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((SectionListFragment) getSupportFragmentManager().findFragmentById(R.id.section_list)).setActivateOnItemClick(true);
			
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
			
			selectedParent.add(which);
			fragmentManager = getSupportFragmentManager();
			Fragment frag = SectionAdder.getSection(which);
			backStackFragment.add(frag);
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		// ErrorReporter errReporter = new ErrorReporter();
		// errReporter.Init(this);
		//
		// db = new DatabaseManager(this);
		// if (db.checkSetting("send_diagnostics"))
		// errReporter.CheckErrorAndSendMail(this);
		// db.close();
	}
	
	/**
	 * This methods returns the user to the section at which the user was before
	 * pausing, if applicable.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if (mTwoPane) {
			try {
				setSelectedParent(selectedParent.peek());
				fragmentManager.beginTransaction().replace(R.id.section_detail_container, backStackFragment.peek()).commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			clearStacks();
		}
	}
	
	public void clearStacks() {
		selectedParent.clear();
		backStackViews.clear();
		backStackFragment.clear();
	}
	
	/**
	 * Callback method from {@link SectionListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			fragmentManager = getSupportFragmentManager();
			putSection(Integer.parseInt(id));
		} else {
			Intent detailIntent = new Intent(this, SectionDetailActivity.class);
			detailIntent.putExtra(FRAG_ID, id + "");
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
		try {
			if (mTwoPane) setSelectedParent(id);
			selectedParent.add(id);
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			Fragment frag = SectionAdder.getSection(id);
			backStackFragment.add(frag);
			transaction.replace(R.id.section_detail_container, frag);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void popSection() {
		db = new DatabaseManager(m);
		db.setSetting("going_back", true);
		db.close();
		try {
			selectedParent.pop();
			backStackFragment.pop();
			if (mTwoPane) setSelectedParent(selectedParent.peek());
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.replace(R.id.section_detail_container, backStackFragment.peek());
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setFragmentManager(FragmentManager fm) {
		fragmentManager = fm;
	}
	
	public void setSelectedParent(int id) {
		db = new DatabaseManager(this);
		String authorization = db.sessionGet("authorization");
		db.close();
		((SectionListFragment) getSupportFragmentManager().findFragmentById(R.id.section_list)).setActivatedPosition(authorization.equals("RPT") ? SectionAdder.PARENTS_RPT_FIXER[id] : SectionAdder.SECTION_PARENTS[id]);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.in_app_menu, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		fragmentManager = getSupportFragmentManager();
		if (backStackFragment.size() > 1) {
			popSection();
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